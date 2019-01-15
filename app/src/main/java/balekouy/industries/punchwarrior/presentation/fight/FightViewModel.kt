package balekouy.industries.punchwarrior.presentation.fight

import android.arch.lifecycle.MutableLiveData
import android.os.CountDownTimer
import android.os.Handler
import balekouy.industries.punchwarrior.data.Difficulty
import balekouy.industries.punchwarrior.data.models.Fighter
import balekouy.industries.punchwarrior.data.models.Level
import balekouy.industries.punchwarrior.data.repository.DataMapper
import balekouy.industries.punchwarrior.di.UltraPunchWarriorApplication
import balekouy.industries.punchwarrior.presentation.BaseViewModel


class FightViewModel : BaseViewModel() {

    companion object {
        private const val PLAYER_LEFT_FORCE = 6
        private const val PLAYER_LEFT_SPEED = 7
        private const val PLAYER_LEFT_DELAY: Long = ((11 - PLAYER_LEFT_SPEED) * 100).toLong()
        private const val PLAYER_RIGHT_FORCE = 8
        private const val PLAYER_RIGHT_SPEED = 5
        private const val PLAYER_RIGHT_DELAY: Long = ((11 - PLAYER_RIGHT_SPEED) * 100).toLong()
        private const val PLAYER_HEALTH = 6
        private const val PLAYER_ENERGY = 7

        const val ROUND_DURATION = 10
        const val NB_ROUND = 3
    }

    private val liveFightState: MutableLiveData<FightState> = MutableLiveData()
    private val livePlayerState: MutableLiveData<FighterState> = MutableLiveData()
    private val liveOpponentState: MutableLiveData<FighterState> = MutableLiveData()
    private val handler = Handler()

    private lateinit var level: Level
    private lateinit var difficulty: Difficulty
    private lateinit var player: FighterState
    private lateinit var opponent: FighterState
    private lateinit var opponentStats: Fighter
    private var fightState: FightState
    private val playerSprite = DataMapper.createSpriteNames("player")

    init {
        initializeDagger(this)
        fightState = FightState(isLoading = true)
        liveFightState.value = fightState
    }


    fun configureFight(level: Level, difficultyId: Int) {
        this.level = level
        this.opponentStats = level.fighter.second
        this.difficulty = Difficulty.withId(difficultyId) ?: Difficulty.NORMAL
        player = FighterState(
            portraitRes = playerSprite.portrait,
            animationRes = playerSprite.normalMode,
            health = 100,
            energy = 100
        )
        opponent = FighterState(
            portraitRes = opponentStats.sprites.portrait,
            animationRes = opponentStats.sprites.normalMode,
            health = 100,
            energy = 100
        )
        livePlayerState.value = player
        liveOpponentState.value = opponent
        fightState = FightState(placeId = level.place.second, isLoading = false)
        liveFightState.value = fightState
        roundCountDown().start()
    }

    private fun roundCountDown(): CountDownTimer {
        return object : CountDownTimer((ROUND_DURATION * 1000).toLong(), 1000) {
            override fun onTick(millisUntilEnd: Long) {
                fightState.timer = (millisUntilEnd / 1000 + 1).toInt()
                liveFightState.value = fightState
            }

            override fun onFinish() {
                fightState.timer = ROUND_DURATION
                fightState.inBreak = true
                fightState.round++
                liveFightState.value = fightState
            }
        }
    }

    fun beginPlayerLeftPunch() {
        player.animation = FightAnimation.LEFT_PUNCH
        player.animationRes = playerSprite.leftPunchMode
        livePlayerState.value = player
        handler.postDelayed({ checkIfPunch(isPlayer = true, isLeft = true) }, PLAYER_LEFT_DELAY)
    }

    fun beginPlayerRightPunch() {
        player.animation = FightAnimation.RIGHT_PUNCH
        player.animationRes = playerSprite.rightPunchMode
        livePlayerState.value = player
        handler.postDelayed({ checkIfPunch(isPlayer = true, isLeft = false) }, PLAYER_RIGHT_DELAY)
    }

    fun beginPlayerLeftDODGE() {
        player.animation = FightAnimation.LEFT_DODGE
        player.animationRes = playerSprite.leftDODGEMode
        livePlayerState.value = player
        handler.postDelayed({ returnToNormal(true) }, 1500)
    }

    fun beginPlayerRightDODGE() {
        player.animation = FightAnimation.RIGHT_DODGE
        player.animationRes = playerSprite.rightDODGEMode
        livePlayerState.value = player
        handler.postDelayed({ returnToNormal(true) }, 1500)
    }

    private fun returnToNormal(isPlayer: Boolean) {
        if (isPlayer) {
            player.animation = FightAnimation.NONE
            player.animationRes = playerSprite.normalMode
            livePlayerState.value = player
        } else {
            opponent.animation = FightAnimation.NONE
            opponent.animationRes = opponentStats.sprites.normalMode
            livePlayerState.value = opponent
        }
    }

    private fun beginOpponentLeftPunch() {
        handler.postDelayed({ checkIfPunch(isPlayer = false, isLeft = true) }, PLAYER_LEFT_DELAY) //TODO CHANGE DELAY
    }

    private fun beginOpponentRightPunch() {
        handler.postDelayed({ checkIfPunch(isPlayer = false, isLeft = false) }, PLAYER_RIGHT_DELAY) //TODO CHANGE DELAY
    }

    private fun checkIfPunch(isPlayer: Boolean, isLeft: Boolean) {
        if (isPlayer) {
            player.energyValue -= 5
            if (if (isLeft) !opponent.leftGuard else !opponent.rightGuard || opponent.energyValue == 0)
                successPunch(true, isLeft)
            else blockedPunch(true, isLeft)
        } else {
            opponent.energyValue -= 5
            if (if (isLeft) !player.leftGuard else !player.rightGuard || player.energyValue == 0)
                successPunch(false, isLeft)
            else blockedPunch(false, isLeft)
        }
    }

    private fun successPunch(isPlayer: Boolean, isLeft: Boolean) {
        if (isPlayer) {
            opponent.healthValue -= (if (isLeft) PLAYER_LEFT_FORCE / opponentStats.health * 10 else PLAYER_RIGHT_FORCE / opponentStats.health * 10)
            opponent.animation = if (isLeft) FightAnimation.LEFT_PUNCHED else FightAnimation.RIGHT_PUNCHED
            opponent.animationRes =
                    if (isLeft) opponentStats.sprites.leftPunchedMode else opponentStats.sprites.rightPunchedMode
            player.specialValue += 5
            player.animation = FightAnimation.NONE
            player.animationRes = playerSprite.normalMode
            if (opponent.healthValue <= 0) {
                fightState.winner = FightState.Winner.PLAYER
                player.animation = FightAnimation.VICTORY
                player.animationRes = playerSprite.victoryMode
                opponent.animation = FightAnimation.KO
                opponent.animationRes = opponentStats.sprites.KOMode
                liveFightState.value = fightState
                handler.removeCallbacksAndMessages(null)
            } else {
                handler.postDelayed({
                    opponent.animation = FightAnimation.NONE
                    opponent.animationRes = opponentStats.sprites.normalMode
                    liveOpponentState.value = opponent
                }, if (isLeft) PLAYER_LEFT_DELAY else PLAYER_RIGHT_DELAY)
            }
            liveOpponentState.value = opponent
            livePlayerState.value = player
        } else {
            opponent.animation = FightAnimation.NONE
            opponent.animationRes = opponentStats.sprites.normalMode
            player.animation = if (isLeft) FightAnimation.LEFT_PUNCHED else FightAnimation.RIGHT_PUNCHED
            player.animationRes = if (isLeft) playerSprite.leftPunchedMode else playerSprite.rightPunchedMode
            player.healthValue -= opponentStats.might / PLAYER_HEALTH * 10
            player.specialValue -= 10
            if (player.healthValue <= 0) {
                fightState.winner = opponentStats.name
                liveFightState.value = fightState
                handler.removeCallbacksAndMessages(null)
            } else {
                handler.postDelayed({
                    player.animation = FightAnimation.NONE
                    player.animationRes = playerSprite.normalMode
                    livePlayerState.value = player
                }, ((11 - opponentStats.might) * 100).toLong())
            }
            liveOpponentState.value = opponent
            livePlayerState.value = player
        }
    }

    private fun blockedPunch(isPlayer: Boolean, isLeft: Boolean) {
        if (isPlayer) {
            opponent.energyValue -= if (isLeft) PLAYER_LEFT_FORCE / opponentStats.energy * 10 else PLAYER_RIGHT_FORCE / opponentStats.energy * 10
            player.specialValue += 2
            liveOpponentState.value = opponent
            livePlayerState.value = player
        } else {
            player.energyValue -= opponentStats.might / PLAYER_ENERGY
            livePlayerState.value = player
        }
    }

    private fun initializeDagger(viewModel: FightViewModel) =
        UltraPunchWarriorApplication.appComponent.inject(viewModel)

    fun getLiveDataFightState() = liveFightState
    fun getLiveDataPlayerState() = livePlayerState
    fun getLiveDataOpponentState() = liveOpponentState
}
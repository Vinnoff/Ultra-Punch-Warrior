package balekouy.industries.punchwarrior.presentation.fight

import android.arch.lifecycle.MutableLiveData
import android.os.CountDownTimer
import android.os.Handler
import balekouy.industries.punchwarrior.R
import balekouy.industries.punchwarrior.data.Difficulty
import balekouy.industries.punchwarrior.data.models.Fighter
import balekouy.industries.punchwarrior.data.models.Level
import balekouy.industries.punchwarrior.di.UltraPunchWarriorApplication
import balekouy.industries.punchwarrior.presentation.BaseViewModel


class FightViewModel : BaseViewModel() {

    companion object {
        private const val PLAYER_LEFT_FORCE = 6
        private const val PLAYER_LEFT_SPEED = 7
        private const val PLAYER_LEFT_DELAY: Long = ((10 - PLAYER_LEFT_SPEED) * 100).toLong()
        private const val PLAYER_RIGHT_FORCE = 8
        private const val PLAYER_RIGHT_SPEED = 5
        private const val PLAYER_RIGHT_DELAY: Long = ((10 - PLAYER_RIGHT_SPEED) * 100).toLong()
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
            portrait = R.drawable.portrait_player,
            health = 100,
            energy = 100
        )
        opponent = FighterState(
            portrait = opponentStats.portraitRes,
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
        handler.postDelayed({ checkIfPunch(isPlayer = true, isLeft = true) }, PLAYER_LEFT_DELAY)
    }

    fun beginPlayerRightPunch() {
        handler.postDelayed({ checkIfPunch(isPlayer = true, isLeft = false) }, PLAYER_RIGHT_DELAY)
    }

    private fun beginOpponentLeftPunch() {
        handler.postDelayed({ checkIfPunch(isPlayer = false, isLeft = true) }, PLAYER_LEFT_DELAY)
    }

    private fun beginOpponentRightPunch() {
        handler.postDelayed({ checkIfPunch(isPlayer = false, isLeft = false) }, PLAYER_RIGHT_DELAY)
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

            player.specialValue += 5
            liveOpponentState.value = opponent
            livePlayerState.value = player
            if (opponent.healthValue <= 0) {
                fightState.winner = FightState.Winner.PLAYER
                liveFightState.value = fightState
                handler.removeCallbacksAndMessages(null)
            }
        } else {
            player.healthValue -= opponentStats.might / PLAYER_HEALTH * 10
            player.specialValue -= 10
            livePlayerState.value = player
            if (player.healthValue <= 0) {
                fightState.winner = opponentStats.name
                liveFightState.value = fightState
                handler.removeCallbacksAndMessages(null)
            }
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
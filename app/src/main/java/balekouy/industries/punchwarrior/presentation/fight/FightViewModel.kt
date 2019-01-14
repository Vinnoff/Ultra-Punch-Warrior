package balekouy.industries.punchwarrior.presentation.fight

import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import balekouy.industries.punchwarrior.R
import balekouy.industries.punchwarrior.data.Difficulty
import balekouy.industries.punchwarrior.data.models.Level
import balekouy.industries.punchwarrior.di.UltraPunchWarriorApplication
import balekouy.industries.punchwarrior.presentation.BaseViewModel

class FightViewModel : BaseViewModel() {

    companion object {
        private const val LEFT_FORCE = 6
        private const val LEFT_SPEED = 7
        private const val LEFT_DELAY: Long = ((10 - LEFT_SPEED) * 100).toLong()
        private const val RIGHT_FORCE = 8
        private const val RIGHT_SPEED = 5
        private const val RIGHT_DELAY: Long = ((10 - RIGHT_SPEED) * 100).toLong()
        private const val HEALTH = 7
        private const val DETERMINATION = 6
    }

    private val liveFightState: MutableLiveData<FightState> = MutableLiveData()
    private val livePlayerState: MutableLiveData<FighterState> = MutableLiveData()
    private val liveOpponentState: MutableLiveData<FighterState> = MutableLiveData()
    private val handler = Handler()

    private lateinit var level: Level
    private lateinit var difficulty: Difficulty
    private lateinit var player: FighterState
    private lateinit var opponent: FighterState
    private var fightState: FightState

    init {
        initializeDagger(this)
        fightState = FightState(isLoading = true)
        liveFightState.value = fightState
    }

    fun configureFight(level: Level, difficultyId: Int) {
        this.level = level
        this.difficulty = Difficulty.withId(difficultyId) ?: Difficulty.NORMAL
        player = FighterState(portrait = R.drawable.portrait_player)
        opponent = FighterState(portrait = level.fighter.second.portraitRes)
        livePlayerState.value = player
        liveOpponentState.value = opponent
        fightState = FightState(placeId = level.place.second, isLoading = false)
        liveFightState.value = fightState
    }

    fun beginPlayerLeftPunch() {
        handler.postDelayed({ checkIfPunch(isPlayer = true, isLeft = true) }, LEFT_DELAY)
    }

    fun beginPlayerRightPunch() {
        handler.postDelayed({ checkIfPunch(isPlayer = true, isLeft = false) }, RIGHT_DELAY)
    }

    private fun beginOpponentLeftPunch() {
        handler.postDelayed({ checkIfPunch(isPlayer = false, isLeft = true) }, LEFT_DELAY)
    }

    private fun beginOpponentRightPunch() {
        handler.postDelayed({ checkIfPunch(isPlayer = false, isLeft = false) }, RIGHT_DELAY)
    }

    private fun checkIfPunch(isPlayer: Boolean, isLeft: Boolean) {
        if (isPlayer) {
            if (if (isLeft) !opponent.leftGuard else !opponent.rightGuard || opponent.energy == 0)
                successPunch(true, isLeft)
            else blockedPunch(true, isLeft)
        } else {
            if (if (isLeft) !player.leftGuard else !player.rightGuard || player.energy == 0)
                successPunch(false, isLeft)
            else blockedPunch(false, isLeft)
        }
    }

    private fun successPunch(isPlayer: Boolean, isLeft: Boolean) {
        if (isPlayer) {
            opponent.health -= (if (isLeft) LEFT_FORCE else RIGHT_FORCE)
            if (opponent.health <= 0) {
                fightState.winner = FightState.Winner.PLAYER
                liveFightState.value = fightState
                return
            }
            if (player.special <= 100 - 5) player.special += 5 else player.special = 100
            liveOpponentState.value = opponent
            livePlayerState.value = player
        } else {
            player.health -= (if (isLeft) LEFT_FORCE else RIGHT_FORCE)
            if (player.health <= 0) {
                fightState.winner = FightState.Winner.OPPONENT
                liveFightState.value = fightState
                return
            }
            if (player.special >= 0 + 10) player.special -= 10 else player.special = 0
            livePlayerState.value = player
        }
    }

    private fun blockedPunch(isPlayer: Boolean, isLeft: Boolean) {
        if (isPlayer) {
            when {
                isLeft && opponent.energy >= 0 + LEFT_FORCE -> opponent.energy -= LEFT_FORCE / 2
                isLeft && opponent.energy < 0 + LEFT_FORCE -> opponent.energy = 0
                !isLeft && opponent.energy >= 0 + RIGHT_FORCE -> opponent.energy -= RIGHT_FORCE / 2
                !isLeft && opponent.energy < 0 + RIGHT_FORCE -> opponent.energy = 0
            }
            liveOpponentState.value = opponent
        } else {
            player.energy -= if (player.energy >= 0 + level.fighter.second.might) level.fighter.second.might / 2 else 0
            livePlayerState.value = player
        }
    }


    private fun initializeDagger(viewModel: FightViewModel) =
        UltraPunchWarriorApplication.appComponent.inject(viewModel)

    fun getLiveDataFightState() = liveFightState
    fun getLiveDataPlayerState() = livePlayerState
    fun getLiveDataOpponentState() = liveOpponentState
}
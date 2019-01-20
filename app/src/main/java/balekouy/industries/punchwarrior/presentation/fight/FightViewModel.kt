package balekouy.industries.punchwarrior.presentation.fight

import android.arch.lifecycle.MutableLiveData
import android.os.CountDownTimer
import android.os.Handler
import balekouy.industries.punchwarrior.data.Difficulty
import balekouy.industries.punchwarrior.data.models.Fighter
import balekouy.industries.punchwarrior.data.models.Level
import balekouy.industries.punchwarrior.data.models.Score
import balekouy.industries.punchwarrior.data.repository.DataMapper
import balekouy.industries.punchwarrior.di.UltraPunchWarriorApplication
import balekouy.industries.punchwarrior.domain.DataResponse
import balekouy.industries.punchwarrior.domain.ErrorResponse
import balekouy.industries.punchwarrior.domain.scores.LevelsUseCase
import balekouy.industries.punchwarrior.domain.scores.ScoresUseCase
import balekouy.industries.punchwarrior.presentation.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


class FightViewModel : BaseViewModel(FightViewModel::class.java.simpleName) {

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

    @Inject
    lateinit var scoresUseCase: ScoresUseCase
    @Inject
    lateinit var levelsUseCase: LevelsUseCase
    private val compositeDisposable = CompositeDisposable()

    private val liveFightState: MutableLiveData<FightState> = MutableLiveData()
    private val livePlayerState: MutableLiveData<FighterState> = MutableLiveData()
    private val liveOpponentState: MutableLiveData<FighterState> = MutableLiveData()
    private val liveDataState: MutableLiveData<FightDataState> = MutableLiveData()
    private val liveUnlockedState: MutableLiveData<FightDataState> = MutableLiveData()
    private val playerHandler = Handler()
    private val opponentHandler = Handler()

    private lateinit var level: Level
    private lateinit var difficulty: Difficulty
    private lateinit var player: FighterState
    private lateinit var opponent: FighterState
    private lateinit var opponentStats: Fighter
    private lateinit var countDownTimer: CountDownTimer

    private var fightState: FightState
    private val playerSprite = DataMapper.createSpriteNames("player")
    private var opponentDelay: Long = 0

    init {
        initializeDagger(this)
        fightState = FightState()
        liveDataState.value = FightDataState(isDone = false, isError = false)
        liveFightState.value = fightState
        log("init() $fightState")
    }


    fun configureFight(level: Level, difficultyId: Int) {
        this.level = level
        this.opponentStats = level.fighter
        this.opponentDelay = ((11 - opponentStats.speed) * 100).toLong()
        this.difficulty = Difficulty.withId(difficultyId) ?: Difficulty.NORMAL
        player = FighterState(
            portraitRes = playerSprite.portrait,
            animationRes = playerSprite.normalMode
        )
        opponent = FighterState(
            portraitRes = opponentStats.sprites.portrait,
            animationRes = opponentStats.sprites.normalMode
        )
        livePlayerState.value = player
        liveOpponentState.value = opponent
        fightState = FightState(placeRes = level.place.second)
        liveFightState.value = fightState
        log("configureFight() $fightState")
        countDownTimer = roundCountDown().start()
    }

    private fun roundCountDown(): CountDownTimer {
        return object : CountDownTimer((ROUND_DURATION * 1000).toLong(), 1000) {
            override fun onTick(millisUntilEnd: Long) {
                fightState.timer = (millisUntilEnd / 1000 + 1).toInt()
                dumbIAMove()
                liveFightState.value = fightState
                log("onTick() $fightState")
            }

            override fun onFinish() {
                fightState.timer = 0
                if (fightState.winner == FightState.Winner.NONE) {
                    fightState.inBreak = true
                    liveFightState.value = fightState
                    log("onFinish() $fightState")
                }
            }
        }
    }

    fun beginPlayerLeftPunch() {

        if (player.energyValue > 0) {
            player.animation = FightAnimation.LEFT_PUNCH
            player.animationRes = playerSprite.leftPunchMode
            playerHandler.postDelayed({ checkIfPunch(isPlayer = true, isLeft = true) }, PLAYER_LEFT_DELAY)
        } else {
            player.animation = FightAnimation.TIRED
            player.animationRes = playerSprite.tiredMode
        }
        player.energyValue -= 5
        livePlayerState.value = player
    }

    fun beginPlayerRightPunch() {
        if (player.energyValue > 0) {
            player.animation = FightAnimation.RIGHT_PUNCH
            player.animationRes = playerSprite.rightPunchMode
            playerHandler.postDelayed({ checkIfPunch(isPlayer = true, isLeft = false) }, PLAYER_RIGHT_DELAY)
        } else {
            player.animation = FightAnimation.TIRED
            player.animationRes = playerSprite.tiredMode
        }
        player.energyValue -= 5
        livePlayerState.value = player
    }

    fun beginPlayerLeftDODGE() {
        if (player.energyValue > 0) {
            player.animation = FightAnimation.LEFT_DODGE
            player.animationRes = playerSprite.leftDODGEMode
            player.leftDODGE = true
            playerHandler.postDelayed({ returnToNormal(true) }, 500)
        } else {
            player.animation = FightAnimation.TIRED
            player.animationRes = playerSprite.tiredMode
        }
        player.energyValue -= 5
        livePlayerState.value = player
    }

    fun beginPlayerRightDODGE() {
        if (player.energyValue > 0) {
            player.animation = FightAnimation.RIGHT_DODGE
            player.animationRes = playerSprite.rightDODGEMode
            player.rightDODGE = true
            playerHandler.postDelayed({ returnToNormal(true) }, 500)
        } else {
            player.animation = FightAnimation.TIRED
            player.animationRes = playerSprite.tiredMode
        }
        player.energyValue -= 5
        livePlayerState.value = player
    }

    private fun returnToNormal(isPlayer: Boolean) {
        if (isPlayer) {
            player.leftDODGE = false
            player.rightDODGE = false
            player.animation = FightAnimation.NONE
            player.animationRes = playerSprite.normalMode
            livePlayerState.value = player
        } else {
            opponent.leftDODGE = false
            opponent.rightDODGE = false
            opponent.animation = FightAnimation.NONE
            opponent.animationRes = opponentStats.sprites.normalMode
            liveOpponentState.value = opponent
        }
    }

    private fun dumbIAMove() {
        val i = Random().nextInt(10)
        when {
            i < 1 -> beginOpponentLeftPunch()
            i < 2 -> beginOpponentRightPunch()
            i < 4 -> beginOpponentLeftDODGE()
            i < 6 -> beginOpponentRightDODGE()
        }
    }

    private fun beginOpponentLeftPunch() {
        if (opponent.energyValue > 0) {
            opponent.animation = FightAnimation.LEFT_PUNCH
            opponent.animationRes = opponentStats.sprites.leftPunchMode
            opponentHandler.postDelayed({ checkIfPunch(isPlayer = false, isLeft = true) }, opponentDelay)
        } else {
            opponent.animation = FightAnimation.TIRED
            opponent.animationRes = opponentStats.sprites.tiredMode
        }
        opponent.energyValue -= 5
        liveOpponentState.value = opponent
    }

    private fun beginOpponentRightPunch() {
        if (opponent.energyValue > 0) {
            opponent.animation = FightAnimation.RIGHT_PUNCH
            opponent.animationRes = opponentStats.sprites.rightPunchMode
            opponentHandler.postDelayed({ checkIfPunch(isPlayer = false, isLeft = false) }, opponentDelay)
        } else {
            opponent.animation = FightAnimation.TIRED
            opponent.animationRes = opponentStats.sprites.tiredMode
        }
        opponent.energyValue -= 5
        liveOpponentState.value = opponent
    }

    private fun beginOpponentLeftDODGE() {
        if (opponent.energyValue > 0) {
            opponent.animation = FightAnimation.LEFT_DODGE
            opponent.animationRes = opponentStats.sprites.leftDODGEMode
            opponent.leftDODGE = true
            opponentHandler.postDelayed({ returnToNormal(false) }, 500)
        } else {
            opponent.animation = FightAnimation.TIRED
            opponent.animationRes = opponentStats.sprites.tiredMode
        }
        opponent.energyValue -= 5
        liveOpponentState.value = opponent
    }

    private fun beginOpponentRightDODGE() {
        if (opponent.energyValue > 0) {
            opponent.animation = FightAnimation.RIGHT_DODGE
            opponent.animationRes = opponentStats.sprites.rightDODGEMode
            opponent.rightDODGE = true
            opponentHandler.postDelayed({ returnToNormal(false) }, 500)
        } else {
            opponent.animation = FightAnimation.TIRED
            opponent.animationRes = opponentStats.sprites.tiredMode
        }
        opponent.energyValue -= 5
        liveOpponentState.value = opponent
    }

    private fun checkIfPunch(isPlayer: Boolean, isLeft: Boolean) {
        if (isPlayer) {
            if (if (isLeft) !opponent.leftDODGE else !opponent.rightDODGE || opponent.energyValue == 0)
                successPunch(true, isLeft)
            else DODGEdPunch(false)
        } else {
            if (if (isLeft) !player.leftDODGE else !player.rightDODGE || player.energyValue == 0)
                successPunch(false, isLeft)
            else DODGEdPunch(true)
        }
    }

    private fun successPunch(isPlayer: Boolean, isLeft: Boolean) {
        if (isPlayer) {
            addToScore(100)
            fightState.multiplicator += 0.2
            player.animation = FightAnimation.NONE
            player.animationRes = playerSprite.normalMode
            opponent.animation = if (isLeft) FightAnimation.LEFT_PUNCHED else FightAnimation.RIGHT_PUNCHED
            opponent.animationRes =
                    if (isLeft) opponentStats.sprites.leftPunchedMode else opponentStats.sprites.rightPunchedMode
            opponent.healthValue -= if (isLeft) (PLAYER_LEFT_FORCE.toDouble() / opponentStats.health.toDouble() * 10).toInt() else (PLAYER_RIGHT_FORCE.toDouble() / opponentStats.health.toDouble() * 10).toInt()
            player.specialValue += 5
            if (opponent.healthValue <= 0) {
                fightState.winner = FightState.Winner.PLAYER
                player.animation = FightAnimation.VICTORY
                player.animationRes = playerSprite.victoryMode
                opponent.animation = FightAnimation.KO
                opponent.animationRes = opponentStats.sprites.KOMode
                endGame()
                liveFightState.value = fightState
                log("playerWin() $fightState")
            } else {
                opponentHandler.postDelayed({
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
            player.healthValue -= (opponentStats.might.toDouble() / PLAYER_HEALTH.toDouble() * 10).toInt()
            player.specialValue -= 10
            if (player.healthValue <= 0) {
                fightState.winner = opponentStats.name
                opponent.animation = FightAnimation.VICTORY
                opponent.animationRes = opponentStats.sprites.victoryMode
                player.animation = FightAnimation.KO
                player.animationRes = playerSprite.KOMode
                endGame()
                liveFightState.value = fightState
                log("opponentWin() $fightState")
            } else {
                playerHandler.postDelayed({
                    player.animation = FightAnimation.NONE
                    player.animationRes = playerSprite.normalMode
                    livePlayerState.value = player
                }, ((11 - opponentStats.might) * 100).toLong())
            }
            liveOpponentState.value = opponent
            livePlayerState.value = player
        }
    }

    private fun endGame() {
        playerHandler.removeCallbacksAndMessages(null)
        opponentHandler.removeCallbacksAndMessages(null)
        countDownTimer.cancel()
        addToScore(player.healthValue * 10 + player.energyValue * 10)
        val unlockFighterDisposable = levelsUseCase.unlockLevel(level.id + 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                when (response) {
                    is DataResponse -> {
                        liveUnlockedState.value = liveUnlockedState.value?.copy(isDone = true, isError = false)
                    }
                    is ErrorResponse -> {
                        liveUnlockedState.value = liveUnlockedState.value?.copy(isDone = true, isError = true)
                    }
                }
            }) { t: Throwable? ->
                log(t.toString())
            }
        compositeDisposable.add(unlockFighterDisposable)
    }

    private fun DODGEdPunch(isPlayer: Boolean) {
        if (isPlayer) {
            player.specialValue += 2
            livePlayerState.value = player
            addToScore(50)
        }
    }

    fun setName(name: String) {
        val scoreDisposable =
            scoresUseCase.saveScore(Score(null, name, level.fighter, difficulty, fightState.score.toInt()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    liveDataState.value = FightDataState(isDone = true, isError = false)
                }

                override fun onError(t: Throwable) {
                    liveDataState.value = FightDataState(isDone = true, isError = true)
                    log(t.toString())
                }
            })

        compositeDisposable.add(scoreDisposable)
    }

    fun newRound() {
        if (fightState.winner == FightState.Winner.NONE) {
            fightState.round++
            fightState.inBreak = false
            fightState.timer = ROUND_DURATION
            opponent.healthValue += 10
            opponent.energyValue += 30
            player.healthValue += 10
            player.energyValue += 30
            liveFightState.value = fightState
            log("newRound() $fightState")
            livePlayerState.value = player
            liveOpponentState.value = opponent
            countDownTimer = roundCountDown().start()
        }
    }

    private fun addToScore(i: Int) {
        fightState.score += i * fightState.multiplicator * difficulty.multiplicator
    }


    private fun initializeDagger(viewModel: FightViewModel) =
        UltraPunchWarriorApplication.appComponent.inject(viewModel)

    fun getLiveDataFightState() = liveFightState
    fun getLiveDataPlayerState() = livePlayerState
    fun getLiveDataOpponentState() = liveOpponentState
    fun getLiveDataViewState() = liveDataState
    fun getLiveDataUnlockedState() = liveUnlockedState
}

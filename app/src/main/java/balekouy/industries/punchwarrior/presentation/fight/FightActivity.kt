package balekouy.industries.punchwarrior.presentation.fight

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import balekouy.industries.punchwarrior.R
import balekouy.industries.punchwarrior.data.models.Level
import balekouy.industries.punchwarrior.presentation.BaseActivity
import balekouy.industries.punchwarrior.presentation.UPWUtils.Companion.getDrawableFromIdentifier
import balekouy.industries.punchwarrior.presentation.UPWUtils.Companion.getRessourceIdFromIdentifier
import balekouy.industries.punchwarrior.presentation.fight.FightAnimation.*
import balekouy.industries.punchwarrior.presentation.home.HomeActivity
import kotlinx.android.synthetic.main.activity_fight.*


class FightActivity : BaseActivity(FightActivity::class.java.simpleName, R.layout.activity_fight),
    EndDialog.OnContinueClickListener {
    companion object {
        private const val LEVEL = "level"
        private const val DIFFICULTY_ID = "difficulty_id"

        fun newIntent(context: Context, level: Level, difficultyId: Int): Intent {
            val intent = Intent(context, FightActivity::class.java)
            intent.putExtra(LEVEL, level)
            intent.putExtra(DIFFICULTY_ID, difficultyId)
            return intent
        }
    }

    private lateinit var viewModel: FightViewModel
    private lateinit var endDialog: EndDialog
    private var handler = Handler()
    private var unlocked = false


    override fun onCreate(savedInstanceState: Bundle?) {
        showLoading()
        super.onCreate(savedInstanceState)
        viewModel.configureFight(
            intent.extras.getSerializable(LEVEL) as Level,
            intent.extras.getInt(DIFFICULTY_ID)
        )
    }

    override fun initUI() {
        left_punch.setOnClickListener {
            viewModel.beginPlayerLeftPunch()
            disablePlayerControls()
        }
        right_punch.setOnClickListener {
            viewModel.beginPlayerRightPunch()
            disablePlayerControls()
        }
        left_dodge.setOnClickListener {
            viewModel.beginPlayerLeftDODGE()
            disablePlayerControls()
        }
        right_dodge.setOnClickListener {
            viewModel.beginPlayerRightDODGE()
            disablePlayerControls()
        }
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(FightViewModel::class.java)
        viewModel.let { lifecycle.addObserver(it) }
    }

    override fun initObservers() {
        viewModel.getLiveDataFightState().observe(this, Observer { viewState ->
            viewState?.let {
                when {
                    it.winner == FightState.Winner.PLAYER -> handler.postDelayed({ showPlayerWinner(it.score) }, 6000)
                    it.winner != FightState.Winner.NONE -> handler.postDelayed({
                        showOpponentWinner(
                            it.winner,
                            it.score
                        )
                    }, 6000)
                    it.inBreak -> showRoundChange(it.round)
                    else -> {
                        fight_background.setImageResource(getRessourceIdFromIdentifier(baseContext, it.placeRes))
                        fight_timer.text = it.timer.toString()
                        fight_round.text = it.round.toString()
                    }
                }
            }
        })

        viewModel.getLiveDataPlayerState().observe(this, Observer { playerState ->
            playerState?.let {
                player_image.setImageDrawable(getDrawableFromIdentifier(baseContext, it.portraitRes))
                player_health_bar.progress = it.healthValue
                player_energy_bar.progress = it.energyValue
                player_special_bar.progress = it.specialValue
                showPlayerAnimation(it.animation, it.animationRes)
            }
        })

        viewModel.getLiveDataOpponentState().observe(this, Observer { opponentState ->
            opponentState?.let {
                opponent_image.setImageDrawable(getDrawableFromIdentifier(baseContext, it.portraitRes))
                opponent_health_bar.progress = it.healthValue
                opponent_energy_bar.progress = it.energyValue
                showOpponentAnimation(it.animation, it.animationRes)
            }
        })

        viewModel.getLiveDataViewState().observe(this, Observer { baseState ->
            baseState?.let {
                when {
                    it.isError -> {
                        Toast.makeText(baseContext, getString(R.string.genneric_errror), Toast.LENGTH_LONG).show()
                        HomeActivity.goBackToHome(baseContext) //that's racist ?
                    }
                    it.isDone -> HomeActivity.goBackToHome(baseContext) //that's racist ?
                }
            }
        })

        viewModel.getLiveDataUnlockedState().observe(this, Observer { unlockedState ->
            unlockedState?.let {
                when {
                    it.isDone -> {
                        unlocked = !it.isError
                    }
                }
            }
        })
    }


    private fun showPlayerAnimation(state: FightAnimation, animationRes: String) {
        when (state) {
            NONE -> {
                player_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
                enablePlayerControls()
            }
            TIRED -> {
                player_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
                enablePlayerControls()
                Toast.makeText(baseContext, "You're tired !!!", Toast.LENGTH_SHORT).show()
            }
            LEFT_PUNCH,
            RIGHT_PUNCH,
            LEFT_DODGE,
            RIGHT_DODGE,
            LEFT_PUNCHED,
            RIGHT_PUNCHED,
            KO -> player_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
            VICTORY -> handler.postDelayed({
                player_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
                player_sprite.alpha = 1F
            }, 3000)
        }
    }

    private fun showOpponentAnimation(state: FightAnimation, animationRes: String) {
        when (state) {
            NONE,
            TIRED,
            LEFT_PUNCH,
            RIGHT_PUNCH,
            LEFT_DODGE,
            RIGHT_DODGE,
            LEFT_PUNCHED,
            RIGHT_PUNCHED,
            KO -> {
                opponent_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
            }
            VICTORY -> {
                handler.postDelayed({
                    opponent_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
                    player_sprite.alpha = 1F
                }, 3000)
            }
        }
    }

    private fun showRoundChange(round: Int) {
        Toast.makeText(baseContext, "Round Terminé", Toast.LENGTH_SHORT).show()
        viewModel.newRound()
    }

    private fun showPlayerWinner(score: Int) {
        Toast.makeText(baseContext, "Player Win", Toast.LENGTH_SHORT).show()
        disablePlayerControls()
        log("showPlayerWinner")
        endDialog = EndDialog.newInstance(true, unlocked, score)
        endDialog.continueClickListener = this
        endDialog.show(supportFragmentManager, null)
    }

    private fun showOpponentWinner(opponentName: String, score: Int) {
        Toast.makeText(baseContext, "$opponentName Win", Toast.LENGTH_SHORT).show()
        disablePlayerControls()
    }

    private fun enablePlayerControls() {
        left_punch.visibility = View.VISIBLE
        right_punch.visibility = View.VISIBLE
        left_dodge.visibility = View.VISIBLE
        right_dodge.visibility = View.VISIBLE
    }

    private fun disablePlayerControls() {
        left_punch.visibility = View.GONE
        right_punch.visibility = View.GONE
        left_dodge.visibility = View.GONE
        right_dodge.visibility = View.GONE
    }

    override fun onBackPressed() {}

    override fun onContinueClick(name: String) {
        viewModel.setName(name)
    }
}
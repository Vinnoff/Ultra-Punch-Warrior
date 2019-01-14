package balekouy.industries.punchwarrior.presentation.fight

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import balekouy.industries.punchwarrior.R
import balekouy.industries.punchwarrior.data.models.Level
import balekouy.industries.punchwarrior.presentation.BaseActivity
import kotlinx.android.synthetic.main.activity_fight.*


class FightActivity : BaseActivity(R.layout.activity_fight) {
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
    private var winner = false

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
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(FightViewModel::class.java)
        viewModel.let { lifecycle.addObserver(it) }
    }

    override fun initObservers() {
        viewModel.getLiveDataFightState().observe(this, Observer { viewState ->
            viewState?.let {
                when {
                    it.isLoading -> showLoading()
                    it.inBreak -> showRoundChange(it.round)
                    it.winner == FightState.Winner.PLAYER -> showPlayerWinner()
                    it.winner != FightState.Winner.NONE -> showOpponentWinner(it.winner)
                    else -> {
                        fight_background.setImageResource(it.placeId)
                        fight_timer.text = it.timer.toString()
                        fight_round.text = it.round.toString()
                    }
                }
            }
        })

        viewModel.getLiveDataPlayerState().observe(this, Observer { playerState ->
            playerState?.let {
                player_image.setImageDrawable(ContextCompat.getDrawable(baseContext, it.portrait))
                player_health_bar.progress = it.healthValue
                player_energy_bar.progress = it.energyValue
                player_special_bar.progress = it.specialValue
                when {
                    it.animation == PunchAnimation.NONE -> enablePlayerControls()
                    else -> showPlayerAnimation(it.animation)
                }
            }
        })

        viewModel.getLiveDataOpponentState().observe(this, Observer { opponentState ->
            opponentState?.let {
                opponent_image.setImageDrawable(ContextCompat.getDrawable(baseContext, it.portrait))
                opponent_health_bar.progress = it.healthValue
                opponent_energy_bar.progress = it.energyValue
            }
        })
    }

    private fun showPlayerAnimation(state: PunchAnimation) {
        when (state) {
            PunchAnimation.LEFT_PUNCH -> Toast.makeText(baseContext, state.name, Toast.LENGTH_SHORT).show()
            PunchAnimation.RIGHT_PUNCH -> Toast.makeText(baseContext, state.name, Toast.LENGTH_SHORT).show()
            PunchAnimation.LEFT_BLOCK -> Toast.makeText(baseContext, state.name, Toast.LENGTH_SHORT).show()
            PunchAnimation.RIGHT_BLOCK -> Toast.makeText(baseContext, state.name, Toast.LENGTH_SHORT).show()
            PunchAnimation.LEFT_PUNCHED -> Toast.makeText(baseContext, state.name, Toast.LENGTH_SHORT).show()
            PunchAnimation.RIGHT_PUNCHED -> Toast.makeText(baseContext, state.name, Toast.LENGTH_SHORT).show()
            PunchAnimation.KO -> Toast.makeText(baseContext, state.name, Toast.LENGTH_SHORT).show()
            PunchAnimation.VICTORY -> Toast.makeText(baseContext, state.name, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showRoundChange(round: Int) {
        Toast.makeText(baseContext, "Round Terminé", Toast.LENGTH_SHORT).show()
    }

    private fun showPlayerWinner() {
        winner = true
        Toast.makeText(baseContext, "Player Win", Toast.LENGTH_SHORT).show()
        disablePlayerControls()
    }

    private fun showOpponentWinner(opponentName: String) {
        winner = true
        Toast.makeText(baseContext, "$opponentName Win", Toast.LENGTH_SHORT).show()
        disablePlayerControls()
    }

    private fun enablePlayerControls() {
        left_punch.visibility = View.VISIBLE
        right_punch.visibility = View.VISIBLE
    }

    private fun disablePlayerControls() {
        left_punch.visibility = View.GONE
        right_punch.visibility = View.GONE
    }
}
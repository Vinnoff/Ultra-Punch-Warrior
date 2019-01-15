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
import kotlinx.android.synthetic.main.activity_fight.*
import pl.droidsonroids.gif.GifDrawable


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
    private var playerGif: GifDrawable? = null
    private var opponentGif: GifDrawable? = null
    private var winner = false
    private var handler = Handler()


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
        player_sprite.setImageDrawable(playerGif)
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
    }


    private fun showPlayerAnimation(state: FightAnimation, animationRes: String) {
        when (state) {
            NONE -> {
                player_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
                enablePlayerControls()
            }
            LEFT_PUNCH -> {
                player_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
            }
            RIGHT_PUNCH -> {
                player_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
            }
            LEFT_DODGE -> {
                player_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
            }
            RIGHT_DODGE -> {
                player_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
            }
            LEFT_PUNCHED -> {
                player_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
            }
            RIGHT_PUNCHED -> {
                player_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
            }
            KO, VICTORY -> {
                handler.postDelayed({
                    player_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
                    player_sprite.alpha = 1F
                }, 3000)

            }
        }
    }

    private fun showOpponentAnimation(state: FightAnimation, animationRes: String) {
        when (state) {
            NONE -> {
                opponent_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
            }
            LEFT_PUNCH -> {
                opponent_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
            }
            RIGHT_PUNCH -> {
                opponent_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
            }
            LEFT_DODGE -> {
                opponent_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
            }
            RIGHT_DODGE -> {
                opponent_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
            }
            LEFT_PUNCHED -> {
                opponent_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
            }
            RIGHT_PUNCHED -> {
                opponent_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
            }
            KO -> {
                opponent_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
            }
            VICTORY -> {
                opponent_sprite.setImageResource(getRessourceIdFromIdentifier(baseContext, animationRes))
            }
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
        left_dodge.visibility = View.VISIBLE
        right_dodge.visibility = View.VISIBLE
    }

    private fun disablePlayerControls() {
        left_punch.visibility = View.GONE
        right_punch.visibility = View.GONE
        left_dodge.visibility = View.GONE
        right_dodge.visibility = View.GONE
    }
}
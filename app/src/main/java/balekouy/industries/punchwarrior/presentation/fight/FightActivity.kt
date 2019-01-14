package balekouy.industries.punchwarrior.presentation.fight

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.view.View
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
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.configureFight(
            intent.extras.getSerializable(LEVEL) as Level,
            intent.extras.getInt(DIFFICULTY_ID)
        )
    }

    override fun initUI() {
        left_punch.setOnClickListener {
            viewModel.beginPlayerLeftPunch()
            left_punch.visibility = View.GONE
            right_punch.visibility = View.GONE
        }
        right_punch.setOnClickListener {
            viewModel.beginPlayerRightPunch()
            left_punch.visibility = View.GONE
            right_punch.visibility = View.GONE
        }
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(FightViewModel::class.java)
        viewModel.let { lifecycle.addObserver(it) }
    }

    override fun initObservers() {
        viewModel.getLiveDataFightState().observe(this, Observer { viewState ->
            viewState?.let {
                if (it.isLoading) showLoading()
                else {
                    fight_root.background = ContextCompat.getDrawable(baseContext, it.placeId)
                    fight_timer.text = it.timer.toString()
                    fight_round.text = it.round.toString()
                }
            }
        })

        viewModel.getLiveDataPlayerState().observe(this, Observer { playerState ->
            playerState?.let {
                player_image.setImageDrawable(ContextCompat.getDrawable(baseContext, it.portrait))
                player_health_bar.progress = it.health
                player_energy_bar.progress = it.energy
                player_special_bar.progress = it.special
            }
        })

        viewModel.getLiveDataOpponentState().observe(this, Observer { opponentState ->
            opponentState?.let {
                opponent_image.setImageDrawable(ContextCompat.getDrawable(baseContext, it.portrait))
                opponent_health_bar.progress = it.health
                opponent_energy_bar.progress = it.energy
                handler.postDelayed({ left_punch.visibility = View.VISIBLE }, 1000)
                handler.postDelayed({ right_punch.visibility = View.VISIBLE }, 1000)
            }
        })
    }
}
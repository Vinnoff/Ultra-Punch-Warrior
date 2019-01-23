package balekouy.industries.punchwarrior.presentation.home

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import balekouy.industries.punchwarrior.R
import balekouy.industries.punchwarrior.presentation.BaseActivity
import balekouy.industries.punchwarrior.presentation.historymode.HistoryModeActivity
import balekouy.industries.punchwarrior.presentation.levelselection.LvlSelectActivity
import balekouy.industries.punchwarrior.presentation.scores.ScoresActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity(HomeActivity::class.java.simpleName, R.layout.activity_home) {
    companion object {
        private fun newIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }

        fun goBackToHome(context: Context) {
            val intent = HomeActivity.newIntent(context)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra(PLAY_INTRO, false)
            context.startActivity(intent)
        }

        private const val PLAY_INTRO = "play_intro"
    }

    private lateinit var viewModel: HomeViewModel

    override fun initUI() {
        if (intent.getBooleanExtra(PLAY_INTRO, true)) playIntro()
        else {
            playMusic()
            displayInterface()
        }

        scores_button.setOnClickListener { startActivity(ScoresActivity.newIntent(this)) }
        levels_button.setOnClickListener { startActivity(LvlSelectActivity.newIntent(this)) }
        history_mode_button.setOnClickListener { startActivity(HistoryModeActivity.newIntent(this)) }
    }

    private fun playIntro() {
        video_view.setVideoURI(Uri.parse("android.resource://$packageName/${R.raw.intro}"))
        video_view.start()
        video_view.setOnPreparedListener { playMusic() }
        video_view.setOnCompletionListener {
            displayInterface()
        }
    }

    private fun playMusic() {
        startMusic(R.raw.intro_sound)
    }

    private fun displayInterface() {
        scores_button.visibility = View.VISIBLE
        levels_button.visibility = View.VISIBLE
        video_view.visibility = View.GONE
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

}

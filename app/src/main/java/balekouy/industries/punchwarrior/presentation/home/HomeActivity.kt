package balekouy.industries.punchwarrior.presentation.home

import android.content.Context
import android.content.Intent
import balekouy.industries.punchwarrior.R
import balekouy.industries.punchwarrior.presentation.BaseActivity
import balekouy.industries.punchwarrior.presentation.historymode.HistoryModeActivity
import balekouy.industries.punchwarrior.presentation.levelselection.LvlSelectActivity
import balekouy.industries.punchwarrior.presentation.scores.ScoresActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity(R.layout.activity_home) {
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }

        fun goBackToHome(context: Context) {
            val intent = HomeActivity.newIntent(context)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    override fun initUI() {
        scores_button.setOnClickListener { startActivity(ScoresActivity.newIntent(this)) }
        levels_button.setOnClickListener { startActivity(LvlSelectActivity.newIntent(this)) }
        history_mode_button.setOnClickListener { startActivity(HistoryModeActivity.newIntent(this))}
    }
}

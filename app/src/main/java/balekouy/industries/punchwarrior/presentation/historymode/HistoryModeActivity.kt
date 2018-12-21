package balekouy.industries.punchwarrior.presentation.historymode

import android.content.Context
import android.content.Intent
import balekouy.industries.punchwarrior.R
import balekouy.industries.punchwarrior.presentation.BaseActivity

class HistoryModeActivity : BaseActivity(R.layout.activity_history_mode) {
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, HistoryModeActivity::class.java)
        }
    }
}
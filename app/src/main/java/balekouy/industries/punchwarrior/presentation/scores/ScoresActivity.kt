package balekouy.industries.punchwarrior.presentation.scores

import android.content.Context
import android.content.Intent
import balekouy.industries.punchwarrior.R
import balekouy.industries.punchwarrior.presentation.BaseActivity

class ScoresActivity : BaseActivity(R.layout.activity_scores) {
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ScoresActivity::class.java)
        }
    }
}
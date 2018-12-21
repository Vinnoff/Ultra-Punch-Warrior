package balekouy.industries.punchwarrior.presentation.levelselection

import android.content.Context
import android.content.Intent
import balekouy.industries.punchwarrior.R
import balekouy.industries.punchwarrior.presentation.BaseActivity

class LevelSelectionActivity : BaseActivity(R.layout.activity_level_selection) {
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LevelSelectionActivity::class.java)
        }
    }

    override fun initUI() {

    }

    override fun initObservers(){

    }
}
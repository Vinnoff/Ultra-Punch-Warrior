package balekouy.industries.punchwarrior.presentation.fight

import android.content.Context
import android.content.Intent
import balekouy.industries.punchwarrior.R
import balekouy.industries.punchwarrior.presentation.BaseActivity

class FightActivity:BaseActivity(R.layout.activity_fight) {
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, FightActivity::class.java)
        }
    }
}
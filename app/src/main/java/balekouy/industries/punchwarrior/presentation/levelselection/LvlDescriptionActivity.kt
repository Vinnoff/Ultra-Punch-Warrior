package balekouy.industries.punchwarrior.presentation.levelselection

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import balekouy.industries.punchwarrior.R
import balekouy.industries.punchwarrior.data.Difficulty
import balekouy.industries.punchwarrior.data.models.Level
import balekouy.industries.punchwarrior.presentation.BaseActivity
import balekouy.industries.punchwarrior.presentation.fight.FightActivity
import kotlinx.android.synthetic.main.activity_level_description.*

class LvlDescriptionActivity : BaseActivity(R.layout.activity_level_description) {
    companion object {
        private const val LEVEL = "level"

        private const val TRANSITION_NAME = "transition_name"

        fun newIntent(context: Context, level: Level, transitionName: String?): Intent {
            val intent = Intent(context, LvlDescriptionActivity::class.java)
            intent.putExtra(LEVEL, level)
            intent.putExtra(TRANSITION_NAME, transitionName)
            return intent
        }
    }

    private lateinit var level: Level
    private lateinit var currentDifficulty: Difficulty

    private lateinit var difficulties: Array<Difficulty>

    override fun onCreate(savedInstanceState: Bundle?) {
        supportPostponeEnterTransition()
        level = intent.extras?.getSerializable(LEVEL) as Level
        difficulties = Difficulty.values()
        currentDifficulty = Difficulty.NORMAL
        super.onCreate(savedInstanceState)
    }


    override fun initUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val transitionName = intent.extras?.getString(TRANSITION_NAME)
            lvl_description_image.transitionName = transitionName
        }
        lvl_description_back.setOnClickListener { onBackPressed() }
        lvl_description_image.setImageDrawable(ContextCompat.getDrawable(this, level.fighter.second.portraitRes))
        lvl_description_name_value.text = level.fighter.second.name
        lvl_description_place_value.text = level.place.first
        lvl_description_background.setImageDrawable(ContextCompat.getDrawable(this, level.place.second))
        lvl_description_speed_value.progress = level.fighter.second.speed * 10
        lvl_description_might_value.progress = level.fighter.second.might * 10
        lvl_description_health_value.progress = level.fighter.second.health * 10
        lvl_description_difficulty_value.text = currentDifficulty.name
        lvl_description_difficulty_switch_left.setOnClickListener { _ ->
            if (currentDifficulty != difficulties.first())
                currentDifficulty = difficulties.single { it.id == currentDifficulty.id - 1 }
            checkArrows()
        }

        lvl_description_difficulty_switch_right.setOnClickListener { _ ->
            if (currentDifficulty != difficulties.last())
                currentDifficulty = difficulties.single { it.id == currentDifficulty.id + 1 }
            checkArrows()
        }
        lvl_description_validate.setOnClickListener {
            startActivity(
                FightActivity.newIntent(
                    baseContext,
                    level,
                    currentDifficulty.id
                )
            )
        }

        supportStartPostponedEnterTransition()
    }

    private fun checkArrows() {
        when (currentDifficulty) {
            difficulties.first() -> {
                lvl_description_difficulty_switch_left.visibility = View.GONE
            }
            difficulties.last() -> {
                lvl_description_difficulty_switch_right.visibility = View.GONE
            }
            else -> {
                lvl_description_difficulty_switch_left.visibility = View.VISIBLE
                lvl_description_difficulty_switch_right.visibility = View.VISIBLE
            }
        }
        lvl_description_difficulty_value.text = currentDifficulty.name
    }
}
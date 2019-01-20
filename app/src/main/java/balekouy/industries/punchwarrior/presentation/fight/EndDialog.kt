package balekouy.industries.punchwarrior.presentation.fight

import android.app.Dialog
import android.os.Bundle
import android.view.View
import balekouy.industries.punchwarrior.R
import balekouy.industries.punchwarrior.presentation.BaseDialog
import com.shawnlin.numberpicker.NumberPicker
import kotlinx.android.synthetic.main.dialog_end_fight.view.*

class EndDialog : BaseDialog() {

    companion object {
        private val EXTRA_DID_WIN: String = EndDialog::class.java.name + "EXTRA_DID_WIN"
        private val EXTRA_CHARACTER_UNLOCKED: String = EndDialog::class.java.name + "EXTRA_CHARACTER_UNLOCKED"
        private val EXTRA_SCORE: String = EndDialog::class.java.name + "EXTRA_SCORE"

        fun newInstance(
            didWin: Boolean,
            characterUnlocked: Boolean,
            score: Int
        ): EndDialog {
            val dialog = EndDialog()
            val bundle = getBaseBundle()
            bundle.putBoolean(EXTRA_DID_WIN, didWin)
            bundle.putBoolean(EXTRA_CHARACTER_UNLOCKED, characterUnlocked)
            bundle.putInt(EXTRA_SCORE, score)
            dialog.arguments = bundle
            return dialog
        }
    }

    lateinit var continueClickListener: OnContinueClickListener

    private var didWin: Boolean = true
    private var characterUnlocked: Boolean = false
    private var score: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            didWin = it.getBoolean(EXTRA_DID_WIN)
            characterUnlocked = it.getBoolean(EXTRA_CHARACTER_UNLOCKED)
            score = it.getInt(EXTRA_SCORE)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        root = activity!!.layoutInflater.inflate(R.layout.dialog_end_fight, null)

        root.end_score.text = score.toString()
        if (didWin) {
            root.end_winner.text = resources.getString(R.string.win)
            root.end_winner_2.text = resources.getString(R.string.win)
        } else {
            root.end_winner.text = resources.getString(R.string.loose)
            root.end_winner_2.text = resources.getString(R.string.loose)
        }

        root.end_letter_picker_first.configure()
        root.end_letter_picker_second.configure()
        root.end_letter_picker_third.configure()

        root.end_new_character.visibility = if (characterUnlocked) View.VISIBLE else View.GONE

        root.end_validate.setOnClickListener { onContinueClick("${root.end_letter_picker_first.charValue()}${root.end_letter_picker_second.charValue()}${root.end_letter_picker_third.charValue()}") }
        return safeDialog()
    }

    private fun onContinueClick(name: String) {
        continueClickListener.onContinueClick(name)
    }


    interface OnContinueClickListener {
        fun onContinueClick(name: String)
    }
}

private fun NumberPicker.charValue(): String = displayedValues[value]

private fun NumberPicker.configure() {
    minValue = 0
    displayedValues = ('A'..'Z')
        .toList()
        .map { it.toString() }
        .toTypedArray()
    maxValue = displayedValues.size - 1
    value = minValue
}

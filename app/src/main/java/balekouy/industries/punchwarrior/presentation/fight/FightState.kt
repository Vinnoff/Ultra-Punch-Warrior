package balekouy.industries.punchwarrior.presentation.fight

import balekouy.industries.punchwarrior.presentation.fight.FightViewModel.Companion.ROUND_DURATION

data class FightState(
    val placeRes: String = "",
    var timer: Int = ROUND_DURATION,
    var round: Int = 1,
    var inBreak: Boolean = false,
    var winner: String = Winner.NONE
) {

    class Winner {
        companion object {
            const val NONE = "None"
            const val PLAYER = "Player"
        }
    }
}
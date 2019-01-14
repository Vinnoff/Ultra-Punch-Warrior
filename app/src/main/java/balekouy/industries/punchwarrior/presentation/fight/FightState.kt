package balekouy.industries.punchwarrior.presentation.fight

import balekouy.industries.punchwarrior.presentation.fight.FightViewModel.Companion.ROUND_DURATION

data class FightState(
    val placeId: Int = 0,
    var timer: Int = ROUND_DURATION,
    var round: Int = 1,
    var inBreak: Boolean = true,
    var winner: String = Winner.NONE,
    val isLoading: Boolean
) {

    class Winner {
        companion object {
            const val NONE = "None"
            const val PLAYER = "Player"
        }
    }
}
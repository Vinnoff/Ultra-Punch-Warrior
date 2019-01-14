package balekouy.industries.punchwarrior.presentation.fight

data class FightState(
    val placeId: Int = 0,
    var timer: Int = 180,
    var round: Int = 1,
    var winner: Winner = Winner.NONE,
    val isLoading: Boolean
) {
    enum class Winner { NONE, PLAYER, OPPONENT }
}
package balekouy.industries.punchwarrior.presentation.fight

data class FighterState(
    var portrait: Int,
    var health: Int = 100,
    var energy: Int = 100,
    var isDown: Boolean = false,
    var special: Int = 0,
    var hasSpecial: Boolean = false,
    var leftGuard: Boolean = false,
    var rightGuard: Boolean = false
)
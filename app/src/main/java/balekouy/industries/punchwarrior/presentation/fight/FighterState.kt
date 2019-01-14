package balekouy.industries.punchwarrior.presentation.fight

class FighterState(
    var portrait: Int,
    health: Int = 100,
    energy: Int = 100,
    special: Int = 0,
    var isDown: Boolean = false,
    var hasSpecial: Boolean = false,
    var leftGuard: Boolean = false,
    var rightGuard: Boolean = false,
    var animation: PunchAnimation = PunchAnimation.NONE
) {
    var healthValue: Int = health
        set(value) {
            field = when {
                value < 0 -> 0
                value > 100 -> 100
                else -> value
            }
        }

    var energyValue: Int = energy
        set(value) {
            field = when {
                value < 0 -> 0
                value > 100 -> 100
                else -> value
            }
        }

    var specialValue: Int = special
        set(value) {
            field = when {
                value < 0 -> 0
                value > 100 -> 100
                else -> value
            }
        }
}

enum class PunchAnimation {
    NONE, LEFT_PUNCH, RIGHT_PUNCH, LEFT_PUNCHED, RIGHT_PUNCHED, LEFT_BLOCK, RIGHT_BLOCK, KO, VICTORY
}
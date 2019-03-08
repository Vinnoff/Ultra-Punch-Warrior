package balekouy.industries.punchwarrior.presentation.fight

class FighterState(
    var portraitRes: String,
    var animationRes: String,
    health: Int = 100,
    energy: Int = 100,
    special: Int = 0,
    var isDown: Boolean = false,
    var hasSpecial: Boolean = false,
    var leftDODGE: Boolean = false,
    var rightDODGE: Boolean = false,
    var animation: FightAnimation = FightAnimation.NONE
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

enum class FightAnimation {
    NONE, LEFT_PUNCH, RIGHT_PUNCH, LEFT_PUNCHED, RIGHT_PUNCHED, LEFT_DODGE, RIGHT_DODGE, KO, VICTORY, TIRED
}
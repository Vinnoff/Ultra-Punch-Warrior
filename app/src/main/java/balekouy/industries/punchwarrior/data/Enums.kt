package balekouy.industries.punchwarrior.data

enum class Difficulty(val id: Int, val string: String, multiplicator: Float) {
    NONE(0, "", 0F),
    EASY(1, "easy", 0.5F),
    NORMAL(2, "normal", 1F),
    HARD(3, "hard", 1.5F),
    CHAMPION(4, "champion", 2F);

    companion object {
        fun withId(id: Int): Difficulty {
            for (weaponChoice in Difficulty.values()) {
                if (weaponChoice.id == id) return weaponChoice
            }
            return NONE
        }
    }
}

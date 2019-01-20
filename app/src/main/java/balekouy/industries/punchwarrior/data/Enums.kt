package balekouy.industries.punchwarrior.data

enum class Difficulty(val id: Int, val string: String, val multiplicator: Double) {
    EASY(1, "easy", 0.5),
    NORMAL(2, "normal", 1.0),
    HARD(3, "hard", 1.5),
    CHAMPION(4, "champion", 2.0);

    companion object {
        fun withId(id: Int): Difficulty? {
            for (weaponChoice in Difficulty.values()) {
                if (weaponChoice.id == id) return weaponChoice
            }
            return null
        }
    }
}

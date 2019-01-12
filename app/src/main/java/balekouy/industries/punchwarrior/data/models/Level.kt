package balekouy.industries.punchwarrior.data.models

import balekouy.industries.punchwarrior.data.Difficulty

data class Level(
    val fighter: Pair<Int, Fighter>,
    val place: Pair<String, Int>,
    val difficulty: Difficulty = Difficulty.NORMAL,
    val isUnlocked: Boolean = true
)
package balekouy.industries.punchwarrior.data.models

import balekouy.industries.punchwarrior.data.Difficulty
import java.io.Serializable

data class Level(
    val fighter: Pair<Int, Fighter>,
    val place: Pair<String, String>,
    val difficulty: Difficulty = Difficulty.NORMAL,
    val isUnlocked: Boolean = true
) : Serializable
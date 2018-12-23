package balekouy.industries.punchwarrior.data.models

import balekouy.industries.punchwarrior.data.Difficulty

data class Score(
    val name: String,
    val fighter: Fighter?,
    val difficulty: Difficulty,
    val score: Int
)
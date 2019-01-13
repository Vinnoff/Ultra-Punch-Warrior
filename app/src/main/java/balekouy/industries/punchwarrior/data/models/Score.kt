package balekouy.industries.punchwarrior.data.models

import balekouy.industries.punchwarrior.data.Difficulty
import java.io.Serializable

data class Score(
    val name: String,
    val fighter: Pair<Int, Fighter>,
    val difficulty: Difficulty?,
    val score: Int
) : Serializable
package balekouy.industries.punchwarrior.data.models

import java.io.Serializable

data class Sprite(
    val portrait: String,
    val normalMode: String,
    val leftPunchMode: String,
    val rightPunchMode: String,
    val leftDODGEMode: String,
    val rightDODGEMode: String,
    val leftPunchedMode: String,
    val rightPunchedMode: String,
    val KOMode: String,
    val victoryMode: String
) : Serializable
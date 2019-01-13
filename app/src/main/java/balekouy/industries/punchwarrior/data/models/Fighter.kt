package balekouy.industries.punchwarrior.data.models

import java.io.Serializable

data class Fighter(
    val name: String,
    val speed: Int,
    val might: Int,
    val health: Int,
    val determination: Int,
    val portraitRes: Int
) : Serializable
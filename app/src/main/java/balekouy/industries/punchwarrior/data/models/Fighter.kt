package balekouy.industries.punchwarrior.data.models

import java.io.Serializable

data class Fighter(
    val id: Int,
    val name: String,
    val speed: Int,
    val might: Int,
    val health: Int,
    val energy: Int,
    val sprites: Sprite
) : Serializable
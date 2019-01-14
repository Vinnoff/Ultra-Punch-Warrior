package balekouy.industries.punchwarrior.data.models

import java.io.Serializable

data class Place(
    val name: String,
    val imageId: Int,
    val gifId: Int
) : Serializable
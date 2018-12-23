package balekouy.industries.punchwarrior.data

import balekouy.industries.punchwarrior.R
import balekouy.industries.punchwarrior.data.database.entity.EFighter
import balekouy.industries.punchwarrior.data.database.entity.EPlace
import balekouy.industries.punchwarrior.data.database.entity.EScore


object DataProvider {

    fun createFighters(): List<EFighter> = listOf(
        EFighter("Old Smith", 3, 2, 2, 3, R.drawable.profile_goku),
        EFighter("Big Buddy", 1, 4, 5, 3, R.drawable.profile_goku),
        EFighter("Skinny Pete", 5, 1, 2, 3, R.drawable.profile_goku),
        EFighter("Simple Sam", 5, 5, 5, 4, R.drawable.profile_goku),
        EFighter("Strong Apple", 3, 6, 4, 7, R.drawable.profile_goku),
        EFighter("Final Opponent", 10, 10, 10, 10, R.drawable.profile_goku)
    )

    fun createPlaces(): List<EPlace> = listOf(
        EPlace("Simple Ring", R.drawable.background_home),
        EPlace("Simple Ring", R.drawable.background_home)
    )

    fun createScores(): List<EScore> = listOf(
        EScore("MAX", 4, 2, 3500),
        EScore("BIL", 2, 2, 3000),
        EScore("SER", 4, 2, 4000),
        EScore("ASS", 3, 2, 3000),
        EScore("ADO", 5, 2, 3000),
        EScore("VIN", 4, 2, 4500),
        EScore("GOK", 4, 3, 9001),
        EScore("FRI", 1, 2, 3000)
    )
}
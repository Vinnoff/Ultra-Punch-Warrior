package balekouy.industries.punchwarrior.data

import balekouy.industries.punchwarrior.data.database.entity.EFighter
import balekouy.industries.punchwarrior.data.database.entity.ELevel
import balekouy.industries.punchwarrior.data.database.entity.EScore


object DataProvider {

    fun createFighters(): List<EFighter> = listOf(
        EFighter("Gabby Jay", 3, 2, 2, 3),
        EFighter("Bear Hugger", 1, 6, 5, 7),
        EFighter("Narcis Prince", 8, 3, 2, 3),
        EFighter("Aran Ryan", 5, 5, 5, 4),
        EFighter("Super Macho Man", 6, 6, 7, 7),
        EFighter("Rick Bruiser", 10, 10, 10, 10)
    )

    fun createLevels(): List<ELevel> = listOf(
        ELevel(1, "Minor Circuit", true),
        ELevel(2, "Minor Circuit", true),
        ELevel(3, "Major Circuit", true),
        ELevel(4, "Major Circuit", true),
        ELevel(5, "World Circuit"),
        ELevel(6, "Special Circuit")
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
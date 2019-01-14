package balekouy.industries.punchwarrior.data

import balekouy.industries.punchwarrior.R
import balekouy.industries.punchwarrior.data.database.entity.EFighter
import balekouy.industries.punchwarrior.data.database.entity.ELevel
import balekouy.industries.punchwarrior.data.database.entity.EScore


object DataProvider {

    fun createFighters(): List<EFighter> = listOf(
        EFighter("Old Smith", 3, 2, 2, 3, R.drawable.portrait_goku),
        EFighter("Big Buddy", 1, 4, 5, 3, R.drawable.portrait_goku),
        EFighter("Skinny Pete", 5, 1, 2, 3, R.drawable.portrait_goku),
        EFighter("Simple Sam", 5, 5, 5, 4, R.drawable.portrait_goku),
        EFighter("Strong Apple", 3, 6, 4, 7, R.drawable.portrait_goku),
        EFighter("Final Opponent", 10, 10, 10, 10, R.drawable.portrait_goku)
    )

    fun createLevels(): List<ELevel> = listOf(
        ELevel(1, "Simple Ring", R.drawable.background_place_minor_circuit, true),
        ELevel(2, "Simple Ring", R.drawable.background_place_minor_circuit, true),
        ELevel(3, "Simple Ring", R.drawable.background_place_major_circuit, true),
        ELevel(4, "Simple Ring", R.drawable.background_place_major_circuit),
        ELevel(5, "Simple Ring", R.drawable.background_place_world_circuit),
        ELevel(6, "Simple Ring", R.drawable.background_place_world_circuit)
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
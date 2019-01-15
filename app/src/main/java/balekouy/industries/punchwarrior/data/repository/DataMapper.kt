package balekouy.industries.punchwarrior.data.repository

import balekouy.industries.punchwarrior.data.Difficulty
import balekouy.industries.punchwarrior.data.database.RoomDataSource
import balekouy.industries.punchwarrior.data.database.entity.EFighter
import balekouy.industries.punchwarrior.data.database.entity.ELevel
import balekouy.industries.punchwarrior.data.database.entity.EScore
import balekouy.industries.punchwarrior.data.models.Fighter
import balekouy.industries.punchwarrior.data.models.Level
import balekouy.industries.punchwarrior.data.models.Score
import balekouy.industries.punchwarrior.data.models.Sprite
import javax.inject.Inject

class DataMapper @Inject constructor(
    private val roomDataSource: RoomDataSource
) {

    fun mapAsEScore(score: Score, fighterId: Int) =
        EScore(
            name = score.name,
            fighterId = fighterId,
            difficulty = score.difficulty?.id ?: 0,
            score = score.score
        )

    fun mapAsLevels(dataLevels: List<ELevel>, dataFighters: List<EFighter>): MutableList<Pair<Int, Level>> {
        val response = mutableListOf<Pair<Int, Level>>()
        if (dataLevels.isNotEmpty() && dataFighters.isNotEmpty())
            dataLevels.forEach { eLevel ->
                response.add(
                    mapAsLevel(
                        eLevel,
                        dataFighters.single { eFighter -> eFighter.id == eLevel.fighterId })
                )
            }
        return response
    }

    fun mapAsScores(dataScores: List<EScore>, dataFighters: List<EFighter>): MutableList<Pair<Int, Score>> {
        val response = mutableListOf<Pair<Int, Score>>()
        if (dataScores.isNotEmpty() && dataFighters.isNotEmpty())
            dataScores.forEach { eScore ->
                response.add(
                    mapAsScore(
                        eScore,
                        dataFighters.single { eFighter -> eFighter.id == eScore.fighterId })
                )
            }
        return response
    }

    private fun mapAsFighter(eFighter: EFighter) =
        Pair(
            eFighter.id, Fighter(
                name = eFighter.name,
                speed = eFighter.speed,
                might = eFighter.might,
                health = eFighter.health,
                energy = eFighter.energy,
                sprites = createSpriteNames(eFighter.name.mapAsLowerUnderScore())
            )
        )

    private fun mapAsLevel(eLevel: ELevel, eFighter: EFighter) =
        Pair(
            eLevel.id, Level(
                fighter = mapAsFighter(eFighter),
                place = Pair(eLevel.placeName, eLevel.placeImageId),
                isUnlocked = eLevel.unlocked
            )
        )

    private fun mapAsScore(eScore: EScore, eFighter: EFighter) =
        Pair(
            eScore.id, Score(
                name = eScore.name,
                fighter = mapAsFighter(eFighter),
                difficulty = Difficulty.withId(eScore.difficulty),
                score = eScore.score
            )
        )

    companion object {
        fun createSpriteNames(name: String): Sprite =
            Sprite(
                portrait = "${name}_portrait",
                normalMode = "${name}_normal",
                leftPunchMode = "${name}_left_punch",
                rightPunchMode = "${name}_right_punch",
                leftDODGEMode = "${name}_left_dodge",
                rightDODGEMode = "${name}_right_dodge",
                leftPunchedMode = "${name}_left_punched",
                rightPunchedMode = "${name}_right_punched",
                KOMode = "${name}_ko",
                victoryMode = "${name}_victory"
            )
    }
}

private fun String.mapAsLowerUnderScore(): String = this.toLowerCase().replace(' ', '_')

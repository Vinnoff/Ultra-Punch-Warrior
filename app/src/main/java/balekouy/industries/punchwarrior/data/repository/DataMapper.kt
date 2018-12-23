package balekouy.industries.punchwarrior.data.repository

import balekouy.industries.punchwarrior.data.Difficulty
import balekouy.industries.punchwarrior.data.database.RoomDataSource
import balekouy.industries.punchwarrior.data.database.entity.EFighter
import balekouy.industries.punchwarrior.data.database.entity.EScore
import balekouy.industries.punchwarrior.data.models.Fighter
import balekouy.industries.punchwarrior.data.models.Score
import javax.inject.Inject

class DataMapper @Inject constructor(
    private val roomDataSource: RoomDataSource
) {
    fun mapAsScores(allScores: List<EScore>): List<Score> {
        val scores = arrayListOf<Score>()
        allScores.forEach { eScore ->
            scores.add(
                Score(
                    name = eScore.name,
                    fighter = eScore.fighter?.run {
                        roomDataSource.roomDao().getFighterById(this).map { mapAsFighter(it) }?.blockingGet()
                    },
                    difficulty = Difficulty.withId(eScore.difficulty),
                    score = eScore.score
                )
            )
        }
        return scores
    }

    private fun mapAsFighter(fighter: EFighter): Fighter? {
        return Fighter(
            name = fighter.name,
            speed = fighter.speed,
            might = fighter.might,
            health = fighter.health,
            determination = fighter.determination,
            imageSet = fighter.imageSet
        )
    }

}

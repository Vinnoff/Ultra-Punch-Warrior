package balekouy.industries.punchwarrior.data.repository.scores

import balekouy.industries.punchwarrior.data.database.entity.EScore
import io.reactivex.Completable
import io.reactivex.Single

interface ScoresRepository {
    fun getAllScores(): Single<List<EScore>>
    fun saveScore(score: EScore): Completable
}
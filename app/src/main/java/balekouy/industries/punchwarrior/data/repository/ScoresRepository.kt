package balekouy.industries.punchwarrior.data.repository

import balekouy.industries.punchwarrior.data.models.Score
import balekouy.industries.punchwarrior.domain.TypeResponse
import io.reactivex.Single

interface ScoresRepository {
    fun getAllScores(): Single<TypeResponse<List<Score>>>
    fun saveScore(score: Score): Single<TypeResponse<Score>>
}
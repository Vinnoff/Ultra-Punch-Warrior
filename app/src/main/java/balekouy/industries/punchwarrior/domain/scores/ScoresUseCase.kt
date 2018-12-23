package balekouy.industries.punchwarrior.domain.scores

import balekouy.industries.punchwarrior.data.models.Score
import balekouy.industries.punchwarrior.domain.TypeResponse
import io.reactivex.Single

interface ScoresUseCase {
    fun getScores(): Single<TypeResponse<List<Score>>>
    fun addScore(score: Score): Single<TypeResponse<Score>>
}
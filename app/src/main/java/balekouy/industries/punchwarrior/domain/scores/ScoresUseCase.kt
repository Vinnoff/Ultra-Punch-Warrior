package balekouy.industries.punchwarrior.domain.scores

import balekouy.industries.punchwarrior.data.models.Score
import balekouy.industries.punchwarrior.domain.TypeResponse
import io.reactivex.Completable
import io.reactivex.Single

interface ScoresUseCase {
    fun getAllScores(): Single<TypeResponse<List<Pair<Int, Score>>>>
    fun saveScore(score: Score): Completable
}
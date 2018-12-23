package balekouy.industries.punchwarrior.domain.scores

import balekouy.industries.punchwarrior.data.models.Score
import balekouy.industries.punchwarrior.data.repository.ScoresRepository
import balekouy.industries.punchwarrior.domain.TypeResponse
import io.reactivex.Single
import javax.inject.Inject

class ScoresInteractor @Inject constructor(
    private val scoresRepository: ScoresRepository
) : ScoresUseCase {
    override fun getScores(): Single<TypeResponse<List<Score>>> {
        return scoresRepository.getAllScores()
    }

    override fun addScore(score: Score): Single<TypeResponse<Score>> {
        return scoresRepository.saveScore(score)
    }

}
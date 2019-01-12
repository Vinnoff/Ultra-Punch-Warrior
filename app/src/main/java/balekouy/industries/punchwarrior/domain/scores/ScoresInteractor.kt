package balekouy.industries.punchwarrior.domain.scores

import balekouy.industries.punchwarrior.data.models.Score
import balekouy.industries.punchwarrior.data.repository.DataMapper
import balekouy.industries.punchwarrior.data.repository.fighters.FighterRepository
import balekouy.industries.punchwarrior.data.repository.scores.ScoresRepository
import balekouy.industries.punchwarrior.domain.DataResponse
import balekouy.industries.punchwarrior.domain.TypeResponse
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class ScoresInteractor @Inject constructor(
    private val scoresRepository: ScoresRepository,
    private val fighterRepository: FighterRepository,
    private val mapper: DataMapper
) : ScoresUseCase {
    override fun getAllScores(): Single<TypeResponse<List<Pair<Int, Score>>>> = Single.zip(
        scoresRepository.getAllScores(),
        fighterRepository.getAllFighters(),
        BiFunction { dataScores, dataFighters ->
            val response = mapper.mapAsScores(dataScores, dataFighters)
            response.sortByDescending { it.second.score }
            return@BiFunction DataResponse(response)
        })

    override fun saveScore(id: Int, score: Score): Completable {
        return scoresRepository.saveScore(mapper.mapAsEScore(score, id))
    }
}
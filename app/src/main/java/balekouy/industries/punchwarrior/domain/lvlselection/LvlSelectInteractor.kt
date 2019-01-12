package balekouy.industries.punchwarrior.domain.lvlselection

import balekouy.industries.punchwarrior.data.models.Level
import balekouy.industries.punchwarrior.data.repository.DataMapper
import balekouy.industries.punchwarrior.data.repository.fighters.FighterRepository
import balekouy.industries.punchwarrior.data.repository.levels.LevelsRepository
import balekouy.industries.punchwarrior.domain.DataResponse
import balekouy.industries.punchwarrior.domain.TypeResponse
import balekouy.industries.punchwarrior.domain.scores.LvlSelectUseCase
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class LvlSelectInteractor @Inject constructor(
    private val levelsRepository: LevelsRepository,
    private val fighterRepository: FighterRepository,
    private val mapper: DataMapper
) : LvlSelectUseCase {
    override fun getLevels(): Single<TypeResponse<List<Pair<Int, Level>>>> = Single.zip(
        levelsRepository.getAllLevels(),
        fighterRepository.getAllFighters(),
        BiFunction { dataLevels, dataFighters ->
            val response = mapper.mapAsLevels(dataLevels, dataFighters)
            response.sortByDescending { it.first }
            return@BiFunction DataResponse(mapper.mapAsLevels(dataLevels, dataFighters))
        })

    override fun deblockLevel(int: Int): Single<TypeResponse<Level>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
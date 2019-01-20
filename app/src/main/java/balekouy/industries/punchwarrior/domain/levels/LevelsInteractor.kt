package balekouy.industries.punchwarrior.domain.levels

import android.util.Log
import balekouy.industries.punchwarrior.data.database.entity.ELevel
import balekouy.industries.punchwarrior.data.models.Level
import balekouy.industries.punchwarrior.data.repository.DataMapper
import balekouy.industries.punchwarrior.data.repository.fighters.FighterRepository
import balekouy.industries.punchwarrior.data.repository.levels.LevelsRepository
import balekouy.industries.punchwarrior.domain.DataResponse
import balekouy.industries.punchwarrior.domain.ErrorCode
import balekouy.industries.punchwarrior.domain.ErrorResponse
import balekouy.industries.punchwarrior.domain.TypeResponse
import balekouy.industries.punchwarrior.domain.scores.LevelsUseCase
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class LevelsInteractor @Inject constructor(
    private val levelsRepository: LevelsRepository,
    private val fighterRepository: FighterRepository,
    private val mapper: DataMapper
) : LevelsUseCase {

    override fun getLevels(): Single<TypeResponse<List<Level>>> = Single.zip(
        levelsRepository.getAllLevels(),
        fighterRepository.getAllFighters(),
        BiFunction { dataLevels, dataFighters ->
            val response = mapper.mapAsLevels(dataLevels, dataFighters)
            response.sortBy { it.id }
            return@BiFunction DataResponse(response)
        })

    override fun unlockLevel(id: Int): Single<TypeResponse<Void?>> =
        levelsRepository.getLevel(id).flatMap { eLevel: ELevel? ->
            when {
                eLevel == null -> return@flatMap Single.just(ErrorResponse<Void?>(ErrorCode.NOT_FOUND))
                eLevel.unlocked -> return@flatMap Single.just(ErrorResponse<Void?>(ErrorCode.NO_CHANGES))
                else -> {
                    levelsRepository.updateLevel(eLevel.id).map {
                        if (it != 1)
                            ErrorResponse<Void?>(ErrorCode.TECHNICAL_ERROR)
                        else
                            DataResponse<Void?>(null)
                    }
                }
            }
        }.onErrorReturn {
            Log.e("azertyuiop", it.toString())
            ErrorResponse<Void?>(ErrorCode.TECHNICAL_ERROR)
        }
}
package balekouy.industries.punchwarrior.domain.scores

import balekouy.industries.punchwarrior.data.models.Level
import balekouy.industries.punchwarrior.domain.TypeResponse
import io.reactivex.Single

interface LvlSelectUseCase {

    fun getLevels(): Single<TypeResponse<List<Pair<Int, Level>>>>
    fun deblockLevel(int: Int): Single<TypeResponse<Level>>
}
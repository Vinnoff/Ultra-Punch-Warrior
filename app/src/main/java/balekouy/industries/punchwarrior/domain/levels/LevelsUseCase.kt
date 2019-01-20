package balekouy.industries.punchwarrior.domain.scores

import balekouy.industries.punchwarrior.data.models.Level
import balekouy.industries.punchwarrior.domain.TypeResponse
import io.reactivex.Single

interface LevelsUseCase {

    fun getLevels(): Single<TypeResponse<List<Level>>>
    fun unlockLevel(id: Int): Single<TypeResponse<Void?>>
}
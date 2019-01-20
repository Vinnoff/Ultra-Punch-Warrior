package balekouy.industries.punchwarrior.data.repository.levels

import balekouy.industries.punchwarrior.data.database.entity.ELevel
import io.reactivex.Single

interface LevelsRepository {
    fun getAllLevels(): Single<List<ELevel>>
    fun getLevel(id: Int): Single<ELevel?>
    fun updateLevel(levelId: Int): Single<Int>
}
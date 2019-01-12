package balekouy.industries.punchwarrior.data.repository.levels

import balekouy.industries.punchwarrior.data.database.RoomDataSource
import balekouy.industries.punchwarrior.data.database.entity.ELevel
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LevelsRepositoryImpl @Inject constructor(
    private val roomDataSource: RoomDataSource
) : LevelsRepository {
    override fun getAllLevels(): Single<List<ELevel>> =
        roomDataSource.roomDao().getAllLevels()

    override fun unlockLevel(eLevel: ELevel): Completable =
        Completable.fromAction { roomDataSource.roomDao().unlockLevel(eLevel) }
}
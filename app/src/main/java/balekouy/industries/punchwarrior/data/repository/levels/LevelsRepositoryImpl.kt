package balekouy.industries.punchwarrior.data.repository.levels

import android.util.Log
import balekouy.industries.punchwarrior.data.database.RoomDataSource
import balekouy.industries.punchwarrior.data.database.entity.ELevel
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LevelsRepositoryImpl @Inject constructor(
    private val roomDataSource: RoomDataSource
) : LevelsRepository {
    override fun getAllLevels(): Single<List<ELevel>> =
        roomDataSource.roomDao().getAllLevels()

    override fun getLevel(id: Int) =
        roomDataSource.roomDao().getLevelById(id).map { it: ELevel? ->
            Log.d("roomLevel", it.toString())
            return@map it
        }

    override fun updateLevel(levelId: Int) =
        Single.just(roomDataSource.roomDao().unlockLevel(levelId))
}
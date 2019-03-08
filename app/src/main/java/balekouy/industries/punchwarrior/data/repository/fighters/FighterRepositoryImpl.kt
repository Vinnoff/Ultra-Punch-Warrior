package balekouy.industries.punchwarrior.data.repository.fighters

import balekouy.industries.punchwarrior.data.database.RoomDataSource
import balekouy.industries.punchwarrior.data.database.entity.EFighter
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FighterRepositoryImpl @Inject constructor(
    private val roomDataSource: RoomDataSource
) : FighterRepository {
    override fun getAllFighters(): Single<List<EFighter>> =
        roomDataSource.roomDao().getAllFighters()

    override fun getFighterId(fighterName: String): Single<Int?> =
        roomDataSource.roomDao().getFighterByName(fighterName)
            .map { it.id }

    override fun getFighterById(fighterId: Int): Single<EFighter> =
        roomDataSource.roomDao().getFighterById(fighterId)
}
package balekouy.industries.punchwarrior.data.repository.scores

import balekouy.industries.punchwarrior.data.database.RoomDataSource
import balekouy.industries.punchwarrior.data.database.entity.EScore
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoresRepositoryImpl @Inject constructor(
    private val roomDataSource: RoomDataSource
) : ScoresRepository {
    override fun getAllScores(): Single<List<EScore>> =
        roomDataSource.roomDao().getAllScores()

    override fun saveScore(score: EScore): Completable =
        Completable.fromAction { roomDataSource.roomDao().insertScore(score) }
}

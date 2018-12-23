package balekouy.industries.punchwarrior.data.repository

import android.content.Context
import balekouy.industries.punchwarrior.data.database.RoomDataSource
import balekouy.industries.punchwarrior.data.models.Score
import balekouy.industries.punchwarrior.domain.DataResponse
import balekouy.industries.punchwarrior.domain.TypeResponse
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoresRepositoryImpl @Inject constructor(
    private val roomDataSource: RoomDataSource,
    private val mapper: DataMapper,
    private val context: Context
) : ScoresRepository {
    override fun getAllScores(): Single<TypeResponse<List<Score>>> {
        return roomDataSource.roomDao().getAllScores().map {
            DataResponse(mapper.mapAsScores(it))
        }
    }

    override fun saveScore(score: Score): Single<TypeResponse<Score>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
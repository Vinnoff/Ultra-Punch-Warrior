package balekouy.industries.punchwarrior.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import balekouy.industries.punchwarrior.data.entity.Fighter
import balekouy.industries.punchwarrior.data.entity.Place
import balekouy.industries.punchwarrior.data.entity.Score
import io.reactivex.Single

@Dao
interface RoomDao {
    @Query(RoomContract.SELECT_ALL_FIGHTERS)
    fun getAllFighter(): Single<List<Fighter>>

    @Query(RoomContract.SELECT_FIGHTER + ":name" + RoomContract.LIMIT_ONE)
    fun getFighter(name: String?): Single<Fighter>

    @Insert
    fun insertFighter(fighter: Fighter)

    @Query(RoomContract.SELECT_ALL_PLACES)
    fun getAllPlaces(): Single<List<Place>>

    @Query(RoomContract.SELECT_PLACE + ":name" + RoomContract.LIMIT_ONE)
    fun getPlace(name: String?): Single<Place>

    @Insert
    fun insertPlace(place: Place)

    @Query(RoomContract.SELECT_SCORES)
    fun getAllScores(): Single<List<Score>>

    @Insert
    fun insertScore(score: Score)

    @Delete
    fun deleteScore(score: Score)
}


package balekouy.industries.punchwarrior.data.database

import android.arch.persistence.room.*
import balekouy.industries.punchwarrior.data.database.entity.EFighter
import balekouy.industries.punchwarrior.data.database.entity.ELevel
import balekouy.industries.punchwarrior.data.database.entity.EScore
import io.reactivex.Single

@Dao
interface RoomDao {
    @Query(RoomContract.SELECT_ALL_FIGHTERS)
    fun getAllFighters(): Single<List<EFighter>>

    @Query(RoomContract.SELECT_FIGHTER_BY_NAME + ":name" + RoomContract.LIMIT_ONE)
    fun getFighterByName(name: String): Single<EFighter?>

    @Query(RoomContract.SELECT_FIGHTER_BY_ID + ":id" + RoomContract.LIMIT_ONE)
    fun getFighterById(id: Int): Single<EFighter>

    @Insert
    fun insertFighter(fighter: EFighter)

    @Insert
    fun initFighters(fighters: List<EFighter>)

    @Query(RoomContract.SELECT_ALL_SCORES)
    fun getAllScores(): Single<List<EScore>>

    @Insert
    fun insertScore(score: EScore)

    @Delete
    fun deleteScore(score: EScore)

    @Insert
    fun initScores(scores: List<EScore>)

    @Query(RoomContract.SELECT_ALL_LEVELS)
    fun getAllLevels(): Single<List<ELevel>>

    @Insert
    fun initLevels(scores: List<ELevel>)

    @Update
    fun unlockLevel(eLevel: ELevel)
}


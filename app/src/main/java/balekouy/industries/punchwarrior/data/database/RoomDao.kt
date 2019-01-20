package balekouy.industries.punchwarrior.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
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

    @Query("UPDATE level SET unlocked=1 WHERE id=:id")
    fun unlockLevel(id: Int): Int

    @Query(RoomContract.SELECT_LEVEL_BY_ID + ":id" + RoomContract.LIMIT_ONE)
    fun getLevelById(id: Int): Single<ELevel?>
}


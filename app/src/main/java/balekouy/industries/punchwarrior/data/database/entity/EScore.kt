package balekouy.industries.punchwarrior.data.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import balekouy.industries.punchwarrior.data.database.RoomContract

@Entity(tableName = RoomContract.TABLE_SCORE)
data class EScore(
    var name: String,
    var fighterId: Int,
    var difficulty: Int,
    var score: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
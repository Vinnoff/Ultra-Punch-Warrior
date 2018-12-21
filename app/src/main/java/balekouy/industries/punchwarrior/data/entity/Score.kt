package balekouy.industries.punchwarrior.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import balekouy.industries.punchwarrior.data.database.RoomContract

@Entity(tableName = RoomContract.TABLE_SCORES)
data class Score(
    @PrimaryKey(autoGenerate = false)
    val key : String,
    val id : String,
    val name: String,
    val level: Int,
    val score: Int
)
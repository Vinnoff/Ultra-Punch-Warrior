package balekouy.industries.punchwarrior.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import balekouy.industries.punchwarrior.data.database.RoomContract

@Entity(tableName = RoomContract.TABLE_FIGHTER)
data class Fighter(
    @PrimaryKey(autoGenerate = false)
    val key : String,
    val id : String,
    val name : String,
    val speed : Int,
    val might : Int,
    val health: Int,
    val determination : Int
)

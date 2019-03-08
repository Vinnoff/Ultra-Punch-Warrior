package balekouy.industries.punchwarrior.data.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import balekouy.industries.punchwarrior.data.database.RoomContract

@Entity(tableName = RoomContract.TABLE_FIGHTER)
data class EFighter(
    var name: String,
    var speed: Int,
    var might: Int,
    var health: Int,
    var energy: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
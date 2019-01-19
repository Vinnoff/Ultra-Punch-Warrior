package balekouy.industries.punchwarrior.data.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import balekouy.industries.punchwarrior.data.database.RoomContract

@Entity(tableName = RoomContract.TABLE_LEVEL)
data class ELevel(
    val fighterId: Int,
    var placeName: String,
    val unlocked: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
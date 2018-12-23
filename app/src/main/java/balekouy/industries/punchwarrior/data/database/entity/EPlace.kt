package balekouy.industries.punchwarrior.data.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import balekouy.industries.punchwarrior.data.database.RoomContract

@Entity(tableName = RoomContract.TABLE_PLACE)
data class EPlace(
    var name: String,
    var imageId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
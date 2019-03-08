package balekouy.industries.punchwarrior.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import balekouy.industries.punchwarrior.data.database.entity.EFighter
import balekouy.industries.punchwarrior.data.database.entity.ELevel
import balekouy.industries.punchwarrior.data.database.entity.EScore


@Database(
    entities = [EFighter::class, EScore::class, ELevel::class],
    version = 4,
    exportSchema = false
)
abstract class RoomDataSource : RoomDatabase() {
    abstract fun roomDao(): RoomDao
}



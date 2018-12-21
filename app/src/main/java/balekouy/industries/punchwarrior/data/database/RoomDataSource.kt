package balekouy.industries.punchwarrior.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import balekouy.industries.punchwarrior.data.entity.Fighter
import balekouy.industries.punchwarrior.data.entity.Place
import balekouy.industries.punchwarrior.data.entity.Score

@Database(
    entities = [Fighter::class, Place::class, Score::class],
    version = 1,
    exportSchema = false)
abstract class RoomDataSource : RoomDatabase() {

  abstract fun roomDao(): RoomDao

  companion object {
    fun buildDatabase(context: Context): RoomDataSource = Room.databaseBuilder(
        context.applicationContext,
        RoomDataSource::class.java,
      RoomContract.DATABASE_NAME
    ).build()
  }

}


package balekouy.industries.punchwarrior.di

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.support.annotation.NonNull
import balekouy.industries.punchwarrior.data.DataProvider
import balekouy.industries.punchwarrior.data.database.RoomContract
import balekouy.industries.punchwarrior.data.database.RoomDataSource
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class RoomModule {
    lateinit var roomData: RoomDataSource

    @Provides
    @Singleton
    fun provideRoomCurrencyDataSource(context: Context): RoomDataSource {
        roomData = Room.databaseBuilder(
            context,
            RoomDataSource::class.java,
            RoomContract.DATABASE_NAME
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Executors.newSingleThreadScheduledExecutor()
                    .execute {
                        roomData.roomDao().initFighters(DataProvider.createFighters())
                        roomData.roomDao().initPlaces(DataProvider.createPlaces())
                        roomData.roomDao().initScores(DataProvider.createScores())
                    }
            }
        }).build()
        return roomData
    }
}
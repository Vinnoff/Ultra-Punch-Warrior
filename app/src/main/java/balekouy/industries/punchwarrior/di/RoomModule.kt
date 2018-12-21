package balekouy.industries.punchwarrior.di
import android.content.Context
import balekouy.industries.punchwarrior.data.database.RoomDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

  @Provides @Singleton fun provideRoomCurrencyDataSource(context: Context) =
      RoomDataSource.buildDatabase(context)
}
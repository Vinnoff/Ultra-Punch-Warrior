package balekouy.industries.punchwarrior.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: UltraPunchWarriorApplication) {

  @Provides @Singleton fun provideContext(): Context = application

}



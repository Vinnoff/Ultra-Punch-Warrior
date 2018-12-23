package balekouy.industries.punchwarrior.di

import android.content.Context
import balekouy.industries.punchwarrior.data.repository.ScoresRepository
import balekouy.industries.punchwarrior.data.repository.ScoresRepositoryImpl
import balekouy.industries.punchwarrior.domain.scores.ScoresInteractor
import balekouy.industries.punchwarrior.domain.scores.ScoresUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: UltraPunchWarriorApplication) {

  @Provides @Singleton fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideScoreRepository(instance: ScoresRepositoryImpl): ScoresRepository = instance

    @Provides
    @Singleton
    fun provideScoreUseCase(instance: ScoresInteractor): ScoresUseCase = instance

}



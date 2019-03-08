package balekouy.industries.punchwarrior.di

import android.content.Context
import balekouy.industries.punchwarrior.data.repository.fighters.FighterRepository
import balekouy.industries.punchwarrior.data.repository.fighters.FighterRepositoryImpl
import balekouy.industries.punchwarrior.data.repository.levels.LevelsRepository
import balekouy.industries.punchwarrior.data.repository.levels.LevelsRepositoryImpl
import balekouy.industries.punchwarrior.data.repository.scores.ScoresRepository
import balekouy.industries.punchwarrior.data.repository.scores.ScoresRepositoryImpl
import balekouy.industries.punchwarrior.domain.levels.LevelsInteractor
import balekouy.industries.punchwarrior.domain.scores.LevelsUseCase
import balekouy.industries.punchwarrior.domain.scores.ScoresInteractor
import balekouy.industries.punchwarrior.domain.scores.ScoresUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: UltraPunchWarriorApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideFighterRepository(instance: FighterRepositoryImpl): FighterRepository = instance

    @Provides
    @Singleton
    fun provideLevelsRepository(instance: LevelsRepositoryImpl): LevelsRepository = instance

    @Provides
    @Singleton
    fun provideScoreRepository(instance: ScoresRepositoryImpl): ScoresRepository = instance

    @Provides
    @Singleton
    fun provideLvlSelectUseCase(instance: LevelsInteractor): LevelsUseCase = instance

    @Provides
    @Singleton
    fun provideScoreUseCase(instance: ScoresInteractor): ScoresUseCase = instance

}



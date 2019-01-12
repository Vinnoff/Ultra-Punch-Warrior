package balekouy.industries.punchwarrior.di

import balekouy.industries.punchwarrior.presentation.home.HomeViewModel
import balekouy.industries.punchwarrior.presentation.levelselection.LvlSelectViewModel
import balekouy.industries.punchwarrior.presentation.scores.ScoresViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, RoomModule::class])
@Singleton interface AppComponent {

    fun inject(viewModel: HomeViewModel)
    fun inject(viewModel: LvlSelectViewModel)
    fun inject(viewModel: ScoresViewModel)
}



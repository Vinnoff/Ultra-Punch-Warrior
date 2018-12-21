package balekouy.industries.punchwarrior.di

import balekouy.industries.punchwarrior.presentation.home.HomeViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, RoomModule::class])
@Singleton interface AppComponent {

  fun inject(viewModel: HomeViewModel)
}



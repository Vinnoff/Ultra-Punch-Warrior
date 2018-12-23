package balekouy.industries.punchwarrior.presentation.home

import balekouy.industries.punchwarrior.di.UltraPunchWarriorApplication
import balekouy.industries.punchwarrior.presentation.BaseViewModel

class HomeViewModel : BaseViewModel() {
    init {
        initializeDagger(this)
    }

    private fun initializeDagger(viewModel: HomeViewModel) {
        UltraPunchWarriorApplication.appComponent.inject(viewModel)
    }
}
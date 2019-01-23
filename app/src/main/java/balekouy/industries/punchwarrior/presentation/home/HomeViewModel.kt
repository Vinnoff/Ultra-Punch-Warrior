package balekouy.industries.punchwarrior.presentation.home

import balekouy.industries.punchwarrior.di.UltraPunchWarriorApplication
import balekouy.industries.punchwarrior.domain.scores.ScoresUseCase
import balekouy.industries.punchwarrior.presentation.BaseViewModel
import javax.inject.Inject

class HomeViewModel : BaseViewModel(HomeViewModel::class.java.simpleName) {
    @Inject
    lateinit var scoresUseCase: ScoresUseCase

    init {
        initializeDagger(this)
        scoresUseCase.getAllScores()
    }

    private fun initializeDagger(viewModel: HomeViewModel) {
        UltraPunchWarriorApplication.appComponent.inject(viewModel)
    }


}
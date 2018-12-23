package balekouy.industries.punchwarrior.presentation.scores

import android.arch.lifecycle.MutableLiveData
import balekouy.industries.punchwarrior.data.models.Score
import balekouy.industries.punchwarrior.di.UltraPunchWarriorApplication
import balekouy.industries.punchwarrior.domain.DataResponse
import balekouy.industries.punchwarrior.domain.ErrorResponse
import balekouy.industries.punchwarrior.domain.scores.ScoresUseCase
import balekouy.industries.punchwarrior.presentation.BaseViewModel
import balekouy.industries.punchwarrior.presentation.BaseViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ScoresViewModel : BaseViewModel() {

    companion object {
        private const val TAG = "ScoresViewModel"
    }

    @Inject
    lateinit var scoresUseCase: ScoresUseCase

    private val compositeDisposable = CompositeDisposable()

    private val liveState: MutableLiveData<BaseViewState> = MutableLiveData()
    private val liveListScore: MutableLiveData<List<Score>> = MutableLiveData()

    init {
        initializeDagger(this)
        liveState.value = BaseViewState(isLoading = true, isError = false)
        val disposable = scoresUseCase.getScores()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                when (list) {
                    is DataResponse -> {
                        liveListScore.value = list.data.sortedByDescending { it.score }
                        liveState.value = liveState.value?.copy(isLoading = false, isError = false)
                    }
                    is ErrorResponse -> {
                        liveState.value = liveState.value?.copy(isLoading = false, isError = true)
                    }
                }
            }) { t: Throwable? ->
                super.setError(liveState, t, TAG)
            }
        compositeDisposable.add(disposable)
    }

    private fun initializeDagger(viewModel: ScoresViewModel) =
        UltraPunchWarriorApplication.appComponent.inject(viewModel)

    fun getLiveDataState() = liveState
    fun getLiveDataScores() = liveListScore
}
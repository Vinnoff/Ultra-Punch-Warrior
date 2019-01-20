package balekouy.industries.punchwarrior.presentation.levelselection

import android.arch.lifecycle.MutableLiveData
import balekouy.industries.punchwarrior.data.models.Level
import balekouy.industries.punchwarrior.di.UltraPunchWarriorApplication
import balekouy.industries.punchwarrior.domain.DataResponse
import balekouy.industries.punchwarrior.domain.ErrorResponse
import balekouy.industries.punchwarrior.domain.scores.LevelsUseCase
import balekouy.industries.punchwarrior.presentation.BaseViewModel
import balekouy.industries.punchwarrior.presentation.BaseViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LvlSelectViewModel : BaseViewModel(LvlSelectViewModel::class.java.simpleName) {

    @Inject
    lateinit var lvlUseCase: LevelsUseCase

    private val compositeDisposable = CompositeDisposable()

    private val liveState: MutableLiveData<BaseViewState> = MutableLiveData()
    private val liveListLevel: MutableLiveData<List<Level>> = MutableLiveData()

    init {
        initializeDagger(this)
        liveState.value = BaseViewState(isLoading = true, isError = false)
        val disposable = lvlUseCase.getLevels()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                when (list) {
                    is DataResponse -> {
                        liveListLevel.value = list.data
                        liveState.value = liveState.value?.copy(isLoading = false, isError = false)
                    }
                    is ErrorResponse -> {
                        liveState.value = liveState.value?.copy(isLoading = false, isError = true)
                    }
                }
            }) { t: Throwable? ->
                super.setError(liveState, t)
            }
        compositeDisposable.add(disposable)

    }

    private fun initializeDagger(viewModel: LvlSelectViewModel) =
        UltraPunchWarriorApplication.appComponent.inject(viewModel)

    fun getLiveDataState() = liveState
    fun getLiveDataLevels() = liveListLevel
}

package balekouy.industries.punchwarrior.presentation

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    internal fun setError(liveState: MutableLiveData<BaseViewState>, t: Throwable?, viewModel: String) {
        liveState.value = liveState.value?.copy(isLoading = false, isError = true)
        Log.e("Error in $viewModel", t.toString())
    }
}

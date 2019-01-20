package balekouy.industries.punchwarrior.presentation

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import balekouy.industries.punchwarrior.BuildConfig

abstract class BaseViewModel(private val tag: String) : ViewModel(), LifecycleObserver {
    internal fun setError(liveState: MutableLiveData<BaseViewState>, t: Throwable?) {
        liveState.value = liveState.value?.copy(isLoading = false, isError = true)
        Log.e("Error $tag", t.toString())
    }

    fun log(string: String) {
        if (BuildConfig.DEBUG) Log.d(tag, string)
    }
}

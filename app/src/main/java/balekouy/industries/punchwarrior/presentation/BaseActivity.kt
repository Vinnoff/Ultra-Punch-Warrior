package balekouy.industries.punchwarrior.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import balekouy.industries.punchwarrior.BuildConfig

abstract class BaseActivity constructor(
    private val tag: String,
    private val layoutId: Int
) : AppCompatActivity() {
    private lateinit var soundPlayer: SoundPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        soundPlayer = SoundPlayer.getInstance(applicationContext)
        initUI()
        initViewModel()
        initObservers()
    }


    open fun initUI() {}

    open fun initObservers() {}

    open fun initViewModel() {}

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    open fun showLoading() {
        if (BuildConfig.DEBUG) Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()

    }

    open fun hideLoading() {
        if (BuildConfig.DEBUG) Toast.makeText(this, "End Loading", Toast.LENGTH_SHORT).show()
    }

    open fun showEmptyList() {
        if (BuildConfig.DEBUG) Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show()
    }

    open fun showError() {
        if (BuildConfig.DEBUG) Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
    }

    open fun log(string: String) {
        if (BuildConfig.DEBUG) Log.d(tag, string)
    }

    fun startMusic(musicId: Int) {
        soundPlayer.startMusic(musicId)
    }

    fun pauseMusic() {
        soundPlayer.pauseMusic()
    }

    fun restartMusic() {
        soundPlayer.restartMusic()
    }

    fun stopMusic() {
        soundPlayer.stopMusic()
    }
}
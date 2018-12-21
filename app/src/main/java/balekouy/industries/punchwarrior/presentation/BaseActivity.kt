package balekouy.industries.punchwarrior.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

abstract class BaseActivity(private val layoutId: Int) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        window.decorView.apply { systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION }
        initUI()
        initObservers()
    }

    open fun initUI() {

    }

    open fun initObservers() {

    }
}
package balekouy.industries.punchwarrior.di

import android.app.Application

class UltraPunchWarriorApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .roomModule(RoomModule())
            .build()
    }
}


package com.weatherapp.di.application

import android.app.Application
import com.weatherapp.di.component.ApplicationComponent
import com.weatherapp.di.component.DaggerApplicationComponent
import com.weatherapp.di.modules.ApplicationModule

class WeatherApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent


    override fun onCreate() {
        super.onCreate()
        getDependencies()
    }

    private fun getDependencies() {

        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }

}

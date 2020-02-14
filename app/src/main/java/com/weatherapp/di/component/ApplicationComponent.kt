package com.weatherapp.di.component

import android.content.Context
import com.weatherapp.data.remote.NetworkService
import com.weatherapp.data.repo.UserRepository
import com.weatherapp.di.application.WeatherApplication
import com.weatherapp.di.modules.ApplicationModule
import com.weatherapp.utils.network.NetworkHelper
import com.weatherapp.utils.rx.SchedulerProvider
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: WeatherApplication)

    fun getContext() : Context

    fun getCompositeDisposable() : CompositeDisposable

    fun getSchedulerProvider(): SchedulerProvider

    fun getNetworkHelper(): NetworkHelper

    fun getUserRepository() : UserRepository

    fun getNetworkService() : NetworkService

}


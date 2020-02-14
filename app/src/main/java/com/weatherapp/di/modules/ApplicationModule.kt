package com.weatherapp.di.modules

import android.content.Context
import com.weatherapp.BuildConfig
import com.weatherapp.data.remote.NetworkService
import com.weatherapp.data.remote.Networking
import com.weatherapp.di.application.WeatherApplication
import com.weatherapp.utils.network.NetworkHelper
import com.weatherapp.utils.rx.RxSchedulerProvider
import com.weatherapp.utils.rx.SchedulerProvider
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: WeatherApplication) {


    @Provides
    fun provideContext(): Context = application

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Singleton
    @Provides
    fun provideNetworkHelper(): NetworkHelper = NetworkHelper(application)



    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService = Networking.create(

        BuildConfig.BASE_URL,
        application.cacheDir,
        10 * 1024 * 1024 // 10MB

    )
}
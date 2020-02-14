package com.weatherapp.di.modules

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import com.weatherapp.data.repo.UserRepository
import com.weatherapp.ui.base.BaseActivity
import com.weatherapp.ui.main.MainViewModel
import com.weatherapp.utils.ViewModelProviderFactory
import com.weatherapp.utils.network.NetworkHelper
import com.weatherapp.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule (private val activity:BaseActivity<*>){

    @Provides
    fun provideContext(
    ) : Context = activity



    @Provides
    fun provideMainViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        userRepository: UserRepository
    ): MainViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel(compositeDisposable, networkHelper, schedulerProvider, userRepository)
        }).get(MainViewModel::class.java)

}
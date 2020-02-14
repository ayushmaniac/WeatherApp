package com.weatherapp.ui.main

import androidx.lifecycle.MutableLiveData
import com.weatherapp.data.model.WeatherModel
import com.weatherapp.data.remote.response.WeatherResponse
import com.weatherapp.data.repo.UserRepository
import com.weatherapp.ui.base.BaseViewModel
import com.weatherapp.utils.network.NetworkHelper
import com.weatherapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.util.jar.Manifest

class MainViewModel(
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val weatherStatus = MutableLiveData<Boolean>()
    val weatherResponse = MutableLiveData<WeatherResponse>()


    override fun onCreate() {
    }


    fun getWeather(city : String) {

        compositeDisposable.addAll(

              userRepository.getData(city)
                  .subscribeOn(schedulerProvider.io())

                  .subscribe(
                      {
                          weatherStatus.postValue(true)
                          weatherResponse.postValue(it)

                      },
                      {

                          handleNetworkError(it)
                          weatherStatus.postValue(false)
                      }
                  )
            )


    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
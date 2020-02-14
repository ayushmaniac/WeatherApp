package com.weatherapp.data.repo

import com.weatherapp.data.remote.NetworkService
import com.weatherapp.data.remote.response.WeatherResponse
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val networkService: NetworkService


    ){



    fun getData(city : String): Single<WeatherResponse> =
        networkService.getCurrentWeatherData(city,"03afdecb8c2d0e5dc25474d54d505c51").map {


            WeatherResponse(
               it.coord,
                it.weather,
                it.base,
                it.main,
                it.visibility,
                it.wind,
                it.clouds,
                it.dt,
                it.sys,
                it.timezone,
                it.id,
                it.name,
                it.cod


            )
        }
}
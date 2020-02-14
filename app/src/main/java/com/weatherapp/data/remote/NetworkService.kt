package com.weatherapp.data.remote

import com.weatherapp.data.remote.response.WeatherResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @GET(Endpoints.WEATHER)
    fun getCurrentWeatherData(
        @Query("q") city: String,
        @Query("APPID") app_id: String): Single<WeatherResponse>
}

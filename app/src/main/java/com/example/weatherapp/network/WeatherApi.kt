package com.example.weatherapp.network

import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {

    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(@Query(value = "q")query: String,@Query(value = "units") units :String = "metric",
                           @Query(value = "appid") appid: String = Constants.API_KEY): WeatherData
}
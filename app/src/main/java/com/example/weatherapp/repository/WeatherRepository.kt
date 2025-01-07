package com.example.weatherapp.repository

import android.util.Log
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.network.WeatherApi
import javax.inject.Inject


class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(cityQuery:String,unitQuery: String): DataOrException<WeatherData,Boolean,Exception>{
        val response = try {
            api.getWeather(query = cityQuery, units = unitQuery)
        }catch (e:Exception){
            Log.d("MYEXP", "getWeather: $e")
            return  DataOrException(e=e)

        }
        Log.d("MYEXPD", "getWeather: ${response.toString()}")
        return DataOrException(
            data = response,
        )
    }
}
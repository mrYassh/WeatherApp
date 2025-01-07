package com.example.weatherapp.screen.mainscreen

import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) :ViewModel(){

   suspend fun getWeatherData(city :String="lisbon",unit :String):DataOrException<WeatherData,Boolean,Exception>{
       return repository.getWeather(cityQuery = city, unitQuery = unit)

   }
}
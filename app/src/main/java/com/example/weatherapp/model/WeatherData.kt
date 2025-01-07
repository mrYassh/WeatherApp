package com.example.weatherapp.model

data class WeatherData(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<details>,
    val message: Double
)
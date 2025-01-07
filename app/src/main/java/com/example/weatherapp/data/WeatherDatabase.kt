package com.example.weatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.model.Units


@Database(entities = [Favorite::class,Units::class], version = 2, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase(){
    abstract fun weatherDao ():WeatherDao
}
package com.example.weatherapp.repository

import com.example.weatherapp.data.WeatherDao
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.model.Units
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDBRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavorites() : Flow<List<Favorite>> = weatherDao.getAllFavorites()
    suspend fun getFavId(city:String):Favorite = weatherDao.getFavoriteById(city)
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
    suspend fun deleteAllFavorite() = weatherDao.deleteAllFavorites()

    fun getUnits() : Flow<List<Units>> = weatherDao.getUnits()
    suspend fun insertUnit(units: Units) = weatherDao.insertUnit(units)
    suspend fun updateUnit(units: Units) = weatherDao.updateUnit(units)
    suspend fun deleteUnit(units: Units) = weatherDao.deleteUnit(units)
    suspend fun deleteAllUnit() = weatherDao.deleteAllUnits()
}
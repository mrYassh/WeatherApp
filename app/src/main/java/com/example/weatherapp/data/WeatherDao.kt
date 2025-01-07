package com.example.weatherapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.model.Units
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDao {
    @Query("SELECT * from favorite_tbl")
    fun  getAllFavorites(): Flow<List<Favorite>>

    @Query("SELECT * from favorite_tbl where city=:city")
    suspend fun getFavoriteById(city:String):Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query("DELETE  from favorite_tbl")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)


    @Query("SELECT * from setting_tbl")
    fun  getUnits(): Flow<List<Units>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(units: Units)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(units: Units)

    @Query("DELETE  from setting_tbl")
    suspend fun deleteAllUnits()

    @Delete
    suspend fun deleteUnit(units: Units)

}
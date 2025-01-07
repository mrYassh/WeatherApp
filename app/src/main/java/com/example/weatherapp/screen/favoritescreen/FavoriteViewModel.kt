package com.example.weatherapp.screen.favoritescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.repository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository : WeatherDBRepository) :ViewModel(){
  private  val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
      viewModelScope.launch (Dispatchers.IO){
        repository.getFavorites().distinctUntilChanged().collect{
          listOfFavs ->
          if (listOfFavs.isNullOrEmpty()){
            Log.d("Fav", "Null: ")
          }else{
            _favList.value = listOfFavs
            Log.d("fav", "${favList.value}")
          }
        }
      }
    }

  fun getFavoriteById(city:String)=viewModelScope.launch { repository.getFavId(city) }
  fun insertFavorite(favorite: Favorite) = viewModelScope.launch { repository.insertFavorite(favorite) }
  fun updateFavorite(favorite: Favorite) = viewModelScope.launch { repository.updateFavorite(favorite) }
  fun deleteFavorite(favorite: Favorite) = viewModelScope.launch { repository.deleteFavorite(favorite) }

}
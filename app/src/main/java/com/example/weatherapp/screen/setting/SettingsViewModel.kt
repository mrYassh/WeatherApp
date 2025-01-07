package com.example.weatherapp.screen.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.Units
import com.example.weatherapp.repository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository : WeatherDBRepository):ViewModel() {
    private val _unitList = MutableStateFlow<List<Units>>(emptyList())
    val  unitList = _unitList.asStateFlow()
    init {
        viewModelScope.launch (Dispatchers.IO){
            repository.getUnits().distinctUntilChanged().collect{
                listUnit ->
                if (listUnit.isNullOrEmpty()){
                    Log.d("units", ":Empty List ")
                }else{
                    _unitList.value =listUnit
                }
            }
        }
    }

    fun insertUnit(units: Units) = viewModelScope.launch { repository.insertUnit(units) }
    fun updateUnit(units: Units) = viewModelScope.launch { repository.updateUnit(units) }
    fun deleteUnit(units: Units) = viewModelScope.launch { repository.deleteUnit(units) }
    fun deleteAllUnits()=viewModelScope.launch { repository.deleteAllUnit() }
}
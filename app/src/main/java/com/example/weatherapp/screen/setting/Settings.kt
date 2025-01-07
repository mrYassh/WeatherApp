package com.example.weatherapp.screen.setting


import androidx.compose.foundation.background
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.components.WeatherAppBar
import com.example.weatherapp.model.Units

@Composable
fun SettingScreen(navController: NavController,settingsViewModel: SettingsViewModel = hiltViewModel()){
    var toggleState = rememberSaveable {
        mutableStateOf(false)
    }
    var measurementUnits = listOf("Imperial (F)","Metric (C)",)
    var choiceFromDB = settingsViewModel.unitList.collectAsState().value
   val defChoice= if (choiceFromDB.isNullOrEmpty()){measurementUnits[0]}else{choiceFromDB[0].unit}
    var choiceState  = rememberSaveable{
        mutableStateOf(defChoice)
    }
Scaffold(topBar = { WeatherAppBar(title = "Settings", icon = Icons.AutoMirrored.Filled.ArrowBack, isMainScreen = false,navController = navController, onButtonClicked = {navController.popBackStack()})}) {
    it
Surface(modifier = Modifier.fillMaxSize(),color = Color(0xFFBEE5F1)) {
    Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
        Text(text = "Tap to Change the Units of Weather", fontSize = 20.sp, fontWeight = FontWeight.W300)
        Spacer(modifier = Modifier.height(25.dp))
        IconToggleButton(checked = !toggleState.value, onCheckedChange = {
            toggleState.value = !it
            if (toggleState.value){
                choiceState.value = measurementUnits[0]
            }else{
                choiceState.value = measurementUnits[1]
            }
            toggleState.value = !it
        }, modifier = Modifier
            .fillMaxWidth(fraction = 0.5f)
            .clip(RectangleShape)
            .padding(5.dp)
            .background(
                Color.Green.copy(alpha = 0.5f)
            )) {
            Text(
                text = if (toggleState.value) {
                    measurementUnits[0]
                }else{
                    measurementUnits[1]
                }

            )
        }
        Button(onClick = { settingsViewModel.deleteAllUnits()
            settingsViewModel.insertUnit(Units(choiceState.value))}, colors = ButtonDefaults.buttonColors(
            Color.Yellow.copy(alpha = 0.5f)
        ), modifier = Modifier.padding(10.dp), shape = RectangleShape) {
            Text(text = "Save", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }

}
}
}



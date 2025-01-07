package com.example.weatherapp.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.components.WeatherAppBar

@Composable
fun AboutScreen(navController: NavController){

        Column {
            Scaffold(topBar = { WeatherAppBar(title = "About us", icon = Icons.AutoMirrored.Filled.ArrowBack,isMainScreen = false,navController = navController, onButtonClicked = {navController.popBackStack()})}) {
                it
                Surface(modifier = Modifier.fillMaxSize(),color = Color(0xFFBEE5F1)) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 50.dp, horizontal = 25.dp), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = R.drawable.cloudy), contentDescription = "App Logo")
                    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){Text(text = "My Weather App", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                        Text(text = "version : 1.0", fontSize = 20.sp, fontWeight = FontWeight.W500)}
                    Text(text = " Weather App is the application of science and technology to predict the conditions of the atmosphere for a given location and time.", textAlign = TextAlign.Center)
                    Text(text = "This App is developed by\n Mr. Yash Dipke", fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Text(text = "© All rights reserved", fontWeight = FontWeight.Bold)
                }
            }

        }
    }
}

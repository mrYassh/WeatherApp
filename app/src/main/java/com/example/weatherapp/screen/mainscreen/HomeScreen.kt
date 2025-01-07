package com.example.weatherapp.screen.mainscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.components.WeatherAppBar
import com.example.weatherapp.components.WeatherImage
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.model.details
import com.example.weatherapp.navigation.WeatherScreens
import com.example.weatherapp.screen.setting.SettingsViewModel
import com.example.weatherapp.utils.formatDate
import com.example.weatherapp.utils.formatDecimals

@Composable
fun HomeScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel= hiltViewModel(),
    city: String
){
ShowData(mainViewModel = mainViewModel,settingsViewModel,navController,city)
}

@Composable
fun ShowData(
    mainViewModel: MainViewModel,
    settingsViewModel: SettingsViewModel,
    navController: NavController,
    city: String
){
    val unitFromDB = settingsViewModel.unitList.collectAsState().value
    val unit = if (unitFromDB.isNullOrEmpty()){ "metric"}else{unitFromDB[0].unit.split(" ")[0].lowercase()}
    val isMetric = (unit=="metric")
    val weatherInfo = produceState<DataOrException<WeatherData,Boolean,Exception>>(initialValue =DataOrException(loading = true) ) {
        value = mainViewModel.getWeatherData(city = city, unit = unit)

    }.value

    if(weatherInfo.loading ==true){
        Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFBEE5F1)) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
            }
        }
    }
    else if(weatherInfo.data !=null){
       MainData(weatherInfo.data,navController,isMetric)
    }
}

@Composable
fun MainData(weatherInfo: WeatherData?, navController: NavController, isMetric: Boolean) {
    Scaffold (topBar = {WeatherAppBar(title = weatherInfo!!.city.name + ", ${weatherInfo.city.country}", isMainScreen = true, navController = navController,onAddActionClicked = {navController.navigate(WeatherScreens.SearchScreen.name)})}){
        it.calculateTopPadding()
        MainContent(weatherInfo = weatherInfo, navController = navController,isMetric)
    }
        
    
}

@Composable
fun MainContent(
    weatherInfo: WeatherData?,
    navController: NavController,
    isMetric: Boolean
) {
    val imageUrl = "https://openweathermap.org/img/wn/${weatherInfo?.list?.get(0)?.weather?.get(0)?.icon}.png"
    Column (modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFFBEE5F1))
        .padding(5.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally){
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = formatDate(weatherInfo!!.list[0].dt))
        Surface(modifier = Modifier
            .padding(2.dp)
            .size(300.dp),shape = CircleShape, border = BorderStroke(2.dp, color = Color.White), color = Color(0xFFBEE5F1)) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = weatherInfo.list[0].weather[0].description.format(), fontSize = 30.sp, fontStyle = FontStyle.Italic ,color = Color.DarkGray)
                WeatherImage(imageurl = imageUrl)
                Text(text = "${formatDecimals(weatherInfo.list[0].temp.day)} ${if(isMetric) "°C" else "°F" }", fontWeight = FontWeight.Bold, fontSize = 30.sp )
            }
        }
        HumidityPressureAd(weatherInfo,isMetric)
        HorizontalDivider(modifier = Modifier.padding(5.dp))
        Surface (modifier = Modifier
            .fillMaxSize()
           , shape = CircleShape.copy(all = CornerSize(8.dp)), color = Color(0xFFD3F2FC)
        ){
            LazyColumn(modifier = Modifier.padding(5.dp),
                contentPadding = PaddingValues(1.dp)
            ) {
                items(items = weatherInfo.list){
                    WeatherRow(it,isMetric)
                }
            }
        }
    }

}

@Composable
fun WeatherRow(weather: details, isMetric: Boolean) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"
    Surface (modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .height(60.dp),shape = CircleShape.copy(all = CornerSize(15.dp)), color = Color(0xFFBEE5F1)
    ){
        Row (modifier = Modifier
            .fillMaxWidth()
           , verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
            Text(text = formatDate(weather.dt).split(",")[0], modifier = Modifier.padding(start = 5.dp))
            WeatherImage(imageurl = imageUrl)
            Surface (modifier = Modifier
                .width(100.dp)
                .height(40.dp)
                .padding(5.dp),shape = CircleShape.copy(all = CornerSize(15.dp)), color = Color.LightGray){
               Row (modifier = Modifier
                   .fillMaxWidth()
                   .padding(2.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                   Text(text = weather.weather[0].description, textAlign = TextAlign.Center)
               }
            }
            Row (modifier = Modifier

                .padding(2.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                Text(text = formatDecimals(weather.temp.max), color = Color.Red.copy(alpha = 0.5f), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = formatDecimals(weather.temp.min), color = Color.White.copy(1.0f), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = if(isMetric) "°C" else "°F" , fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 5.dp))
            }
        }
        
    }
}

@Composable
fun HumidityPressureAd(weatherInfo: WeatherData, isMetric: Boolean) {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(5.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weatherInfo.list[0].humidity.toString()}%", style = MaterialTheme.typography.bodyLarge)
        }
            Row() {
                Icon(
                    painter = painterResource(id = R.drawable.pressure),
                    contentDescription = "pressure",
                    modifier = Modifier.size(20.dp)
                )
                Text(text = "${weatherInfo.list[0].pressure.toString()} ${if(isMetric) "pa" else "°psi" }",style = MaterialTheme.typography.bodyLarge)
            }
            Row() {
                Icon(
                    painter = painterResource(id = R.drawable.windy),
                    contentDescription = "wind speed",
                    modifier = Modifier.size(20.dp)
                )
                Text(text = "${formatDecimals(weatherInfo.list[0].speed)} ${if(isMetric) "m/s" else "mph" }",style = MaterialTheme.typography.bodyLarge)
            }



    }
}

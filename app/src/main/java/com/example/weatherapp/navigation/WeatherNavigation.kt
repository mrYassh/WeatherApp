package com.example.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.screen.about.AboutScreen
import com.example.weatherapp.screen.mainscreen.HomeScreen
import com.example.weatherapp.screen.mainscreen.MainViewModel
import com.example.weatherapp.screen.searchscreen.SearchScreen
import com.example.weatherapp.screen.setting.SettingScreen
import com.example.weatherapp.screen.splashscreen.SplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.weatherapp.screen.favoritescreen.FavortiesScreen
import com.example.weatherapp.screen.setting.SettingsViewModel

@Composable
fun WeatherNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination =WeatherScreens.SplashScreen.name ) {
        composable(WeatherScreens.SplashScreen.name){
            SplashScreen(navController = navController)
        }
        val route = WeatherScreens.HomeScreen.name
        composable("$route/{city}", arguments = listOf(navArgument(name = "city"){
            type = NavType.StringType
        })){
            navBack ->
            navBack.arguments?.getString("city").let {
                city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                val settingsViewModel = hiltViewModel<SettingsViewModel>()
                HomeScreen(navController = navController,mainViewModel,settingsViewModel,city.toString())
            } }

        composable(WeatherScreens.SearchScreen.name){
            SearchScreen(navController = navController)
        }
        composable(WeatherScreens.FavourtiesScreen.name){
            FavortiesScreen(navController = navController)
        }
        composable(WeatherScreens.SettingScreen.name){
            SettingScreen(navController = navController)
        }
        composable(WeatherScreens.AboutScreen.name){
            AboutScreen(navController = navController)
        }

    }
}
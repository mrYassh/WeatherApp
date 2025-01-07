package com.example.weatherapp.screen.favoritescreen

import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.components.ShowToast
import com.example.weatherapp.components.WeatherAppBar
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.navigation.WeatherScreens

@Composable
fun FavortiesScreen(navController: NavController,favoriteViewModel: FavoriteViewModel = hiltViewModel()){
    Scaffold(topBar = { WeatherAppBar(title = "Favorite Screen", icon = Icons.AutoMirrored.Filled.ArrowBack, isMainScreen = false,navController = navController, onButtonClicked = {navController.popBackStack()}) }) {
        it
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),color = Color(0xFFBEE5F1)) {



                FavoriteItems(  navController = navController, favoriteViewModel = favoriteViewModel)

        }
    }
}


@Composable
fun FavoriteItems(modifier: Modifier = Modifier,navController: NavController,favoriteViewModel: FavoriteViewModel){
    val listFav = favoriteViewModel.favList.collectAsState().value
LazyColumn {
    items(items = listFav){
        FavoriteCard( fav = it, navController = navController, favoriteViewModel = favoriteViewModel)
    }
}
}

@Composable
fun FavoriteCard(modifier: Modifier= Modifier, fav: Favorite, navController: NavController, favoriteViewModel: FavoriteViewModel){
    val context = LocalContext.current
    var showToast = rememberSaveable {
        mutableStateOf(false)
    }

        Card (modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(15.dp)
            .clickable { navController.navigate(WeatherScreens.HomeScreen.name + "/${fav.city}") }) {
            Row (modifier = modifier
                .height(100.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
                Text(text = fav.city, fontSize = 25.sp)
                Text(text = fav.country,fontSize = 25.sp, fontWeight = FontWeight.Bold)
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", modifier = modifier
                    .size(25.dp)
                    .clickable {
                        favoriteViewModel
                            .deleteFavorite(fav)
                            .run {
                                showToast.value = true
                            }


                    }, tint = Color.Red)
            }
            if (showToast.value) {
                ShowToast(context,txt = "Deleted from Favorites...",showToast)

            }
    }
}


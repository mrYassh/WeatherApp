package com.example.weatherapp.screen.searchscreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.weatherapp.components.WeatherAppBar
import com.example.weatherapp.navigation.WeatherScreens

@Composable
fun SearchScreen(navController: NavHostController) {
        Scaffold(topBar = { WeatherAppBar(title = "Search", icon = Icons.AutoMirrored.Filled.ArrowBack,isMainScreen = false, navController = navController,onButtonClicked = {navController.popBackStack()})}){
           it
Column {
   Surface {
      SearchBar()  {city ->
         Log.d("city", "SearchScreen:$city ")
         navController.navigate(WeatherScreens.HomeScreen.name+"/$city")
      }
   }
}
        }
}
@Composable
fun SearchBar(search:(String)->Unit={}){
   val searchQueryState = rememberSaveable {
      mutableStateOf("")
   }
   val valid = rememberSaveable(searchQueryState.value) {
      searchQueryState.value.trim().isNotEmpty()
   }
   val keyboardController = LocalSoftwareKeyboardController.current
   CommonTextField(valueState = searchQueryState,placeholder = "City Name",onAction = KeyboardActions {
      if(!valid)return@KeyboardActions
      search(searchQueryState.value.trim())
      searchQueryState.value=""
      keyboardController!!.hide()
   })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTextField(valueState: MutableState<String>, placeholder: String,keyboardType :KeyboardType= KeyboardType.Text,imeAction: ImeAction = ImeAction.Next ,onAction: KeyboardActions) {
OutlinedTextField(value = valueState.value, onValueChange ={valueState.value = it} , label = { Text(
   text = placeholder
)}, maxLines = 1, singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction), keyboardActions = onAction, colors = TextFieldDefaults.colors(focusedTextColor = Color.Blue, cursorColor = Color.Black), shape = RoundedCornerShape(10.dp), modifier = Modifier
   .fillMaxWidth()
   .padding(start = 10.dp, top = 50.dp, end = 10.dp))
}

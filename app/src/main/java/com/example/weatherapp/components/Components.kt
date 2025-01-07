package com.example.weatherapp.components


import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.model.Favorite

import com.example.weatherapp.navigation.WeatherScreens
import com.example.weatherapp.screen.favoritescreen.FavoriteViewModel


@Composable
fun ShowDropDownBox(isDialog: MutableState<Boolean>, navController: NavController) {
  var isExpanded by remember {
    mutableStateOf(value = true)}
    var items = listOf("Favorites","About","Settings")
    Column (modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.TopEnd)
        .absolutePadding(top = 40.dp, right = 20.dp)){
        DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false}, modifier = Modifier
            .width(150.dp)
            .background(color = Color.White) ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(text = { Text(text = s, fontWeight = FontWeight.W300) }, onClick = { isExpanded = false
                                                                      isDialog.value = false
                    when(s){
                        "Favorites" -> navController.navigate(WeatherScreens.FavourtiesScreen.name)
                        "About" -> navController.navigate(WeatherScreens.AboutScreen.name)
                        else -> navController.navigate(WeatherScreens.SettingScreen.name)
                    }}, leadingIcon = {
                    Icon(
                        imageVector = when(s){
                            "Favorites" -> Icons.Default.Favorite
                            "About" -> Icons.Default.Info
                            else -> Icons.Default.MoreVert
                        },
                        contentDescription = null,
                        tint = Color.LightGray
                    )
                })
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 10.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
){
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp), elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        val isDialog = remember {
            mutableStateOf(false);
        }
        val context = LocalContext.current

        var isDel = rememberSaveable {
            mutableStateOf(false)
        }
        var isAdd = rememberSaveable {
            mutableStateOf(false)
        }
        TopAppBar(title = { Text(text = title, modifier = Modifier.padding(start = 25.dp),style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)) },
            modifier = Modifier,
            actions = {
                if (isMainScreen){
                    IconButton(onClick = { onAddActionClicked.invoke() }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }
                    IconButton(onClick = { isDialog.value= !isDialog.value }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                    }

                }else Box{}
            }, navigationIcon = {
                if (icon!=null){
                    Icon(imageVector = icon, contentDescription = null, modifier = Modifier.clickable { onButtonClicked.invoke() })
                }
                if (isMainScreen){
                    val dataList = title.split(",")
                    val myFav = Favorite(city = dataList[0], country = dataList[1])
                   val alreadyExist= favoriteViewModel.favList.collectAsState().value.filter {
                       item->
                       item.city == dataList[0]
                   }
                    if (alreadyExist.isNullOrEmpty()){
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                favoriteViewModel.insertFavorite(myFav)
                                    .run { isAdd.value = true }
                            },
                            tint = Color.Red
                        )
                    }
                    else {
                        Box{}

//                        Icon(
//                            imageVector = Icons.Default.FavoriteBorder,
//                            contentDescription = null,
//                            modifier = Modifier.clickable {
//                                favoriteViewModel.deleteFavorite(myFav)
//                                    .run { isDel.value = true }
//                            },
//                            tint = Color.LightGray)
                    }
                }
            }
        )
        if (isDialog.value){
            ShowDropDownBox(isDialog =isDialog,navController = navController)
        }
        if (isAdd.value){
            ShowToast(context = context, txt = "ADDED to Favorites...", showToast =isAdd )
        }
        if (isDel.value){
            ShowToast(context = context, txt = "Deleted From  Favorites...", showToast =isDel )
        }
    }
}

@Composable
fun WeatherImage(imageurl : String){
    //var imageUri by remember { mutableStateOf<Uri?>(imageurl) }
    //Image(painter = rememberAsyncImagePainter(data=imageUri,), contentDescription = null)
    Image(painter = rememberAsyncImagePainter(model = imageurl), contentDescription =null, modifier = Modifier.size(100.dp) )
}

@Composable
fun ShowToast(context: Context, txt:String, showToast : MutableState<Boolean>) {
        Toast.makeText(
            context, txt, Toast.LENGTH_SHORT
        ).show()
    showToast.value = false
}

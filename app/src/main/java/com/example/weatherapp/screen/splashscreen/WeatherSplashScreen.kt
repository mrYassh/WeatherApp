package com.example.weatherapp.screen.splashscreen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.navigation.WeatherScreens
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController){
    val cityname = "Hyderabad"
    val scale = remember {
        Animatable(0.0f)
    }
    LaunchedEffect(key1 = true, block = {
        scale.animateTo(0.9f, animationSpec = tween(durationMillis = 1000, easing = {OvershootInterpolator(8f).getInterpolation(it)}))
        delay(2000L)
        navController.navigate(WeatherScreens.HomeScreen.name+"/$cityname")
    }
    )
    Surface(modifier = Modifier
        .padding(2.dp)
        .scale(scale.value)) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.cloudy), contentDescription ="Logo" )
            Text(text = "Weather App", style = MaterialTheme.typography.headlineSmall.copy(Color(
                0xFF6BD5F8
            )
            ), fontWeight = FontWeight.Bold, fontSize = 30.sp
            )

        }

    }
}
package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF4CE5CB)
                ) {
                   App()
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun App(){

    val  navController = rememberNavController()
    NavHost(navController = navController, startDestination = "WeatherAppMainUI"){

        composable( "WeatherAppMainUI" ){WeatherAppMainUI(navController)}
        composable("DailyForecast"){DailyForecast(navController)}

    }

}



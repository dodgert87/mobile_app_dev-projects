package com.example.weatherapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.ui.res.stringResource


@Composable
fun DailyForecast(navController: NavHostController) {
    val weatherDataList = listOf(
        WeatherData("Monday", "Sunny", 25, "North"),
        WeatherData("Tuesday", "Rainy", 20, "West"),
        WeatherData("Wednesday", "Rainy", 15, "South"),
        WeatherData("Thursday", "Sunny", 28, "East"),
        WeatherData("Friday", "Cloudy", 22, "Northwest")
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){

        Spacer(modifier = Modifier.height(50.dp))
        Header()
        LazyColumn{

            items(weatherDataList){
                weatherData -> DailyStat(weatherData)
            }
        }


        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { navController.navigate("WeatherAppMainUI") },
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ){
            Text(text = stringResource(R.string.main_page))
        }

    }

}
@Preview(showBackground = true)
@Composable
fun DailyForecastPreview(){
    val navController = rememberNavController()
    DailyForecast(navController = navController)
}
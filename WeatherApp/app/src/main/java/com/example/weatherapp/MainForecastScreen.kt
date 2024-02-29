package com.example.weatherapp

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun WeatherAppMainUI(navController: NavHostController) {

    var rawWeatherData by remember { mutableStateOf<RawData?>(null)}
    var weatherData by remember { mutableStateOf<WeatherData?>(null)}
    rawWeatherData =  fetchData()

    if (rawWeatherData != null){

        weatherData = WeatherData(rawWeatherData!!.day, rawWeatherData!!.weather.first().main.lowercase(), rawWeatherData!!.main.temp.toInt(), rawWeatherData!!.wind.deg, rawWeatherData!!.wind.speed.toInt())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            ) {

            Spacer(modifier = Modifier.height(50.dp))
            Header()
            Spacer(modifier = Modifier.height(50.dp))

            Row (modifier = Modifier
                .padding(30.dp)
                .align(Alignment.CenterHorizontally)){

                Image(
                    painter = painterResource(id = weatherData!!.getIcon(weatherData!!.description)),
                    contentDescription = "Status Icon",
                    modifier = Modifier.size(100.dp),
                )


                Spacer(modifier = Modifier.width(50.dp))
                Row {
                    Text(text = weatherData!!.temperature.toString(),
                        fontSize = 30.sp)
                    Spacer(modifier = Modifier.width(7.dp))

                    Text(text = stringResource(R.string.degree_symbol),
                        fontSize = 30.sp)
                }
            }

            Spacer(modifier = Modifier.height(50.dp))
            Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)){
                Text(text = stringResource(R.string.weather_status),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(vertical = 20.dp)
                )

                Text(text = stringResource(weatherData!!.getDescription(weatherData!!.description)),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(vertical = 20.dp, horizontal = 7.dp)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))
            Row(modifier = Modifier.padding(20.dp)){

                Text(
                    text = stringResource(R.string.wind_speed),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Left,
                )

                Spacer(modifier = Modifier.width(5.dp))
                Text(text =weatherData!!.windSpeed.toString(),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Left,)

                Spacer(modifier = Modifier.width(5.dp))
                Text(text =stringResource(R.string.speed_unit),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Left,)
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = stringResource(weatherData!!.getWindDirection(weatherData!!.windDirection)),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Left,
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { navController.navigate("DailyForecast") },
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ){
                Text(text = stringResource(R.string.daily_report))
            }
        }
    }else{
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(modifier = Modifier.size(70.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherAppMainUIPreview(){
    val navController = rememberNavController()
    WeatherAppMainUI(navController = navController )
}
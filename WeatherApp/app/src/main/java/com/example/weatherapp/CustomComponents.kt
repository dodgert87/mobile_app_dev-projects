package com.example.weatherapp

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable

fun Header(){
    Text(
        stringResource(R.string.city_name),
        fontSize = 60.sp,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF3F51B5)),
        textAlign = TextAlign.Center,
        color = Color(0xFFFFFFFF)
    )
}

@Composable
fun DailyStat(weatherData: WeatherData){

    Spacer(modifier = Modifier.height(50.dp))
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(weatherData.getDay(weatherData.day)),
            fontSize = 15.sp,
            modifier = Modifier
                .padding(vertical = 13.dp, horizontal = 6.dp)
                .weight(4.8f)
        )
        Log.d("MyActivity", "Temperature retrieved successfully: day")

        Column (modifier = Modifier.weight(3f)){
            Text(
                text = stringResource(weatherData.getDescription(weatherData.description)),
                fontSize = 15.sp,
            )
            Log.d("MyActivity", "Temperature retrieved successfully: descrption")

            Text(
                text = "${weatherData.temperature} " + stringResource(R.string.degree_symbol),
                fontSize = 15.sp,
            )
            Log.d("MyActivity", "Temperature retrieved successfully: temp")

        }

        Text(
            text = weatherData.windSpeed.toString(),
            fontSize = 15.sp,
            modifier = Modifier
                .padding(vertical = 13.dp)
                .weight(1f)
        )
        Text(
            text = stringResource(R.string.speed_unit),
            fontSize = 15.sp,
            modifier = Modifier
                .padding(vertical = 13.dp)
                .weight(2.5f)
        )
        Text(
            text = stringResource(weatherData.getWindDirection(weatherData.windDirection)),
            fontSize = 15.sp,
            modifier = Modifier
                .padding(vertical = 13.dp)
                .weight(2.5f)
        )
        Log.d("MyActivity", "Temperature retrieved successfully: winddirection")

        Log.d("MyActivity", "Temperature retrieved successfully: ${weatherData.description}")

        Image(
            painter = painterResource(id = weatherData.getIcon(weatherData.description)),
            contentDescription = "Status Icon",
            modifier = Modifier
                .size(50.dp)
        )
        Log.d("MyActivity", "Temperature retrieved successfully: image")

    }
}
package com.example.weatherapp

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
                painter = painterResource(id = R.drawable.cloudy_icon),
                contentDescription = "Status Icon",
                modifier = Modifier.size(100.dp),
            )
            Spacer(modifier = Modifier.width(50.dp))
            Row {
                Text(text = "-18",
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
            Text(text = stringResource(R.string.Rainy),
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
            Text(text ="5",
                fontSize = 24.sp,
                textAlign = TextAlign.Left,)
            Spacer(modifier = Modifier.width(5.dp))
            Text(text =stringResource(R.string.speed_unit),
                fontSize = 24.sp,
                textAlign = TextAlign.Left,)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = stringResource(R.string.Northeast),
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
}

@Preview(showBackground = true)
@Composable
fun WeatherAppMainUIPreview(){
    val navController = rememberNavController()
    WeatherAppMainUI(navController = navController)
}
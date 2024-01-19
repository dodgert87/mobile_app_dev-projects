package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    WeatherAppUI()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WeatherAppUI(){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),


    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Text( "Tampere",
            fontSize = 60.sp,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF1FE0A1)),
            textAlign = TextAlign.Center,
            color = Color(0xFFE62861)
        )
        Spacer(modifier = Modifier.height(50.dp))

        Row (modifier = Modifier
            .padding(30.dp)
            .align(Alignment.CenterHorizontally)){
            Image(
                painter = painterResource(id = R.drawable.mostly_sunny_icon),
                contentDescription = "Status Icon",
                modifier = Modifier.size(100.dp),
                )
            Spacer(modifier = Modifier.width(50.dp))
            Text(text = "-18° C",
                fontSize = 30.sp)
        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = "Status : Mostly Sunny ",
            fontSize = 24.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(20.dp)

            )
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Wind Speed : 5Km/h ,NE",
            fontSize = 24.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(20.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { /*TODO*/ },
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
            ){
            Text(text = "Refresh Weather Status")
        }
    }
}


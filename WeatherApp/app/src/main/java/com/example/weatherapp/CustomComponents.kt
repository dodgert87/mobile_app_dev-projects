package com.example.weatherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
fun DailyStat(){

    Spacer(modifier = Modifier.height(50.dp))
    Row (modifier = Modifier
        .padding(15.dp)){

        Text(text = stringResource(R.string.friday),
            fontSize = 15.sp,
            modifier = Modifier.padding(vertical = 13.dp, horizontal = 8.dp)
        )
        Text(text = stringResource(R.string.sunny),
            fontSize = 15.sp,
            modifier = Modifier.padding(vertical = 13.dp, horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "22",
            fontSize = 15.sp,
            modifier = Modifier.padding(vertical = 13.dp, horizontal = 4.dp)
        )

        Text(text = stringResource(R.string.degree_symbol),
            fontSize = 15.sp,
            modifier = Modifier.padding(vertical = 13.dp)
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(text = "5",
            fontSize = 15.sp,
            modifier = Modifier.padding(vertical = 13.dp)
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(text = stringResource(R.string.speed_unit),
            fontSize = 15.sp,
            modifier = Modifier.padding(vertical = 13.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = stringResource(R.string.ne),
            fontSize = 15.sp,
            modifier = Modifier.padding(vertical = 13.dp)

        )
        Spacer(modifier = Modifier.width(10.dp))

        Image(
            painter = painterResource(id = R.drawable.sunny_icon),
            contentDescription = "Status Icon",
            modifier = Modifier
                .size(50.dp)

        )

    }
}
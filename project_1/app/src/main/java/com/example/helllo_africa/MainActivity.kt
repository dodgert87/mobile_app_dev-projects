package com.example.helllo_africa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.helllo_africa.ui.theme.Helllo_africaTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Helllo_africaTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    HelloAfrica();
                }
            }
        }
    }
}

@Composable
fun HelloAfrica( modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        val coloredText = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Green)) {
                append("Hello ")
            }
            withStyle(style = SpanStyle(color = Color.Red)) {
                append("Africa ")
            }
            withStyle(style = SpanStyle(color = Color.White)) {
                append("Cup")
            }
        }
        Text(
            text =coloredText,
            textAlign = TextAlign.Center,
            fontSize = 24.sp


        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Helllo_africaTheme {
        HelloAfrica()
    }
}
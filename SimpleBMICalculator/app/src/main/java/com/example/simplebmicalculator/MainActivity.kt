package com.example.simplebmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.DecimalFormat


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMICalculatorApp()
        }
    }
}

@Composable
fun BMICalculatorApp() {
    var weight by remember { mutableStateOf("") }
    var heightInCm by remember { mutableStateOf("") }
    var bmi by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Weight (kg)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = heightInCm,
                onValueChange = { heightInCm = it },
                label = { Text("Height (cm)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val weightValue = weight.toFloatOrNull()
                val heightValue = heightInCm.toFloatOrNull()?.div(100)
                if (weightValue != null && heightValue != null && heightValue > 0) {
                    val bmiValue = weightValue / (heightValue * heightValue)
                    val decimalFormat = DecimalFormat("#.##")
                    bmi = decimalFormat.format(bmiValue)
                } else {
                    bmi = null
                }
            }) {
                Text("Calculate BMI")
            }
            bmi?.let {
                Text("Your BMI is: $it", Modifier.padding(top = 16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BMICalculatorApp()
}
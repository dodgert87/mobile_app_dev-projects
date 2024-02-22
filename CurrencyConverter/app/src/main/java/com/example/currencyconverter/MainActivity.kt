package com.example.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // Use a Surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    CurrencyConverterApp()
                }
            }
        }
    }

    @Composable
    fun CurrencyConverterApp() {
        var amount by remember { mutableStateOf("") }
        var fromCurrency by remember { mutableStateOf(currencies[0]) }
        var toCurrency by remember { mutableStateOf(currencies[1]) }
        var result by remember { mutableStateOf<String?>(null) }

        Column(modifier = Modifier.padding(16.dp)) {
            // Amount input and From currency dropdown side by side
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(Modifier.width(8.dp))

                CurrencyDropdown(
                    selectedCurrency = fromCurrency,
                    onCurrencySelected = { fromCurrency = it }
                )
            }

            Spacer(Modifier.height(16.dp))

            // Convert button
            Button(
                onClick = {
                    result = try {
                        val inputAmount = amount.toFloat()
                        val rate = exchangeRates[fromCurrency]?.get(toCurrency) ?: 1f
                        val convertedAmount = inputAmount * rate
                        "%.2f".format(convertedAmount)
                    } catch (e: Exception) {
                        "Invalid input"
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Convert")
            }

            Spacer(Modifier.height(16.dp))

            // Result display and To currency dropdown side by side
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = result ?: ("Result:" + ""),
                    modifier = Modifier.weight(1f)
                )
                CurrencyDropdown(
                    selectedCurrency = toCurrency,
                    onCurrencySelected = { toCurrency = it }
                )
            }
        }
    }

    @Composable
    fun CurrencyDropdown(
        selectedCurrency: String,
        onCurrencySelected: (String) -> Unit
    ) {
        var expanded by remember { mutableStateOf(false) }

        Box {
            OutlinedButton(onClick = { expanded = true }) {
                Text(selectedCurrency)
                Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                currencies.forEach { currency ->
                    DropdownMenuItem(
                        text = { Text(text = currency)},
                        onClick = {
                        onCurrencySelected(currency)
                        expanded = false
                    })
                }
            }
        }
    }



    @Preview(showBackground = true)
    @Composable
    fun PreviewCurrencyConverterApp() {
        CurrencyConverterApp()
    }
}
package com.example.currencyconverter

val currencies = listOf("USD", "EUR", "GBP")

val exchangeRates = mapOf(
    "USD" to mapOf("USD" to 1.0f, "EUR" to 0.88f, "GBP" to 0.75f),
    "EUR" to mapOf("USD" to 1.14f, "EUR" to 1.0f, "GBP" to 0.85f),
    "GBP" to mapOf("USD" to 1.33f, "EUR" to 1.17f, "GBP" to 1.0f)
)
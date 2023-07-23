package com.example.currencyconverterapp

data class ExchangeRatesApiData(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: String,
    val rates: Map<String, Float>
)

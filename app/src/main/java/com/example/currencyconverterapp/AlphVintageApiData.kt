package com.example.currencyconverterapp

data class AlphaVantageResponse(
    val metaData: AlphaVantageMetaData,
    val timeSeries: Map<String, AlphaVantageTimeSeries>
)

data class AlphaVantageMetaData(
    val symbol: String
)

data class AlphaVantageTimeSeries(
    val open: String,
    val close: String,
    val high: String,
    val low: String
)

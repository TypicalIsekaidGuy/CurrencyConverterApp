package com.example.currencyconverterapp

import com.google.gson.annotations.SerializedName

data class AlphaVantageResponse(
    val metaData: Map<String, AlphaVantageMetaData>,
    val timeSeries: Map<String, Map<String, AlphaVantageTimeSeries>>
)

data class AlphaVantageMetaData(
    val information: String,
    val symbol: String,
    val lastRefreshed: String,
    val outputSize: String,
    val timeZone: String
)

data class AlphaVantageTimeSeries(
    val open: String,
    val high: String,
    val low: String,
    val close: String,
    val volume: String
)


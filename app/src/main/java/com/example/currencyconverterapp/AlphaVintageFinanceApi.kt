package com.example.currencyconverterapp

import retrofit2.http.GET
import retrofit2.http.Query

interface AlphaVantageApiService {
    @GET("function=TIME_SERIES_DAILY")
    suspend fun getHistoricalData(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = API_KEY
    ): AlphaVantageResponse
}


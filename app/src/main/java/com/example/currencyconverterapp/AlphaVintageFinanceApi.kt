package com.example.currencyconverterapp

import retrofit2.http.GET
import retrofit2.http.Query

interface AlphaVantageFinanceApi{
    @GET("function=TIME_SERIES_DAILY")
    suspend fun getHistoricalData(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = RetrofitClient.ALPHA_API_KEY
    ): AlphaVantageResponse
}

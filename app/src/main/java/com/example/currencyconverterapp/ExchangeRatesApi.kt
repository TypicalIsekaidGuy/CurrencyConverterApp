package com.example.currencyconverterapp

import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesApi{
    @GET("latest")
    suspend fun getLatestExchangeRates(
        @Query("access_key") apiKey: String/*,
        @Query("base") baseCurrency: String = "USD"*/
    ): ExchangeRatesApiData
}

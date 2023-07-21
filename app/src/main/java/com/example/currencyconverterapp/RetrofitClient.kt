package com.example.currencyconverterapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.alphavantage.co/query/"
    private const val API_KEY = "YOUR_ALPHA_VANTAGE_API_KEY"

    val apiService: AlphaVantageApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(AlphaVantageApiService::class.java)
    }
}
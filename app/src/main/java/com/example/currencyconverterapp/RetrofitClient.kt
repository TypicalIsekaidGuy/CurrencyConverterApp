package com.example.currencyconverterapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//SVU1M01ZL1AZNSA2
object RetrofitClient {
    private const val ALPHA_BASE_URL = "https://www.alphavantage.co/query/"
    const val ALPHA_API_KEY = "SVU1M01ZL1AZNSA2"
    private const val ER_BASE_URL = "http://api.exchangeratesapi.io/v1/"
    const val ER_API_KEY = "dab814d8ff0930813983c46842a74427"

    val alphaApiService: AlphaVantageFinanceApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(ALPHA_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(AlphaVantageFinanceApi::class.java)
    }
    val exchangeRatesApiService: ExchangeRatesApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(ER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ExchangeRatesApi::class.java)
    }

}
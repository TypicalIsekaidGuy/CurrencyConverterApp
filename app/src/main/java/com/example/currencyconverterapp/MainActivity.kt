 package com.example.currencyconverterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController

import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import com.example.currencyconverterapp.ui.Navigation
import com.example.currencyconverterapp.ui.SearchScreen
import kotlinx.coroutines.launch

 class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Example usage in a Coroutine scope (make sure to use Dispatchers.IO or similar for network calls)
        lifecycleScope.launch {
            try {
                val symbol = "AAPL" // Replace with the stock symbol you want to retrieve data for
                val startDate = 1626096000L // Replace with the start date timestamp (e.g., in seconds)
                val endDate = 1626278400L // Replace with the end date timestamp (e.g., in seconds)

                val response = RetrofitClient.yahooFinanceApi.getHistoricalData(symbol, startDate, endDate)
                println(response)
                // Process the response
            } catch (e: Exception) {
                // Handle exceptions
            }
        }
        setContent {
            CurrencyConverterAppTheme {
                val navController = rememberNavController()
                SearchScreen(navController =  navController, "name", 10F)
                /*Navigation()*/
            }
        }
    }
}
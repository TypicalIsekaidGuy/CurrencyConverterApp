 package com.example.currencyconverterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import com.example.currencyconverterapp.ui.Navigation
import com.example.currencyconverterapp.ui.SearchScreen

 class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterAppTheme {
                val navController = rememberNavController()
                SearchScreen(navController =  navController, "name", 10F)
                /*Navigation()*/
            }
        }
    }
}
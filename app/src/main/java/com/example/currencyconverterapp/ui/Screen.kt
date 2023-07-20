package com.example.currencyconverterapp.ui

sealed class Screen(val route: String){
    object MainScreen: Screen("main_screen")
    object SearchScreen: Screen("search_screen")
    object CalculatorScreen: Screen("calculator_screen")
}

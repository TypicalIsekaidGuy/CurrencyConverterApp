package com.example.currencyconverterapp.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.currencyconverterapp.MainViewModel
import java.lang.Exception

@Composable
fun Navigation(viewModel: MainViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route ){
        composable(route = Screen.MainScreen.route){
            MainScreen(navController =  navController, viewModel.data)
        }
        composable(route = Screen.CalculatorScreen.route + "/{name}/{price}", arguments = listOf(navArgument("name") { type = NavType.StringType },navArgument("price") { type = NavType.FloatType } )){backStackEntry ->
            if(backStackEntry.arguments?.getString("name").isNullOrEmpty()||backStackEntry.arguments?.getFloat("price")==null){
                throw NullPointerException("NULL NAME||PRICE")
            }
            val name = backStackEntry.arguments?.getString("name") as String
            val price = backStackEntry.arguments?.getFloat("price") as Float
            CalculatorScreen(navController =  navController, name, price)
        }
        composable(route = Screen.SearchScreen.route){
            SearchScreen(navController =  navController)
        }
    }
}

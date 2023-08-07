package com.example.currencyconverterapp.ui

import androidx.annotation.DrawableRes

data class Currency (
    val name: String,
    @DrawableRes val image: Int,
    val price: Float,
    val trend: Boolean,
    val trendProcentage: Float,
    val anotherPrice: Float
){
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = mutableListOf<String>()

        for (i in 1..name.length) {
            matchingCombinations.add(name.substring(0, i))
        }

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }

}
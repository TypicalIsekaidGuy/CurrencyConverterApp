package com.example.currencyconverterapp.ui

import androidx.annotation.DrawableRes

data class Currency (
    val name: String,
    @DrawableRes val image: Int,
    val price: Float,
    val trend: Boolean,
    val trendProcentage: Float,
    val anotherPrice: Float
)
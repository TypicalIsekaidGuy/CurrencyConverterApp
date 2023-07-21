package com.example.currencyconverterapp.ui

data class Currency (
    val name: String,
    val price: Float,
    val trend: Boolean,
    val trendProcentage: Float,
    val anotherPrice: Float
)
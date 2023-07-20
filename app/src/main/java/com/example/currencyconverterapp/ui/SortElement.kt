package com.example.currencyconverterapp.ui

data class SortElement(
    val name: String,
    val sortBy: Sort
)
enum class Sort{
    PRICE,
    TREND,
    TREND_PERCENTAGE
}
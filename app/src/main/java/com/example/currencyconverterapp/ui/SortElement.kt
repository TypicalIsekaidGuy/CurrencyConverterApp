package com.example.currencyconverterapp.ui

data class SortElement(
    val name: String,
    val sortBy: Sort,
    val onClick: () -> Unit,
    val onClickDefault: () -> Unit
)
enum class Sort{
    NAME,
    PRICE,
    TREND,
    TREND_PERCENTAGE
}
package com.example.currencyconverterapp

import androidx.compose.runtime.MutableState

interface NumPadInterface {
    fun insertDigit(amount: MutableState<Float>, digit: Int): Boolean
    fun deleteDigit( amount: MutableState<Float>): Boolean
}
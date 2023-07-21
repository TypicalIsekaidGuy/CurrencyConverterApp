package com.example.currencyconverterapp

import androidx.compose.runtime.MutableState

interface NumPadInterface {
    fun inputNumber(int: Int,cursorPosition: Int,  changeValue: MutableState<Float>)
    fun changeValues(amount: MutableState<Float>,totalSum: MutableState<Float>)
    fun delete()
    fun putPoint()
}
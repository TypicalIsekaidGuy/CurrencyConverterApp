/*
package com.example.currencyconverterapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColorScheme(
    primary = ButtonNotPressedGray,
    onPrimary = ButtonPressedGray,
    secondary = LightBlack
)

private val LightColorPalette = lightColorScheme(
    primary = ButtonNotPressedGray,
    onPrimary = ButtonPressedGray,
    secondary = LightBlack



)

@Composable
fun CurrencyConverterAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = NormalText,
        content = content
    )
}
*/

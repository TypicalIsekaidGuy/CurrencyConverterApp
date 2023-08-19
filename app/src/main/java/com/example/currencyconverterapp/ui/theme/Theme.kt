package com.example.currencyconverterapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColorScheme(
    primary = LightBlack,
    background = LightBlack,
    onPrimary = ButtonPressedDarkGray,
    secondary = VeryDarkGray,
    onSecondary = ButtonNotPressedarkGray,
    tertiary = DarkerOrange,
    onTertiary = DarkOrange,
    surface = GreenTrend,
    onSurface = RedTrend
)

private val LightColorPalette = lightColorScheme(
    primary = BGWhite,
    background = BGWhite,
    onPrimary = ButtonPressedGray,
    secondary = VeryGray,
    onSecondary = ButtonNotPressedGray,
    tertiary = LightBlack,
    onTertiary = LighterBlack,
    surface = GreenTrend,
    onSurface = RedTrend



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

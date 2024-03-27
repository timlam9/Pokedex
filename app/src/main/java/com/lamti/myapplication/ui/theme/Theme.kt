package com.lamti.myapplication.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = Primary,
    secondary = PrimaryDark,
    tertiary = Secondary,
    background = Color.Black,
    onBackground = Color.White,
)

private val LightColorPalette = lightColorScheme(
    primary = PrimaryDark,
    secondary = PrimaryDarker,
    tertiary = Secondary,
    background = Color.White,
    onBackground = Color.Black,
)

@Composable
fun PokedexTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
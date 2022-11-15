package com.example.noties.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Green500,
    secondary = Green500,
    onSecondary = Color.Black,
    surface = darkPrimary,
    background = background,
    onBackground = background800,
    primaryVariant = purple500,
    onPrimary = Color.Black,
    onSurface = Color.White
)


private val LightColorPalette = lightColors(
    background = Color.White,
    onBackground = purple500,
    surface = Color.White,
    primary = purple200,
    primaryVariant = purple500,
    secondary = purple500,
    onPrimary = Color.White,
    onSecondary = Color.White,
    error = orangeError

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun NotiesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
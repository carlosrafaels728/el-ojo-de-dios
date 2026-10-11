package com.carlosart.elojodedios.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary = Color(0xFF7CF0FF),
    secondary = Color(0xFF68A8FF),
    tertiary = Color(0xFF004C99),
    background = Color(0xFF07111F),
    surface = Color(0xFF0D1B2A),
    onPrimary = Color(0xFF00131F),
    onSecondary = Color(0xFF00131F),
    onBackground = Color(0xFFEAF7FF),
    onSurface = Color(0xFFEAF7FF),
)

private val LightColors = lightColorScheme(
    primary = Color(0xFF0A60B7),
    secondary = Color(0xFF3EA6FF),
    tertiary = Color(0xFF0055C7),
    background = Color(0xFFEAF4FF),
    surface = Color(0xFFF6FBFF),
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFFFFFFFF),
    onBackground = Color(0xFF101828),
    onSurface = Color(0xFF101828),
)

@Composable
fun ElOjoDeDiosTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}

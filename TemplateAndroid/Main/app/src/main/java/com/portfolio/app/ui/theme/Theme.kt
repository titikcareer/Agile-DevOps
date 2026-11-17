package com.portfolio.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = SlateBlue90,
    onPrimary = Paper,
    secondary = MutedTeal,
    onSecondary = Paper,
    background = Cloud,
    onBackground = Ink,
    surface = Paper,
    onSurface = Ink,
    surfaceVariant = Mist,
    onSurfaceVariant = Charcoal,
    outline = Slate,
    error = SoftError
)

private val DarkColors = darkColorScheme(
    primary = Mist,
    onPrimary = Ink,
    secondary = MutedTeal,
    onSecondary = Ink,
    background = Ink,
    onBackground = Cloud,
    surface = SlateBlue90,
    onSurface = Cloud,
    surfaceVariant = SlateBlue60,
    onSurfaceVariant = Mist,
    outline = SlateBlue40,
    error = SoftError
)

@Composable
fun PortfolioTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

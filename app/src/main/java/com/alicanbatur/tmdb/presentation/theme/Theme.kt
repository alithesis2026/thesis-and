package com.alicanbatur.tmdb.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = TMDBPrimary,
    secondary = TMDBSecondary,
    background = TMDBBackgroundLight
)

private val DarkColors = darkColorScheme(
    primary = TMDBPrimary,
    secondary = TMDBSecondary,
    background = TMDBBackgroundDark
)

@Composable
fun TMDBMovieAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

package com.alicanbatur.tmdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.alicanbatur.tmdb.presentation.navigation.TMDBNavHost
import com.alicanbatur.tmdb.presentation.settings.AppearanceMode
import com.alicanbatur.tmdb.presentation.settings.SettingsViewModel
import com.alicanbatur.tmdb.presentation.theme.TMDBMovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val uiState by settingsViewModel.uiState.collectAsState()
            val useDarkTheme = when (uiState.appearanceMode) {
                AppearanceMode.SYSTEM -> isSystemInDarkTheme()
                AppearanceMode.LIGHT -> false
                AppearanceMode.DARK -> true
            }

            TMDBMovieAppTheme(darkTheme = useDarkTheme) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TMDBNavHost()
                }
            }
        }
    }
}

package com.alicanbatur.tmdb.presentation.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class AppearanceMode {
    SYSTEM, LIGHT, DARK
}

data class SettingsUiState(
    val appearanceMode: AppearanceMode = AppearanceMode.SYSTEM,
    val notificationsEnabled: Boolean = false
)

private object SettingsKeys {
    val APPEARANCE_MODE = stringPreferencesKey("appearance_mode")
    val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = dataStore.data
        .map { preferences ->
            SettingsUiState(
                appearanceMode = preferences[SettingsKeys.APPEARANCE_MODE]
                    ?.let { runCatching { AppearanceMode.valueOf(it) }.getOrNull() }
                    ?: AppearanceMode.SYSTEM,
                notificationsEnabled = preferences[SettingsKeys.NOTIFICATIONS_ENABLED] ?: false
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SettingsUiState())

    fun setAppearanceMode(mode: AppearanceMode) {
        viewModelScope.launch {
            dataStore.edit { it[SettingsKeys.APPEARANCE_MODE] = mode.name }
        }
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.edit { it[SettingsKeys.NOTIFICATIONS_ENABLED] = enabled }
        }
    }
}

package com.alicanbatur.tmdb.presentation.settings

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.Coil
import coil.annotation.ExperimentalCoilApi

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted -> viewModel.setNotificationsEnabled(granted) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Görünüm", style = MaterialTheme.typography.titleMedium)
        AppearanceMode.entries.forEach { mode ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = uiState.appearanceMode == mode,
                        onClick = { viewModel.setAppearanceMode(mode) }
                    )
                    .padding(vertical = 4.dp)
            ) {
                RadioButton(selected = uiState.appearanceMode == mode, onClick = { viewModel.setAppearanceMode(mode) })
                Text(text = mode.name)
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        Text(text = "Bildirimler", style = MaterialTheme.typography.titleMedium)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text(text = "Bildirimlere izin ver", modifier = Modifier.weight(1f))
            Switch(
                checked = uiState.notificationsEnabled,
                onCheckedChange = { enabled ->
                    if (enabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        viewModel.setNotificationsEnabled(enabled)
                    }
                }
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        Text(text = "Depolama", style = MaterialTheme.typography.titleMedium)
        Button(
            onClick = {
                val imageLoader = Coil.imageLoader(context)
                imageLoader.memoryCache?.clear()
                imageLoader.diskCache?.clear()
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Görsel önbelleğini temizle")
        }
    }
}

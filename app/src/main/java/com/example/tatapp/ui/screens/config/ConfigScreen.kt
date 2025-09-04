package com.example.tatapp.ui.screens.config

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tatapp.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigScreen(settingsVm: SettingsViewModel) {
    val dark by settingsVm.darkMode.collectAsState()

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Configuración") }) }
    ) { padding ->
        Row(
            modifier = Modifier.padding(padding).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Tema oscuro", modifier = Modifier.weight(1f))
            Switch(checked = dark, onCheckedChange = { settingsVm.toggleDark() })
        }
    }
}
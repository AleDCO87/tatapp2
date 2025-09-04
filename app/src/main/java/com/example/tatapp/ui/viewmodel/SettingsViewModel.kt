package com.example.tatapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tatapp.data.prefs.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = SettingsRepository(app)

    val darkMode = repo.darkMode
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun toggleDark() = viewModelScope.launch {
        repo.setDarkMode(!darkMode.value)
    }
}

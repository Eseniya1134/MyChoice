package com.mychoice.settings.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SettingsUiState(
    val username: String         = "Иванов Иван",
    val handle: String           = "ivanovia",
    val avatarUrl: String?       = null,
    val isLightTheme: Boolean    = true,
    val selectedLanguage: String = "Русский"
)

class SettingsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun toggleTheme(isLight: Boolean) {
        _uiState.update { it.copy(isLightTheme = isLight) }
    }

    fun onLanguageClick() {
        // TODO: диалог выбора языка
    }

    fun onLogout() {
        // TODO: логаут
    }
}
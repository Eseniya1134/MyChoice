package com.mychoice.settings.presentation

data class SettingsUiState(
    val username: String = "Имя пользователя",
    val handle: String = "username",
    val avatarUrl: String? = null,
    val isLightTheme: Boolean = true,
    val selectedLanguage: String = "Русский",
    val showLanguageDialog: Boolean = false,

    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val city: String = "",
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

enum class AppLanguage(val displayName: String, val code: String) {
    RUSSIAN("Русский", "ru"),
    ENGLISH("English", "en")
}
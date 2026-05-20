package com.mychoice.settings.presentation

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import android.util.Log
import kotlinx.coroutines.launch

// единый синглтон на весь процесс
private val Context.settingsDataStore: DataStore<Preferences>
        by preferencesDataStore(name = "app_settings")

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = application.settingsDataStore

    // Ключи DataStore
    companion object {
        val KEY_LIGHT_THEME = booleanPreferencesKey("is_light_theme")
        val KEY_LANGUAGE    = stringPreferencesKey("selected_language")
    }    // UI‑состояние
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    private val _restartApp = MutableSharedFlow<Unit>()
    val restartApp = _restartApp.asSharedFlow()

    init {
        // Читаем сохранённые настройки при запуске
        viewModelScope.launch {
            dataStore.data.collect { prefs ->
                val isLight  = prefs[KEY_LIGHT_THEME] ?: true
                val langCode = prefs[KEY_LANGUAGE] ?: AppLanguage.RUSSIAN.code
                val lang     = AppLanguage.entries.firstOrNull { it.code == langCode }
                    ?: AppLanguage.RUSSIAN

                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(langCode)
                )


                _uiState.update {
                    it.copy(
                        isLightTheme     = isLight,
                        selectedLanguage = lang.displayName
                    )
                }
            }
        }
    }

    // Смена темы
    fun toggleTheme(isLight: Boolean) {
        viewModelScope.launch {
            dataStore.edit { it[KEY_LIGHT_THEME] = isLight }
        }
        // uiState обновится автоматически через collect выше
    }

    // ── Диалог выбора языка ───────────────────────────────────────────────────
    fun onLanguageClick() {
        _uiState.update { it.copy(showLanguageDialog = true) }
    }

//    fun onLanguageSelected(language: AppLanguage) {
//        viewModelScope.launch {
//            dataStore.edit { it[KEY_LANGUAGE] = language.code }
//        }
//        _uiState.update {
//            it.copy(
//                selectedLanguage  = language.displayName,
//                showLanguageDialog = false
//            )
//        }
//        // Здесь можно вызвать AppCompatDelegate.setApplicationLocales(...)
//        // или перезапустить Activity — зависит от вашей реализации i18n
//    }

    fun onLanguageSelected(language: AppLanguage) {
        viewModelScope.launch {

            Log.d("LANG_DEBUG", "CLICKED: ${language.code}")

            dataStore.edit {
                it[KEY_LANGUAGE] = language.code
            }

            Log.d("LANG_DEBUG", "SAVED TO DATASTORE: ${language.code}")

            val locale = LocaleListCompat.forLanguageTags(language.code)

            Log.d("LANG_DEBUG", "APPLYING LOCALE: ${locale.toLanguageTags()}")

            AppCompatDelegate.setApplicationLocales(locale)

            Log.d(
                "LANG_DEBUG",
                "AFTER APPLY: ${AppCompatDelegate.getApplicationLocales().toLanguageTags()}"
            )

            _uiState.update {
                it.copy(
                    selectedLanguage = language.displayName,
                    showLanguageDialog = false
                )
            }

            _restartApp.emit(Unit)
        }
    }

    fun setLanguage(languageCode: String) {  // "ru" или "en"
        val appLocale = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
        // AppCompatDelegate сам сохраняет выбор и пересоздаёт Activity
    }

    fun onLanguageDialogDismiss() {
        _uiState.update { it.copy(showLanguageDialog = false) }
    }

    // ── Выход ─────────────────────────────────────────────────────────────────
    fun onLogout() {
        // TODO: очистить токены / навигировать к экрану входа
    }
}
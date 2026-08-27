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
import com.mychoice.profile.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import android.util.Log
import com.mychoice.network.TokenStorage
import kotlinx.coroutines.launch
import javax.inject.Inject

private val Context.settingsDataStore: DataStore<Preferences>
        by preferencesDataStore(name = "app_settings")

@HiltViewModel
class SettingsViewModel @Inject constructor(
    application: Application,
    private val profileRepository: ProfileRepository,
    private val tokenStorage: TokenStorage
) : AndroidViewModel(application) {

    private val dataStore = application.settingsDataStore

    companion object {
        val KEY_LIGHT_THEME = booleanPreferencesKey("is_light_theme")
        val KEY_LANGUAGE = stringPreferencesKey("selected_language")
    }

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    private val _restartApp = MutableSharedFlow<Unit>()
    val restartApp = _restartApp.asSharedFlow()

    private val _logoutEvent = MutableSharedFlow<Unit>()
    val logoutEvent = _logoutEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            dataStore.data.collect { prefs ->
                val isLight = prefs[KEY_LIGHT_THEME] ?: true
                val langCode = prefs[KEY_LANGUAGE] ?: AppLanguage.RUSSIAN.code
                val lang = AppLanguage.entries.firstOrNull { it.code == langCode }
                    ?: AppLanguage.RUSSIAN

                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(langCode)
                )

                _uiState.update {
                    it.copy(
                        isLightTheme = isLight,
                        selectedLanguage = lang.displayName
                    )
                }
            }
        }

        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val profile = profileRepository.getMyProfile()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        firstName = profile.firstName,
                        lastName = profile.lastName,
                        username = profile.username,
                        email = profile.email,
                        city = profile.city,
                        handle = profile.username,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Ошибка загрузки профиля"
                    )
                }
            }
        }
    }

    suspend fun refreshProfile() {
        try {
            _uiState.update { it.copy(isLoading = true) }
            val profile = profileRepository.getMyProfile()
            _uiState.update {
                it.copy(
                    isLoading = false,
                    firstName = profile.firstName,
                    lastName = profile.lastName,
                    username = profile.username,
                    email = profile.email,
                    city = profile.city,
                    handle = profile.username,
                    errorMessage = null
                )
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    fun toggleTheme(isLight: Boolean) {
        viewModelScope.launch {
            dataStore.edit { it[KEY_LIGHT_THEME] = isLight }
        }
    }

    fun onLanguageClick() {
        _uiState.update { it.copy(showLanguageDialog = true) }
    }

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

    fun onLanguageDialogDismiss() {
        _uiState.update { it.copy(showLanguageDialog = false) }
    }

    fun onLogout() {

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                tokenStorage.clear()
                _uiState.update { it.copy(isLoading = false) }
                _logoutEvent.emit(Unit)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Ошибка выхода"
                    )
                }
            }
        }
    }


}
package com.mychoice.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mychoice.auth.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(value: String) =
        _uiState.update { it.copy(email = value, errorMessage = null) }

    fun onPasswordChange(value: String) =
        _uiState.update { it.copy(password = value, errorMessage = null) }

    fun login() {
        val state = _uiState.value
        if (!validate(state)) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            loginUseCase(state.email.trim(), state.password)
                .onSuccess { token ->
                    // TODO: сохранить токен через TokenRepository / DataStore
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Неизвестная ошибка"
                        )
                    }
                }
        }
    }

    fun resetSuccess() = _uiState.update { it.copy(isSuccess = false) }

    private fun validate(state: LoginUiState): Boolean {
        val error = when {
            state.email.isBlank()    -> "Введите email"
            state.password.isBlank() -> "Введите пароль"
            else                     -> null
        }
        if (error != null) _uiState.update { it.copy(errorMessage = error) }
        return error == null
    }
}
package com.mychoice.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mychoice.auth.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onEmailChange(value: String) =
        _uiState.update { it.copy(email = value, errorMessage = null) }

    fun onPasswordChange(value: String) =
        _uiState.update { it.copy(password = value, errorMessage = null) }

    fun onFirstNameChange(value: String) =
        _uiState.update { it.copy(firstName = value, errorMessage = null) }

    fun onLastNameChange(value: String) =
        _uiState.update { it.copy(lastName = value, errorMessage = null) }

    fun onAgeChange(value: String) =
        _uiState.update { it.copy(ageText = value.filter(Char::isDigit), errorMessage = null) }

    fun onCityChange(value: String) =
        _uiState.update { it.copy(city = value, errorMessage = null) }

    fun onRoleChange(value: UserRole?) =
        _uiState.update { it.copy(role = value, errorMessage = null) }

    fun register() {
        val state = _uiState.value
        val age = state.ageText.toIntOrNull()
        if (!validate(state, age)) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            registerUseCase(
                email     = state.email.trim(),
                password  = state.password,
                firstName = state.firstName.trim(),
                lastName  = state.lastName.trim(),
                age       = age!!,
                city      = state.city.trim(),
                role      = state.role?.apiValue
            )
                .onSuccess {
                    // регистрация прошла — переходим на Login
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading    = false,
                            errorMessage = error.message ?: "Неизвестная ошибка"
                        )
                    }
                }
        }
    }

    fun resetSuccess() = _uiState.update { it.copy(isSuccess = false) }

    private fun validate(state: RegisterUiState, age: Int?): Boolean {
        val error = when {
            state.firstName.isBlank() -> "Введите имя"
            state.lastName.isBlank()  -> "Введите фамилию"
            state.email.isBlank()     -> "Введите email"
            state.password.length < 6 -> "Пароль — минимум 6 символов"
            age == null || age <= 0   -> "Введите корректный возраст"
            state.city.isBlank()      -> "Введите город"
            else                      -> null
        }
        if (error != null) _uiState.update { it.copy(errorMessage = error) }
        return error == null
    }
}
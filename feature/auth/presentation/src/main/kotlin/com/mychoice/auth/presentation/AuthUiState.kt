package com.mychoice.auth.presentation

// Состояние экрана входа

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)

// Состояние экрана регистрации

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val ageText: String = "",
    val city: String = "",
    val role: UserRole? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)

// Роль (presentation-модель, только для UI)

enum class UserRole(val displayName: String, val apiValue: String) {
    ABITURIENT("Абитуриент", "ABITURIENT"),
    BACHELOR("Бакалавр", "BACHELOR"),
    MASTER("Магистр", "MASTER"),
    POSTGRADUATE("Аспирант", "POSTGRADUATE"),
    TEACHER("Преподаватель", "TEACHER"),
    ADMIN("Администратор", "ADMIN")
}
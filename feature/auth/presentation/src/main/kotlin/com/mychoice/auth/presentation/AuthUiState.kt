package com.mychoice.auth.presentation
import androidx.annotation.StringRes
import com.mychoice.resources.R

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

enum class UserRole(
    @StringRes val displayNameRes: Int,
    val apiValue: String
) {
    ABITURIENT(R.string.role_abiturient, "ABITURIENT"),
    STUDENT(R.string.role_student, "STUDENT"),
    TEACHER(R.string.role_teacher, "TEACHER"),
    ADMIN(R.string.role_admin, "ADMIN")
}

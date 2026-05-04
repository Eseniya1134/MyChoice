package com.mychoice.profile.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.update


data class ProfileUiState(
    val username: String         = "Иванов Иван",
    val handle: String           = "@ivanovia",
    val avatarUrl: String?       = null,
    val email: String           = "ivanivanov@mail.ru",
    val city: String            = "Казань",
    val age: String             = "15 лет"
)

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

}
package com.mychoice.profile.presentation


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mychoice.network.TokenStorage
import com.mychoice.profile.domain.usecase.GetMyProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val username: String   = "",
    val handle: String     = "",
    val avatarUrl: String? = null,
    val email: String      = "",
    val city: String       = "",
    val age: String        = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val token = tokenStorage.token.first()
            if (token == null) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Не авторизован")
                }
                return@launch
            }

            getMyProfileUseCase()
                .onSuccess { profile ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            username  = "${profile.firstName} ${profile.lastName}",
                            handle    = profile.username,
                            email     = profile.email,
                            city      = profile.city,
                            age       = "${profile.age} лет"
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = error.message)
                    }
                }
        }
    }

}
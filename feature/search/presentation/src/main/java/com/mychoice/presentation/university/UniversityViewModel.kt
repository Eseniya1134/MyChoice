// ✅ Правильно
package com.mychoice.search.presentation.university

import com.mychoice.search.domain.usecase.GetUniversityUseCase
import com.mychoice.search.domain.usecase.GetUniversityFacultiesUseCase
import com.mychoice.search.domain.model.University
import com.mychoice.search.domain.model.FacultyShort
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UniversityUiState(
    val university: University? = null,
    val faculties: List<FacultyShort> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class UniversityViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getUniversity: GetUniversityUseCase,
    private val getUniversityFaculties: GetUniversityFacultiesUseCase
) : ViewModel() {

    private val universityId: Long = checkNotNull(savedStateHandle["universityId"])

    private val _uiState = MutableStateFlow(UniversityUiState())
    val uiState: StateFlow<UniversityUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Загружаем параллельно
            val universityResult = getUniversity(universityId)
            val facultiesResult = getUniversityFaculties(universityId)

            _uiState.update { state ->
                state.copy(
                    university = universityResult.getOrNull(),
                    faculties = facultiesResult.getOrNull() ?: emptyList(),
                    isLoading = false,
                    error = universityResult.exceptionOrNull()?.message
                )
            }
        }
    }
}
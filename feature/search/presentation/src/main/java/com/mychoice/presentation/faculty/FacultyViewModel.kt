package com.mychoice.search.presentation.faculty

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mychoice.search.domain.model.*
import com.mychoice.search.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FacultyUiState(
    val faculty: Faculty? = null,
    val programs: List<ProgramShort> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class FacultyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFaculty: GetFacultyUseCase,
    private val getFacultyPrograms: GetFacultyProgramsUseCase
) : ViewModel() {

    private val facultyId: Long = checkNotNull(savedStateHandle["facultyId"])

    private val _uiState = MutableStateFlow(FacultyUiState())
    val uiState: StateFlow<FacultyUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val facultyResult = getFaculty(facultyId)
            val programsResult = getFacultyPrograms(facultyId)
            _uiState.update {
                it.copy(
                    faculty = facultyResult.getOrNull(),
                    programs = programsResult.getOrNull() ?: emptyList(),
                    isLoading = false,
                    error = facultyResult.exceptionOrNull()?.message
                )
            }
        }
    }
}
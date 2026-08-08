package com.mychoice.search.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mychoice.search.domain.model.University
import com.mychoice.search.domain.usecase.SearchUniversitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val query: String = "",
    val universities: List<University> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUniversities: SearchUniversitiesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val queryFlow = MutableStateFlow("")

    init {
        queryFlow
            .debounce(300)
            .distinctUntilChanged()
            .onEach { query -> loadUniversities(query) }
            .launchIn(viewModelScope)
    }

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query) }
        queryFlow.value = query
    }

    private fun loadUniversities(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            searchUniversities(query)
                .onSuccess { paged ->
                    _uiState.update { it.copy(universities = paged.content, isLoading = false) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(error = e.message, isLoading = false) }
                }
        }
    }
}
package com.example.btl.ui.search
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btl.repository.Fake
import com.example.btl.domain.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val query: String = "",
    val results: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
    val errormess: String? = null
)

@HiltViewModel
class SearchViewModel @Inject constructor(private val repo: Fake) : ViewModel() {
    private val UiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = UiState
    private var searchJob: Job? = null

    fun onQueryChange(newQuery: String) {
        UiState.value = UiState.value.copy(query = newQuery)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300L)
            if (newQuery.isNotBlank()) {
                performSearch(newQuery)
            } else {
                UiState.value = UiState.value.copy(results = emptyList())
            }
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            UiState.value = UiState.value.copy(isLoading = true, errormess = null)
            val searchResults = repo.search(query)
            UiState.value = UiState.value.copy(isLoading = false, results = searchResults)
        }
    }
}
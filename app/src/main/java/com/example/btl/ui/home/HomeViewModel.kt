package com.example.btl.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btl.repository.Fake
import com.example.btl.domain.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(val recipes: List<Recipe>) : HomeUiState
}
@HiltViewModel
class HomeViewModel @Inject constructor( private val repo: Fake) : ViewModel() {
    private val UiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = UiState
    init {
        loadRecommendations()
    }

    private fun loadRecommendations() {
        viewModelScope.launch {
            UiState.value = HomeUiState.Loading
            val data = repo.getRecommendations()
            UiState.value = HomeUiState.Success(data)
        }
    }
}
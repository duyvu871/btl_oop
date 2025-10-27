package com.example.btl.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btl.data.local.TokenManager
import com.example.btl.data.models.RecommendRequest
import com.example.btl.data.remote.ApiService
import com.example.btl.domain.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val recommendations: List<Recipe> = emptyList(),
    val errorMessage: String? = null
)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        val initialQuery: String = try {
            java.net.URLDecoder.decode(savedStateHandle.get<String>("q") ?: "", "UTF-8")
        } catch (e: Exception) {
            ""
        }
        if (initialQuery.isNotBlank()) {
            fetchRecommendations(initialQuery)
        }
    }

    fun fetchRecommendations(query: String) {
        if (query.isBlank()) {
            _uiState.value = HomeUiState(recommendations = emptyList())
            return
        }
        viewModelScope.launch {
            _uiState.value = HomeUiState(isLoading = true)
            try {
                val token = "Bearer ${tokenManager.getAccessToken() ?: ""}"
                val request = RecommendRequest(query = query)
                val response = apiService.recommendRecipes(token, request)
                _uiState.value = HomeUiState(recommendations = response.recipes)
            } catch (e: Exception) {
                val errorMsg = if (e is retrofit2.HttpException && e.code() == 422) {
                    "Lỗi 422"
                } else {
                    e.message ?: "Lỗi"
                }
                _uiState.value = HomeUiState(errorMessage = errorMsg)
            }
        }
    }
}
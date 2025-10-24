package com.example.btl.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btl.data.local.TokenManager
import com.example.btl.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CongthucView @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val recipeId: String = savedStateHandle.get<String>("id")!!
    private val UiState = MutableStateFlow<CongthucUiState>(CongthucUiState.Loading)
    val uiState: StateFlow<CongthucUiState> = UiState
    init {
        UiState.value = CongthucUiState.Error("")
    }
    private fun loadRecipeDetails() {
    }
}
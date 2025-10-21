package com.example.btl.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btl.repository.Fake
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CongthucView @Inject constructor(
    private val repo: Fake,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val recipeId: String = savedStateHandle.get<String>("id")!!
    private val UiState = MutableStateFlow<CongthucUiState>(CongthucUiState.Loading)
    val uiState: StateFlow<CongthucUiState> = UiState
    init {
        loadRecipeDetails()
    }
    private fun loadRecipeDetails() {
        viewModelScope.launch {
            UiState.value = CongthucUiState.Loading
            val result = repo.getRecipeById(recipeId)
            if (result != null) {
                UiState.value = CongthucUiState.Success(result)
            }
            else {
                UiState.value = CongthucUiState.Error("Không tìm thấy công thức.")
            }
        }
    }
}
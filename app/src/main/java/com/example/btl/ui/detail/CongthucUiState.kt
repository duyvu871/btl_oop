package com.example.btl.ui.detail

import com.example.btl.domain.model.Recipe

sealed interface CongthucUiState {
    object Loading : CongthucUiState
    data class Success(val recipe: Recipe) : CongthucUiState
    data class Error(val mess: String) : CongthucUiState
}
package com.example.btl.ui.auth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btl.data.models.VerifyEmailRequest
import com.example.btl.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyEmailViewModel @Inject constructor(
    private val apiService: ApiService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val email: String = savedStateHandle.get<String>("email") ?: ""
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()
    fun verifyEmail(code: String) {
        if (email.isBlank()) {
            _uiState.value = AuthUiState(errormess = "Email không hợp lệ")
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            try {
                val request = VerifyEmailRequest(email = email, code = code)
                val response = apiService.verifyEmail(request)
                if (response.success) {
                    _uiState.value = AuthUiState(successmess = response.message)
                } else {
                    _uiState.value = AuthUiState(errormess = response.message)
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState(errormess = e.message ?:"Xác thực thất bại")
            }
        }
    }
    fun resetState() {
        _uiState.value = AuthUiState()
    }
}
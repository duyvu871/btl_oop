package com.example.btl.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btl.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val successmess: String? = null,
    val errormess: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRe: AuthRepository) : ViewModel() {
    private val UiState = MutableStateFlow(AuthUiState())
    val uiState = UiState.asStateFlow()


    fun login(email: String, pass: String) {
        viewModelScope.launch {
            UiState.value = AuthUiState(isLoading = true)
            try {
                authRe.loginUser(email, pass)
                UiState.value = AuthUiState(successmess = "Đăng nhập thành công")
            } catch (e: Exception) {
                UiState.value = AuthUiState(errormess = e.message)
            }
        }
    }
    fun register(email: String, pass: String) {
        viewModelScope.launch {
            UiState.value = AuthUiState(isLoading = true)
            try {

                authRe.registerUser(email, pass)
                UiState.value = AuthUiState(successmess = "Đăng ký thành công")
            } catch (e: Exception) {
                UiState.value = AuthUiState(errormess = e.message)
            }
        }
    }
    fun resetState() {
        UiState.value = AuthUiState()
    }
}
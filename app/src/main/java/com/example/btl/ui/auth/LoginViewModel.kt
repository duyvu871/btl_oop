package com.example.btl.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.btl.repository.AuthRepository

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    fun login(username: String,password: String,onSuccess: () -> Unit,onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                authRepository.loginUser(username,password)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Đăng nhập thất bại")
            }
        }
    }
}
package com.example.btl.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btl.data.local.TokenManager
import com.example.btl.data.remote.ApiService
import com.example.btl.data.models.UserRead
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) : ViewModel() {
    private val _user = MutableStateFlow<UserRead?>(null)
    val user = _user.asStateFlow()
    fun fetchUserProfile() {
        viewModelScope.launch {
            try {
                val token = "Bearer ${tokenManager.getAccessToken() ?: ""}"
                val userData = apiService.getMe(token)
                _user.value = userData
            } catch (e: Exception) {
                _user.value = null
            }
        }
    }
}
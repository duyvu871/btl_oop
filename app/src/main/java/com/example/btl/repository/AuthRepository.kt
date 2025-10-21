package com.example.btl.repository

import com.example.btl.network.Auth
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor() {

    suspend fun loginUser(email: String, pass: String): Auth {
        delay(1000)

        if (email.isNotBlank() && pass == "123456") {
            return Auth(token = "", userId = "123")
        } else {
            throw Exception("Không hợp lệ")
        }
    }
    suspend fun registerUser(username: String, email: String, pass: String): Auth {
        delay(1000)

        if (email.equals("test@example.com", ignoreCase = true)) {
            throw Exception(" ")
        } else {
            return Auth(token = "", userId = "456")
        }
    }
}
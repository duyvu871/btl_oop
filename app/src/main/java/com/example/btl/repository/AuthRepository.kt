package com.example.btl.repository

import com.example.btl.data.local.TokenManager
import com.example.btl.data.models.LoginRequest
import com.example.btl.data.models.UserCreate
import com.example.btl.data.remote.ApiService
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

data class ErrorDetail(
    @SerializedName("msg") val msg: String
)
data class ErrorResponse(
    @SerializedName("detail") val detail: Any
)

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager,
    private val gson: Gson
) {
    suspend fun loginUser(email: String, pass: String) {
        try {
            val request = LoginRequest(email = email, password = pass)
            val response = apiService.login(request)
            tokenManager.saveAccessToken(response.accessToken)
            tokenManager.saveRefreshToken(response.refreshToken)
        } catch (e: Exception) {
            throw Exception("Đăng nhập thất bại: ${parseErrorMessage(e)}")
        }
    }
    suspend fun registerUser(email: String, pass: String) {
        try {
            val request = UserCreate(email = email, password = pass)
            apiService.register(request)
        } catch (e: Exception) {
            throw Exception("Đăng ký thất bại: ${parseErrorMessage(e)}")
        }
    }

    private fun parseErrorMessage(e: Exception): String {
        return when (e) {
            is HttpException -> {
                try {
                    val errorBody = e.response()?.errorBody()?.string() ?: "lỗi"
                    try {
                        val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                        if (errorResponse.detail is List<*>) {
                            val detailList = gson.fromJson<List<ErrorDetail>>(
                                gson.toJson(errorResponse.detail),
                                object : TypeToken<List<ErrorDetail>>() {}.type
                            )
                            detailList.firstOrNull()?.msg ?: "Lỗi từ máy chủ"
                        }
                        else if (errorResponse.detail is String) {
                            errorResponse.detail.toString()
                        }
                        else {
                            "lỗi"
                        }
                    } catch (jsonError: Exception) {
                        if (errorBody.length < 100) errorBody else "Lỗi ${e.code()}: ${e.message()}"
                    }
                } catch (io: IOException) {
                    "Lỗi đọc phản hồi ${e.message()}"
                }
            }
            is IOException -> {
                "Lỗi kết nối mạng"
            }
            else -> {
                e.message ?: "Lỗi ??"
            }
        }
    }
}
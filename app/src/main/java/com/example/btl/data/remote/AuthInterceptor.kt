package com.example.btl.data.remote

import android.util.Log
import com.example.btl.data.local.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenManager.getAccessToken()
        Log.d("AuthInterceptor", "Token used: $token")
        if (token == null) {
            Log.w("AuthInterceptor", "Token is null, proceeding without Authorization header.")
            return chain.proceed(chain.request())
        }
        val request = chain.request().newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}
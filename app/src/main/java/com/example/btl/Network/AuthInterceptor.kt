package com.example.btl.Network

import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.atomic.AtomicReference

class AuthInterceptor(
    private val tokenProvider: TokenProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val token = tokenProvider.getAccessToken()
        val requestWithAuth = if (token != null) {
            original.newBuilder().addHeader("Authorization", "Bearer $token").build()
        } else original

        val response = chain.proceed(requestWithAuth)
        if (response.code == 401) {
            synchronized(tokenProvider) {
                val refreshed = tokenProvider.refreshToken()
                if (refreshed) {
                    response.close()
                    val newToken = tokenProvider.getAccessToken()
                    val retryRequest = original.newBuilder().addHeader("Authorization", "Bearer $newToken").build()
                    return chain.proceed(retryRequest)
                }
            }
        }
        return response
    }
}

interface TokenProvider {
    fun getAccessToken(): String?
    fun refreshToken(): Boolean
}

class InMemoryTokenProvider : TokenProvider {
    private val access = AtomicReference<String?>("")
    private val refresh = AtomicReference<String?>("")
    override fun getAccessToken(): String? = access.get()
    override fun refreshToken(): Boolean {
        access.set("fake-access-token-refreshed")
        return true
    }
}
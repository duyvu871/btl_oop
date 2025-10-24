package com.example.btl.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception
import kotlin.math.pow
import javax.inject.Inject

class RetryInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var response: Response? = null
        var tryCount = 0
        val maxRetries = 3
        val initialDelayMs = 1000L

        while (response == null && tryCount < maxRetries) {
            try {
                response = chain.proceed(request)
                if (response.isSuccessful) return response
            } catch (e: Exception) {
                if (tryCount == maxRetries - 1) throw e
                val delay = (initialDelayMs * 2.0.pow(tryCount.toDouble())).toLong()
                Thread.sleep(delay.coerceAtMost(5000))
            }
            tryCount++
        }
        return response ?: throw Exception("Failed after $maxRetries retries")
    }
}
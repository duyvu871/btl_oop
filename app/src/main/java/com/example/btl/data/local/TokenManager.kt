package com.example.btl.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private val prefs = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    fun saveAccessToken(accessToken: String) {
        prefs.edit().apply {
            putString("access_token", accessToken)
            apply()
        }
    }
    fun getAccessToken(): String? = prefs.getString("access_token", null)
    fun saveRefreshToken(refreshToken: String?) {
        prefs.edit().apply {
            if (refreshToken != null) {
                putString("refresh_token", refreshToken)
            } else {
                remove("refresh_token")
            }
            apply()
        }
    }
    fun clearTokens() {
        prefs.edit().clear().apply()
    }
}
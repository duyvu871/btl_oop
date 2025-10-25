
package com.example.btl.data.models

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("user")
    val user: UserRead
)
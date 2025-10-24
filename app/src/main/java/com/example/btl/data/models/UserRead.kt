
package com.example.btl.data.models

import com.google.gson.annotations.SerializedName


data class UserRead(
    @SerializedName("id")
    val id: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("preferences")
    val preferences: List<String>,
    @SerializedName("created_at")
    val createdAt: String
)
package com.example.btl.data.models

import com.google.gson.annotations.SerializedName

data class EmailResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("remaining_attempts")
    val remainingAttempts: Int
)
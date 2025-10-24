package com.example.btl.data.models


data class VerifyEmailRequest(
    val email: String,
    val code: String
)
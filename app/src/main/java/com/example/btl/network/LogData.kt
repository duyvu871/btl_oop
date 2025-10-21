package com.example.btl.network

data class Login(
    val email: String,
    val pass: String
)

data class Register(
    val name: String,
    val email: String,
    val pass: String
)
data class Auth(
    val token: String,
    val userId: String
)
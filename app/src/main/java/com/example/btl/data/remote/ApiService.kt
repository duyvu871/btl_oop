package com.example.btl.data.remote

import com.example.btl.data.models.*
import com.example.btl.domain.model.Recipe
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("/api/v1/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Token
    @POST("/api/v1/auth/register")
    suspend fun register(
        @Body request: UserCreate
    ): UserRead

    @POST("/api/v1/auth/verify-email")
    suspend fun verifyEmail(
        @Body request: VerifyEmailRequest
    ): Response<Unit>

    @GET("/api/v1/auth/me")
    suspend fun getMe(
        @Header("Authorization") token: String
    ): UserRead

    @GET("recommendations")
    suspend fun getRecommendations(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int = 20,
        @Query("lat") lat: Double?,
        @Query("lng") lng: Double?,
        @Query("budget") budget: Double?,
        @Query("mood") mood: String?,
        @Query("ingredients") ingredients: List<String>?
    ): PaginatedResponse<Recipe>

    @GET("dishes/search")
    suspend fun searchDishes(
        @Header("Authorization") token: String,
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("size") size: Int = 20
    ): PaginatedResponse<Recipe>

    @GET("recipes/{id}")
    suspend fun getRecipeById(
        @Header("Authorization") token: String,
        @Path("id") recipeId: String
    ): Recipe?
}
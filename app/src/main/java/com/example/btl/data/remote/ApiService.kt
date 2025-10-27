package com.example.btl.data.remote

import com.example.btl.data.models.*
import com.example.btl.domain.model.Recipe
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
    ): EmailResponse
    @GET("/api/v1/auth/me")
    suspend fun getMe(
        @Header("Authorization") token: String
    ): UserRead
    @POST("/api/v1/ai/recommend")
    suspend fun recommendRecipes(
        @Header("Authorization") token: String,
        @Body request: RecommendRequest
    ): RecommendResponse
    @GET("/api/v1/recipe/search")
    suspend fun searchDishes(
        @Header("Authorization") token: String,
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("size") size: Int = 20
    ): PaginatedSearch<Recipe>
    @GET("/api/v1/recipe/{id}")
    suspend fun getRecipeById(
        @Header("Authorization") token: String,
        @Path("id") recipeId: String
    ): Recipe?
}
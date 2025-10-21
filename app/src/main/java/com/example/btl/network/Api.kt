package com.example.btl.network
import com.example.btl.domain.model.Recipe
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.btl.network.Login
import com.example.btl.network.Register
import com.example.btl.network.Auth
interface Api {

    @GET("recommendations")
    suspend fun getRecommendations(): List<Recipe>

    @GET("search")
    suspend fun searchRecipes(@Query("q") query: String): List<Recipe>

    @GET("recipes/{id}")
    suspend fun getRecipeById(@Path("id") recipeId: String): Recipe?

    @POST("auth/login")
    suspend fun login(@Body request: Login): Auth

    @POST("auth/register")
    suspend fun register(@Body request: Register): Auth
}
package com.example.btl.data.models

import com.example.btl.domain.model.Recipe
import com.google.gson.annotations.SerializedName

data class RecommendResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("recipes")
    val recipes: List<Recipe>,
    @SerializedName("total")
    val total: Int
)
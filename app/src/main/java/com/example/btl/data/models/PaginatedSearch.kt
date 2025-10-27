package com.example.btl.data.models

import com.google.gson.annotations.SerializedName

class PaginatedSearch<T>(
    @SerializedName("recipes")
    val recipes: List<T>,

    @SerializedName("total")
    val total: Int,

    @SerializedName("page")
    val page: Int,

    @SerializedName("size")
    val size: Int
)
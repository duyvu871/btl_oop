package com.example.btl.data.models

import com.google.gson.annotations.SerializedName


data class PaginatedResponse<T>(
    @SerializedName("results")
    val results: List<T>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)
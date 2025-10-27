package com.example.btl.domain.model

import com.google.gson.annotations.SerializedName

data class Nguyenlieu(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("quantity")
    val quantity: String,
    @SerializedName("unit")
    val unit: String
)
package com.example.btl.domain.model

import com.google.gson.annotations.SerializedName

data class TutorialStep(
    @SerializedName("id")
    val id: String,
    @SerializedName("index")
    val index: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("box_gallery")
    val boxGallery: List<String>?
)
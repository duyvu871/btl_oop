package com.example.btl.domain.model

import com.google.gson.annotations.SerializedName
import com.example.btl.domain.model.Nguyenlieu
import com.example.btl.domain.model.TutorialStep

data class Recipe(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("tutorial")
    val tutorial: String?,
    @SerializedName("quantitative")
    val quantitative: String?,
    @SerializedName("ingredients")
    val ingredients: List<Nguyenlieu>?,

    @SerializedName("link")
    val link: String?,
    @SerializedName("tutorial_steps")
    val tutorialSteps: List<TutorialStep>?
) {
    val imageUrl: String?
        get() = thumbnail
}
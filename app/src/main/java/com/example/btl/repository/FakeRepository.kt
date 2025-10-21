package com.example.btl.repository

import com.example.btl.domain.model.Recipe
import kotlinx.coroutines.delay

class Fake {
    private val recipes = listOf(
        Recipe("1", "mon abc", "https://www.themealdb.com/images/media/meals/0206h11699763370.jpg",),
        Recipe("2", "mon def", "https://www.themealdb.com/images/media/meals/zqtyb01683207564.jpg",),
        Recipe("3", "mon ijk", "https://www.themealdb.com/images/media/meals/hqaebe1683209870.jpg",),
        Recipe("4", "mon xyz", "https://www.themealdb.com/images/media/meals/vvpprx1487325699.jpg",),
        )

    suspend fun getRecommendations(): List<Recipe> {
        delay(300)
        return recipes
    }

    suspend fun search(query: String): List<Recipe> {
        delay(300)
        if (query.isBlank()) return emptyList()
        return recipes.filter { it.title.contains(query, true) }
    }

    suspend fun getRecipeById(id: String): Recipe? {
        delay(300)
        return recipes.firstOrNull { it.id == id }
    }
}
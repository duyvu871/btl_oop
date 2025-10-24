package com.example.btl.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.btl.data.local.TokenManager
import com.example.btl.data.paging.RecommendationParams
import com.example.btl.data.remote.ApiService
import com.example.btl.domain.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) : ViewModel() {
    private val _recommendationParams = MutableStateFlow(RecommendationParams())

    fun updateRecommendationParams(params: RecommendationParams) {
        _recommendationParams.value = params
    }

    init {
        viewModelScope.launch {
            val params = RecommendationParams(
                lat = 10.7769,
                lng = 106.7009,
                budget = 150000.0,
                mood = "th",
                ingredients = null
            )
            updateRecommendationParams(params)
        }
    }
    @OptIn(ExperimentalCoroutinesApi::class)

    val recommendations: Flow<PagingData<Recipe>> = flowOf(PagingData.empty())

}
package com.example.btl.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.btl.data.local.TokenManager
import com.example.btl.data.remote.ApiService
import com.example.btl.domain.model.Recipe
import retrofit2.HttpException
import java.io.IOException

data class RecommendationParams(
    val lat: Double? = null,
    val lng: Double? = null,
    val budget: Double? = null,
    val mood: String? = null,
    val ingredients: List<String>? = null
)

class HomePagingSource(
    private val apiService: ApiService,
    private val tokenManager: TokenManager,
    private val params: RecommendationParams
) : PagingSource<Int, Recipe>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        val page = params.key ?: 1
        val token = "Bearer ${tokenManager.getAccessToken() ?: ""}"

        return try {
            val response = apiService.getRecommendations(
                token = token,
                page = page,
                size = params.loadSize,
                lat = this.params.lat,
                lng = this.params.lng,
                budget = this.params.budget,
                mood = this.params.mood,
                ingredients = this.params.ingredients
            )
            val recipes = response.results

            LoadResult.Page(
                data = recipes,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= response.totalPages) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
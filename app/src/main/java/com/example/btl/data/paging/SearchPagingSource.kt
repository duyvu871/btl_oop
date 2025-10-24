package com.example.btl.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.btl.data.local.TokenManager
import com.example.btl.data.remote.ApiService
import com.example.btl.domain.model.Recipe
import retrofit2.HttpException
import java.io.IOException

class SearchPagingSource(
    private val apiService: ApiService,
    private val tokenManager: TokenManager,
    private val query: String,
) : PagingSource<Int, Recipe>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        if (query.isBlank()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }
        val page = params.key ?: 1
        val token = "Bearer ${tokenManager.getAccessToken() ?: ""}"
        return try {
            val response = apiService.searchDishes(
                token = token,
                q = query,
                page = page,
                size = params.loadSize
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
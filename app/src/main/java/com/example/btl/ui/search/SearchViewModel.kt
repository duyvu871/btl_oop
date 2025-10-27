package com.example.btl.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.btl.data.local.TokenManager
import com.example.btl.data.paging.SearchPagingSource
import com.example.btl.data.remote.ApiService
import com.example.btl.domain.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val initialQuery: String = try {
        java.net.URLDecoder.decode(savedStateHandle.get<String>("q") ?: "", "UTF-8")
    } catch (e: Exception) {
        ""
    }
    private val _query = MutableStateFlow(initialQuery)
    val query: StateFlow<String> = _query
    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResults: Flow<PagingData<Recipe>> = _query
        .debounce(300L)
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf(PagingData.empty())
            } else {
                Pager(
                    config = PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = {
                        SearchPagingSource(apiService, tokenManager, query)
                    }
                ).flow
            }
        }
        .cachedIn(viewModelScope)
}
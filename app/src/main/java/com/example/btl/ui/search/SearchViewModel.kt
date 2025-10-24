package com.example.btl.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.btl.data.local.TokenManager
import com.example.btl.data.remote.ApiService
import com.example.btl.domain.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    val searchResults: Flow<PagingData<Recipe>> = flowOf(PagingData.empty())

}
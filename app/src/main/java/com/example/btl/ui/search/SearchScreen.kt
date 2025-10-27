package com.example.btl.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.* import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.btl.domain.model.Recipe

@Composable
fun SearchScreen(
    onResultClick: (String) -> Unit,
    onNavigateUp: () -> Unit,
    vm: SearchViewModel = hiltViewModel()
) {
    val query by vm.query.collectAsStateWithLifecycle()
    val results = vm.searchResults.collectAsLazyPagingItems()

    SearchScreenContent(
        query = query,
        results = results,
        onQueryChange = vm::onQueryChange,
        onResultClick = onResultClick,
        onNavigateUp = onNavigateUp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenContent(
    query: String,
    results: LazyPagingItems<Recipe>,
    onQueryChange: (String) -> Unit,
    onResultClick: (String) -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tìm kiếm món ăn") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Tìm kiếm") },
                singleLine = true
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when (results.loadState.refresh) {
                    is LoadState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    is LoadState.Error -> {
                        val e = (results.loadState.refresh as LoadState.Error).error
                        Text(
                            text = "Lỗi: ${e.localizedMessage}",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    else -> {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(
                                count = results.itemCount,
                                key = results.itemKey { it.id }
                            ) { index ->
                                val recipe = results[index]
                                if (recipe != null) {
                                    Text(
                                        text = recipe.title,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { onResultClick(recipe.id) }
                                            .padding(vertical = 12.dp)
                                    )
                                    HorizontalDivider()
                                }
                            }
                            if (results.loadState.append is LoadState.Loading) {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
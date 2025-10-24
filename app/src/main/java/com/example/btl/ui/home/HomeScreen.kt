package com.example.btl.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.btl.domain.model.Recipe
import com.example.btl.ui.components.RecipeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onRecipeClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    vm: HomeViewModel = hiltViewModel()
) {
    val recipes = vm.recommendations.collectAsLazyPagingItems()
    Scaffold(topBar = { TopAppBar(title = { Text("Nhập sở thích của bạn") }) }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (recipes.loadState.refresh) {
                is LoadState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is LoadState.Error -> {
                    val e = (recipes.loadState.refresh as LoadState.Error).error
                    Text(
                        text = "Lỗi: ${e.localizedMessage}",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    HomeScreenContent(
                        recipes = recipes,
                        onRecipeClick = onRecipeClick,
                        onSearchClick = onSearchClick
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    recipes: LazyPagingItems<Recipe>,
    onRecipeClick: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSearchClick() },
            placeholder = { Text("Tìm kiếm món ăn") }
            )
        }
        item {
            Text("Top Recommendations", style = MaterialTheme.typography.titleLarge)
        }
        items(
            count = recipes.itemCount,
            key = recipes.itemKey { it.id }
        ) { index ->
            val recipe = recipes[index]
            if (recipe != null) {
                RecipeCard(recipe = recipe, onClick = { onRecipeClick(recipe.id) })
            }
        }
        if (recipes.loadState.append is LoadState.Loading) {
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
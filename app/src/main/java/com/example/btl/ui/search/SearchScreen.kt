package com.example.btl.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.btl.domain.model.Recipe
import com.example.btl.ui.theme.BTLTheme

@Composable
fun SearchScreen(
    onResultClick: (String) -> Unit,
    onNavigateUp: () -> Unit,
    vm: SearchViewModel = hiltViewModel()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    SearchScreenContent(
        uiState = uiState,
        onQueryChange = vm::onQueryChange,
        onResultClick = onResultClick,
        onNavigateUp = onNavigateUp
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenContent(
    uiState: SearchUiState,
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
                        Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.query,
                onValueChange = onQueryChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Tìm kiếm...") },
                singleLine = true
            )
            Spacer(Modifier.height(16.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (uiState.errorMessage != null) {
                    Text(uiState.errorMessage, modifier = Modifier.align(Alignment.Center))
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(items = uiState.results, key = { it.id }) { recipe ->
                            Text(
                                text = recipe.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onResultClick(recipe.id) }
                                    .padding(vertical = 12.dp)
                            )
                            Divider()
                        }
                    }
                }
            }
        }
    }
}


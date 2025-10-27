package com.example.btl.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.btl.R
import com.example.btl.domain.model.Recipe
import com.example.btl.domain.model.Nguyenlieu
import com.example.btl.domain.model.TutorialStep

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CongthucScreen(
    onNavigateUp: () -> Unit,
    vm: CongthucView = hiltViewModel()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chi tiết công thức") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val state = uiState) {
                is CongthucUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is CongthucUiState.Error -> {
                    Text(
                        text = state.mess,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is CongthucUiState.Success -> {
                    RecipeDetailsContent(recipe = state.recipe)
                }
            }
        }
    }
}

@Composable
fun RecipeDetailsContent(recipe: Recipe) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(recipe.imageUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.placeholder_image),
            error = painterResource(R.drawable.placeholder_image),
            contentDescription = recipe.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )
        Text(recipe.title, style = MaterialTheme.typography.headlineMedium)

        recipe.ingredients?.let { ingredients ->
            if (ingredients.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text("Nguyên liệu:", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(4.dp))
                Column {
                    ingredients.forEach { ingredient ->
                        Text(
                            text = "• ${ingredient.name} (${ingredient.quantity} ${ingredient.unit})",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }

        recipe.tutorialSteps?.let { steps ->
            if (steps.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text("Các bước thực hiện:", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(4.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    steps.sortedBy { it.index }.forEach { step ->
                        Text(
                            text = "${step.index}. ${step.title}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = step.content,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}
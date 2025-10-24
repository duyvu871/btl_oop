package com.example.btl.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun VerifyEmailScreen(
    onVerifySuccess: () -> Unit,
    viewModel: VerifyEmailViewModel = hiltViewModel()
) {
    var code by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(uiState.successmess) {
        if (uiState.successmess != null) {
            onVerifySuccess()
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetState()
        }
    }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Xác thực Email", style = MaterialTheme.typography.headlineMedium)
            Text(
                text = "mã xác thực đến:\n${viewModel.email}",
                textAlign = TextAlign.Center
            )

            OutlinedTextField(
                value = code,
                onValueChange = { code = it },
                label = { Text("Nhập mã") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { viewModel.verifyEmail(code) },
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Xác thực")
            }

            uiState.errormess?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
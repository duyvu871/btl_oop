package com.example.btl.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Profile(onFavoritesClick: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onFavoritesClick) {
            Text("Danh sách yêu thích")
        }
    }
}
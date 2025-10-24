package com.example.btl.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

@Composable
fun Profile(
    onFavoritesClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Button(
                onClick = onFavoritesClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Danh sách yêu thích")
            }
        }
        Button(
            onClick = onLogoutClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Đăng xuất")
        }
    }
}

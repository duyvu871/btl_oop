package com.example.btl.ui.cookathome

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Nautainha() {

    var ingredientsInput by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(12.dp)) {
        Text("Nhập các nguyên liệu bạn có")
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = ingredientsInput,
            onValueChange = { ingredientsInput = it },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5
        )
        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {
                val submittedIngredients = ingredientsInput.trim()
                if (submittedIngredients.isNotEmpty()) {
                    println("Các nguyên liệu đã nhập: $submittedIngredients")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nhận gợi ý")
        }
    }
}
package com.example.btl.ui.cookathome

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Nautainha() {
    val ingredients = listOf("Trứng", "Thịt", "Cà chua", "Khoai Tây","Tôm","Cá")
    val selected = remember { mutableStateMapOf<String, Boolean>().apply { ingredients.forEach { put(it, false) } } }
    Column(modifier = Modifier.padding(12.dp)) {
        Text("Chọn nguyên liệu bạn có")
        Spacer(Modifier.height(8.dp))
        ingredients.forEach { ing ->
            Row(modifier = Modifier.fillMaxWidth().toggleable(
                value = selected[ing] == true,
                onValueChange = { selected[ing] = !(selected[ing] == true) }
            ).padding(8.dp)) {
                Checkbox(checked = selected[ing] == true, onCheckedChange = null)
                Spacer(Modifier.width(8.dp))
                Text(ing)
            }
        }
        Spacer(Modifier.height(12.dp))
        Button(onClick = {  }, modifier = Modifier.fillMaxWidth()) {
            Text("Nhận gợi ý")
        }
    }
}
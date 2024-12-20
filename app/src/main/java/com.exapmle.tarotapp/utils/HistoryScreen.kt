package com.example.tarotapp.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tarotapp.utils.HistoryManager

@Composable
fun HistoryScreen() {
    val context = LocalContext.current
    val history = remember { HistoryManager.loadHistory(context) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Сохранённые расклады",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(history) { spread ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(16.dp)
                ) {
                    Text("Дата: ${spread.date}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 8.dp))
                    Text("Карты:", fontSize = 14.sp, modifier = Modifier.padding(bottom = 8.dp))
                    spread.cards.forEach { cardName ->
                        Text("• $cardName", fontSize = 14.sp, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }
    }
}
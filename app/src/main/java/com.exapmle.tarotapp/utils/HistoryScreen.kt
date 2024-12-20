package com.example.tarotapp.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tarotapp.utils.HistoryManager
import com.example.tarotapp.utils.TarotSpread
import androidx.compose.ui.platform.LocalContext

@Composable
fun HistoryScreen() {
    val history = HistoryManager.loadHistory(context = LocalContext.current)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "История раскладов",
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(history) { spread: TarotSpread ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Дата расклада: ${spread.date}", fontSize = 14.sp)
                    Text("Карты: ${spread.cards.joinToString(", ")}", fontSize = 14.sp)
                }
            }
        }
    }
}
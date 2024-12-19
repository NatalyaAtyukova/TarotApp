package com.example.tarotapp.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tarotapp.tarotCards

@Composable
fun MultiCardScreen(numCards: Int) {
    val context = LocalContext.current
    val selectedCards = remember { tarotCards.shuffled().take(numCards) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top // Располагаем элементы сверху вниз
    ) {
        Text(
            text = "Ваш расклад Таро:",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Вертикальная прокрутка карт
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(selectedCards) { card ->
                val imageBitmap = loadImageFromAssets(context, card.first)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    imageBitmap?.let {
                        Image(
                            bitmap = it,
                            contentDescription = "Tarot Card",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(end = 16.dp)
                        )
                    }
                    Text(
                        text = card.second,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}
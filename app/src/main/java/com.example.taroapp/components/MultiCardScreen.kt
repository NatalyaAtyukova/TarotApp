package com.example.tarotapp.components

import androidx.compose.foundation.lazy.LazyRow
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
        verticalArrangement = Arrangement.Center
    ) {
        Text("Ваш расклад Таро:", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

        // Горизонтальная прокрутка карт
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(selectedCards) { card ->
                val imageBitmap = loadImageFromAssets(context, card.first)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    imageBitmap?.let {
                        Image(
                            bitmap = it,
                            contentDescription = "Tarot Card",
                            modifier = Modifier.size(120.dp)
                        )
                    }
                    Text(card.second, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
                }
            }
        }
    }
}
package com.example.tarotapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import com.example.tarotapp.TarotCard
import com.example.tarotapp.tarotCards
import com.example.tarotapp.utils.HistoryManager.saveTarotSpread
import java.text.SimpleDateFormat
import java.util.*
import com.example.tarotapp.components.loadImageFromAssets
import androidx.compose.ui.platform.LocalContext

@Composable
fun MultiCardScreen(numCards: Int) {
    val context = LocalContext.current
    val selectedCards = remember { tarotCards.shuffled().take(numCards) }
    var isSaved by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(selectedCards) { card ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Загрузка изображения из папки assets
                    val imageBitmap: ImageBitmap? = loadImageFromAssets(context, card.imagePath)

                    imageBitmap?.let {
                        Image(
                            bitmap = it,
                            contentDescription = card.name,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(end = 16.dp)
                        )
                    }

                    Column {
                        Text(card.name, fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                        Text(card.description, fontSize = 14.sp)
                    }
                }
            }
        }

        Button(
            onClick = {
                val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                saveTarotSpread(context, selectedCards, currentDate)
                isSaved = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Сохранить расклад")
        }

        if (isSaved) {
            Text(
                "Расклад сохранен!",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
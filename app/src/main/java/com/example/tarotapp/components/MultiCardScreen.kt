package com.example.tarotapp.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tarotapp.TarotCard
import com.example.tarotapp.tarotCards
import com.example.tarotapp.utils.HistoryManager
import com.example.tarotapp.R
import java.text.SimpleDateFormat
import java.util.*
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import com.google.accompanist.flowlayout.FlowRow
import androidx.compose.foundation.background

@Composable
private fun KeywordChip(keyword: String) {
    Surface(
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            keyword,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun KeywordsList(keywords: List<String>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(bottom = 16.dp)
            .height(120.dp)
    ) {
        items(keywords.size) { index ->
            KeywordChip(keywords[index])
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiCardScreen(numCards: Int, isSubscribed: Boolean, onNavigateBack: () -> Unit = {}) {
    val context = LocalContext.current
    
    // Инициализируем карты напрямую, без использования remember или rememberSaveable
    // Это должно предотвратить проблемы с сохранением состояния
    val cardsList = remember { mutableStateOf<List<TarotCard>>(emptyList()) }
    
    // Используем LaunchedEffect для инициализации карт при первом отображении экрана
    LaunchedEffect(key1 = Unit) {
        try {
            // Безопасно получаем карты
            val shuffledCards = if (tarotCards.isNotEmpty()) {
                tarotCards.shuffled().take(minOf(numCards, tarotCards.size))
            } else {
                emptyList()
            }
            cardsList.value = shuffledCards
        } catch (e: Exception) {
            // Обрабатываем ошибку
            println("Ошибка при инициализации карт: ${e.message}")
            cardsList.value = emptyList()
        }
    }
    
    var isSaved by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(
                text = when (numCards) {
                    3 -> "Прошлое, настоящее и будущее"
                    5 -> "Подробный расклад"
                    10 -> "Кельтский крест"
                    else -> "Расклад на $numCards карт"
                },
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )
        }

        // Проверяем, есть ли карты для отображения
        if (cardsList.value.isEmpty()) {
            // Показываем сообщение о загрузке или ошибке
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Загрузка карт...",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            // Отображаем карты
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(cardsList.value) { card ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Card(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                ) {
                                    // Загружаем изображение
                                    AsyncImage(
                                        model = ImageRequest.Builder(context)
                                            .data("file:///android_asset/" + card.imagePath)
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = card.name,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Column {
                                    Text(
                                        card.name,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        card.element,
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                "Описание",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                card.description,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Justify,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            Text(
                                "Ситуация",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                card.situation,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Justify,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            Text(
                                "Ключевые слова",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            KeywordsList(card.keywords)

                            Text(
                                "Совет",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                card.advice,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Justify
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        try {
                            val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                            HistoryManager.saveTarotSpread(context, cardsList.value, currentDate)
                            isSaved = true
                            Toast.makeText(context, "Расклад сохранен!", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Ошибка при сохранении расклада", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Сохранить расклад")
                }

                Button(
                    onClick = {
                        try {
                            // Безопасно получаем новые карты
                            val shuffledCards = if (tarotCards.isNotEmpty()) {
                                tarotCards.shuffled().take(minOf(numCards, tarotCards.size))
                            } else {
                                emptyList()
                            }
                            cardsList.value = shuffledCards
                            isSaved = false
                        } catch (e: Exception) {
                            Toast.makeText(context, "Ошибка при обновлении расклада", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Сменить расклад")
                }
            }

            if (isSaved) {
                Text(
                    "Расклад сохранён!",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
    }
} 
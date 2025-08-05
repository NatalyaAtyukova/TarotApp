package com.tarotapp.reading.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarotapp.reading.TarotCard
import com.tarotapp.reading.getTarotCardsForLanguage
import com.tarotapp.reading.utils.HistoryManager
import com.tarotapp.reading.utils.YandexBannerAd
import com.tarotapp.reading.utils.showYandexInterstitialAd
import com.tarotapp.reading.R
import com.google.accompanist.flowlayout.FlowRow
import java.text.SimpleDateFormat
import java.util.*
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import android.widget.Toast
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.background
import androidx.compose.ui.res.stringResource

@Composable
private fun KeywordChip(keyword: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        ),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            keyword,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
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
            .padding(bottom = 8.dp)
            .height(120.dp)
    ) {
        items(keywords) { keyword ->
            KeywordChip(keyword)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PremiumTarotScreen(numCards: Int, isSubscribed: Boolean, onNavigateBack: () -> Unit = {}) {
    val context = LocalContext.current
    
    LaunchedEffect(Unit) {
        showYandexInterstitialAd(context)
    }
    
    // Инициализируем карты напрямую, без использования remember или rememberSaveable
    // Это должно предотвратить проблемы с сохранением состояния
    val cardsList = remember { mutableStateOf<List<TarotCard>>(emptyList()) }
    
    // Используем LaunchedEffect для инициализации карт при первом отображении экрана
    LaunchedEffect(key1 = Unit) {
        try {
            // Безопасно получаем карты
            val cards = getTarotCardsForLanguage(context)
            val shuffledCards = if (cards.isNotEmpty()) {
                cards.shuffled().take(minOf(numCards, cards.size))
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

    Scaffold(
        bottomBar = {
            YandexBannerAd(modifier = Modifier.fillMaxWidth())
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                        contentDescription = stringResource(R.string.back),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = when (numCards) {
                        5 -> stringResource(R.string.premium_spread_five_cards)
                        10 -> stringResource(R.string.premium_spread_celtic_cross)
                        else -> stringResource(R.string.premium_spread_cards_count, numCards)
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
                        text = stringResource(R.string.loading_cards),
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
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            card.description,
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            modifier = Modifier.padding(top = 4.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    stringResource(R.string.situation),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                Text(
                                    card.situation,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                                Text(
                                    stringResource(R.string.keywords),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                KeywordsList(card.keywords)

                                Text(
                                    stringResource(R.string.advice),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                Text(
                                    card.advice,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        stringResource(R.string.element_label, card.element),
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                    card.planet?.let {
                                        Text(
                                            stringResource(R.string.planet_label, it),
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                }
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
                                Toast.makeText(context, context.getString(R.string.spread_saved), Toast.LENGTH_SHORT).show()
                            } catch (e: Exception) {
                                Toast.makeText(context, context.getString(R.string.error_saving_spread), Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(stringResource(R.string.save_spread))
                    }

                    Button(
                        onClick = {
                            try {
                                // Безопасно получаем новые карты
                                val cards = getTarotCardsForLanguage(context)
                                val shuffledCards = if (cards.isNotEmpty()) {
                                    cards.shuffled().take(minOf(numCards, cards.size))
                                } else {
                                    emptyList()
                                }
                                cardsList.value = shuffledCards
                                isSaved = false
                                showYandexInterstitialAd(context)
                            } catch (e: Exception) {
                                Toast.makeText(context, context.getString(R.string.error_updating_spread), Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(stringResource(R.string.change_spread))
                    }
                }

                if (isSaved) {
                    Text(
                        stringResource(R.string.spread_saved),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }
        }
    }
} 
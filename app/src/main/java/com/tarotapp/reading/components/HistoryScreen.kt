package com.tarotapp.reading.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarotapp.reading.utils.HistoryManager
import com.tarotapp.reading.utils.YandexBannerAd
import com.tarotapp.reading.utils.showYandexInterstitialAd
import androidx.compose.material3.Scaffold
import com.tarotapp.reading.R
import androidx.compose.ui.res.stringResource

@Composable
fun HistoryScreen(isSubscribed: Boolean) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        showYandexInterstitialAd(context)
    }
    var history by remember { mutableStateOf(HistoryManager.loadHistory(context)) }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            if (isSubscribed) {
                YandexBannerAd(modifier = Modifier.fillMaxWidth())
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.saved_spreads),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (!isSubscribed) {
                Text(
                    text = stringResource(R.string.history_subscription_required),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 32.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                if (history.isEmpty()) {
                    Text(
                        text = stringResource(R.string.history_empty_message),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(top = 32.dp),
                        textAlign = TextAlign.Center
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(vertical = 16.dp)
                    ) {
                        items(history) { spread ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                                        shape = MaterialTheme.shapes.medium
                                    )
                                    .padding(16.dp)
                            ) {
                                Text(
                                    stringResource(R.string.history_date, spread.date),
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    stringResource(R.string.history_cards_label),
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                spread.cards.forEach { cardName ->
                                    Text(
                                        "• $cardName",
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                                        modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = {
                Text(text = stringResource(R.string.confirmation))
            },
            text = {
                Text(text = stringResource(R.string.clear_history_confirmation))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        HistoryManager.clearHistory(context)
                        history = emptyList()
                        showConfirmationDialog = false
                    }
                ) {
                    Text(stringResource(R.string.yes), color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmationDialog = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
} 
package com.example.tarotapp.components

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
import com.example.tarotapp.utils.HistoryManager

@Composable
fun HistoryScreen(isSubscribed: Boolean) {
    val context = LocalContext.current
    var history by remember { mutableStateOf(HistoryManager.loadHistory(context)) }
    var showConfirmationDialog by remember { mutableStateOf(false) } // Управление состоянием диалога

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Сохранённые расклады",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (!isSubscribed) {
            Text(
                text = "Доступ к истории доступен только для подписчиков.",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 32.dp),
                textAlign = TextAlign.Center
            )
            return
        }

        if (history.isEmpty()) {
            Text(
                text = "История пустая. Сохраните свои первые расклады!",
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
                            "Дата: ${spread.date}",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            "Карты:",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        spread.cards.forEach { cardName ->
                            Text(
                                "• $cardName",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    showConfirmationDialog = true // Открыть диалог подтверждения
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Очистить историю")
            }
        }
    }

    // Диалог подтверждения очистки истории
    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = {
                Text(text = "Подтверждение")
            },
            text = {
                Text(text = "Вы уверены, что хотите очистить всю историю? Это действие необратимо.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        HistoryManager.clearHistory(context)
                        history = emptyList() // Обновляем состояние истории
                        showConfirmationDialog = false
                    }
                ) {
                    Text("Да", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmationDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }
}
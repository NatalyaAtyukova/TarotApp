package com.example.tarotapp.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SubscriptionScreen(
    hasThreeCardSubscription: Boolean,
    hasPremiumSubscription: Boolean,
    onThreeCardSubscribe: (Boolean) -> Unit,
    onPremiumSubscribe: (Boolean) -> Unit,
    onNavigateToTarotScreens: () -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            // Отображаем индикатор загрузки, если идет процесс выполнения действия
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Заголовок
                Text(
                    text = "Управление подписками",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Подписка на 3 карты
                Text(
                    text = "Подписка на 3 карты: ${
                        if (hasPremiumSubscription) {
                            "Не требуется (Премиум активен)"
                        } else if (hasThreeCardSubscription) {
                            "Активна"
                        } else {
                            "Не активна"
                        }
                    }",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Button(
                    onClick = { onThreeCardSubscribe(!hasThreeCardSubscription) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading && !hasPremiumSubscription
                ) {
                    Text(
                        if (hasThreeCardSubscription) "Отключить подписку на 3 карты"
                        else "Активировать подписку на 3 карты"
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Премиум подписка
                Text(
                    text = "Премиум подписка: ${
                        if (hasPremiumSubscription) {
                            "Активна"
                        } else {
                            "Не активна"
                        }
                    }",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Button(
                    onClick = { onPremiumSubscribe(!hasPremiumSubscription) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                ) {
                    Text(
                        if (hasPremiumSubscription) "Отключить премиум подписку"
                        else "Активировать премиум подписку"
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Кнопка перехода к экранам Таро
                Button(
                    onClick = onNavigateToTarotScreens,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                ) {
                    Text("Перейти к экранам Таро")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Подсказка для пользователя
                when {
                    hasPremiumSubscription -> {
                        Text(
                            text = "Вы можете использовать премиум функции, включая доступ к раскладам (3, 5 и 10 карт).",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    hasThreeCardSubscription -> {
                        Text(
                            text = "Вы можете использовать базовые расклады (3 карты).",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    else -> {
                        Text(
                            text = "Для доступа к раскладам оформите подписку.",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }

        // Отображение ошибок через Snackbar
        errorMessage?.let {
            LaunchedEffect(snackbarHostState) {
                snackbarHostState.showSnackbar(it)
            }
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
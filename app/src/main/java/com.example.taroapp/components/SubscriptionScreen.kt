package com.example.tarotapp.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SubscriptionScreen(
    hasThreeCardSubscription: Boolean,
    hasPremiumSubscription: Boolean,
    threeCardSubscriptionEndDate: String?,
    premiumSubscriptionEndDate: String?,
    onThreeCardSubscribe: (Boolean) -> Unit,
    onPremiumSubscribe: (Boolean) -> Unit,
    onNavigateToTarotScreens: () -> Unit
) {
    var isThreeCardLoading by remember { mutableStateOf(false) }
    var isPremiumLoading by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Управление подписками",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Подписка на 3 карты
        Text(
            text = "Подписка на 3 карты: ${
                if (hasThreeCardSubscription) {
                    "Активна до ${threeCardSubscriptionEndDate ?: "неизвестно"}"
                } else {
                    "Не активна"
                }
            }",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LoadingButton(
            isLoading = isThreeCardLoading,
            text = if (hasThreeCardSubscription) "Отключить подписку на 3 карты" else "Активировать подписку на 3 карты",
            onClick = {
                isThreeCardLoading = true
                onThreeCardSubscribe(!hasThreeCardSubscription)
                isThreeCardLoading = false
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !hasPremiumSubscription // Подписка на 3 карты недоступна с премиум подпиской
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Премиум подписка
        Text(
            text = "Премиум подписка: ${
                if (hasPremiumSubscription) {
                    "Активна до ${premiumSubscriptionEndDate ?: "неизвестно"}"
                } else {
                    "Не активна"
                }
            }",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LoadingButton(
            isLoading = isPremiumLoading,
            text = if (hasPremiumSubscription) "Отключить премиум подписку" else "Активировать премиум подписку",
            onClick = {
                isPremiumLoading = true
                onPremiumSubscribe(!hasPremiumSubscription)
                isPremiumLoading = false
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка перехода к экранам Таро
        Button(
            onClick = onNavigateToTarotScreens,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Перейти к экранам Таро")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Подсказка для пользователя
        when {
            hasPremiumSubscription -> {
                Text(
                    text = "Вы можете использовать премиум функции, включая доступ к базовым раскладам (5 и 10 карт).",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            hasThreeCardSubscription -> {
                Text(
                    text = "Вы можете использовать расклады на 3 карты.",
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

@Composable
fun LoadingButton(
    isLoading: Boolean,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled && !isLoading // Кнопка отключена, если идет загрузка
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(16.dp)
            )
        } else {
            Text(text)
        }
    }
}
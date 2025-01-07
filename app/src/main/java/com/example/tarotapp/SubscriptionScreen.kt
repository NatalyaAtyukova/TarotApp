package com.example.tarotapp.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SubscriptionScreen(
    hasThreeCardSubscription: Boolean,
    hasPremiumSubscription: Boolean,
    onThreeCardSubscribe: (Boolean) -> Unit,
    onPremiumSubscribe: (Boolean) -> Unit,
    onNavigateToTarotScreens: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
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
            enabled = !hasPremiumSubscription // Нельзя активировать базовую подписку, если активна премиум подписка
        ) {
            Text(if (hasThreeCardSubscription) "Отключить подписку на 3 карты" else "Активировать подписку на 3 карты")
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
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (hasPremiumSubscription) "Отключить премиум подписку" else "Активировать премиум подписку")
        }

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
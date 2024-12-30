package com.example.tarotapp.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SubscriptionScreen(
    hasBasicSubscription: Boolean,
    hasPremiumSubscription: Boolean,
    basicSubscriptionEndDate: String?, // Дата окончания базовой подписки
    premiumSubscriptionEndDate: String?, // Дата окончания премиум подписки
    onBasicSubscribe: (Boolean) -> Unit,
    onPremiumSubscribe: (Boolean) -> Unit,
    onNavigateToTarotScreens: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Управление подписками",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Базовая подписка
        Text(
            text = "Базовая подписка: ${
                if (hasBasicSubscription || hasPremiumSubscription) {
                    "Активна до ${
                        basicSubscriptionEndDate ?: premiumSubscriptionEndDate ?: "неизвестно"
                    }"
                } else "Не активна"
            }",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Button(
            onClick = { onBasicSubscribe(!hasBasicSubscription) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !hasPremiumSubscription // Отключить кнопку, если премиум активен
        ) {
            Text(if (hasBasicSubscription) "Отключить базовую подписку" else "Активировать базовую подписку")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Премиум подписка
        Text(
            text = "Премиум подписка: ${
                if (hasPremiumSubscription) "Активна до $premiumSubscriptionEndDate" else "Не активна"
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
    }
}
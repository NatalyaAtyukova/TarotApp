package com.example.tarotapp.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.GroupWork
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun TarotScreens(
    navigateToSingleCard: () -> Unit,
    navigateToThreeCards: () -> Unit,
    navigateToFiveCards: () -> Unit,
    navigateToTenCards: () -> Unit,
    navigateToHistory: () -> Unit,
    hasThreeCardSubscription: Boolean,
    hasPremiumSubscription: Boolean
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = "Экран Таро",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Одна карта: всегда доступно
        TarotCardOption(
            text = "Одна карта",
            icon = Icons.Default.Star,
            color = MaterialTheme.colorScheme.primary,
            onClick = navigateToSingleCard
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // Три карты: доступно для подписки на 3 карты или премиум подписки
        TarotCardOption(
            text = "Три карты",
            icon = Icons.Default.GroupWork,
            color = if (hasThreeCardSubscription || hasPremiumSubscription) MaterialTheme.colorScheme.primary else Color.Gray,
            onClick = {
                if (hasThreeCardSubscription || hasPremiumSubscription) {
                    navigateToThreeCards()
                } else {
                    coroutineScope.launch {
                        showSubscriptionWarning(snackbarHostState, "Подписка на 3 карты")
                    }
                }
            }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // Пять карт: доступно только для премиум подписки
        TarotCardOption(
            text = "Пять карт",
            icon = Icons.Default.Dashboard,
            color = if (hasPremiumSubscription) MaterialTheme.colorScheme.primary else Color.Gray,
            onClick = {
                if (hasPremiumSubscription) {
                    navigateToFiveCards()
                } else {
                    coroutineScope.launch {
                        showSubscriptionWarning(snackbarHostState, "Премиум подписка")
                    }
                }
            }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // Десять карт: доступно только для премиум подписки
        TarotCardOption(
            text = "Десять карт",
            icon = Icons.Default.ViewModule,
            color = if (hasPremiumSubscription) MaterialTheme.colorScheme.primary else Color.Gray,
            onClick = {
                if (hasPremiumSubscription) {
                    navigateToTenCards()
                } else {
                    coroutineScope.launch {
                        showSubscriptionWarning(snackbarHostState, "Премиум подписка")
                    }
                }
            }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // История: всегда доступна
        TarotCardOption(
            text = "История",
            icon = Icons.Default.History,
            color = MaterialTheme.colorScheme.primary,
            onClick = navigateToHistory
        )

        Spacer(modifier = Modifier.weight(1f))

        // Snackbar для отображения предупреждений
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun TarotCardOption(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                color = color,
                fontSize = 16.sp
            )
        }
    }
}

suspend fun showSubscriptionWarning(
    snackbarHostState: SnackbarHostState,
    subscriptionType: String
) {
    snackbarHostState.showSnackbar("Для этого действия требуется $subscriptionType.")
} 
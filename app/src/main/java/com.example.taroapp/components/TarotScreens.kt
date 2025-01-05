package com.example.tarotapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextAlign
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

        // Карты и их доступность
        TarotCardOption(
            text = "Одна карта",
            icon = Icons.Default.Star,
            color = MaterialTheme.colorScheme.primary,
            isEnabled = true,
            onClick = navigateToSingleCard
        )

        TarotCardOption(
            text = "Три карты",
            icon = Icons.Default.GroupWork,
            color = MaterialTheme.colorScheme.primary,
            isEnabled = hasThreeCardSubscription || hasPremiumSubscription,
            onClick = {
                if (hasThreeCardSubscription || hasPremiumSubscription) {
                    navigateToThreeCards()
                } else {
                    coroutineScope.launch {
                        showSubscriptionWarning(snackbarHostState, "подписка на 3 карты")
                    }
                }
            }
        )

        TarotCardOption(
            text = "Пять карт",
            icon = Icons.Default.Dashboard,
            color = MaterialTheme.colorScheme.primary,
            isEnabled = hasPremiumSubscription,
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

        TarotCardOption(
            text = "Десять карт",
            icon = Icons.Default.ViewModule,
            color = MaterialTheme.colorScheme.primary,
            isEnabled = hasPremiumSubscription,
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

        TarotCardOption(
            text = "История",
            icon = Icons.Default.History,
            color = MaterialTheme.colorScheme.primary,
            isEnabled = true,
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
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isEnabled) color.copy(alpha = 0.1f) else Color.LightGray.copy(alpha = 0.2f)
    val contentColor = if (isEnabled) color else Color.Gray

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        onClick = {
            if (isEnabled) onClick()
        },
        enabled = isEnabled
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
                tint = contentColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = text,
                    color = contentColor,
                    fontSize = 16.sp
                )
                if (!isEnabled) {
                    Text(
                        text = "Требуется подписка",
                        fontSize = 12.sp,
                        color = Color.Red,
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}

suspend fun showSubscriptionWarning(
    snackbarHostState: SnackbarHostState,
    subscriptionType: String
) {
    snackbarHostState.showSnackbar("Для этого действия требуется $subscriptionType.")
}
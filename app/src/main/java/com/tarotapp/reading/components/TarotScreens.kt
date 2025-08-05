package com.tarotapp.reading.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.GroupWork
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import com.tarotapp.reading.R

@Composable
fun TarotScreens(
    navigateToSingleCard: () -> Unit,
    navigateToThreeCards: () -> Unit,
    navigateToFiveCards: () -> Unit,
    navigateToTenCards: () -> Unit,
    navigateToHistory: () -> Unit,
    navigateToLanguageSettings: () -> Unit,
    hasThreeCardSubscription: Boolean,
    hasPremiumSubscription: Boolean
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = context.getString(R.string.main_title),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Text(
                text = context.getString(R.string.main_subtitle),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Одна карта: всегда доступно
            MysticalTarotOption(
                title = context.getString(R.string.spread_single_card),
                description = context.getString(R.string.spread_single_card_desc),
                icon = Icons.Filled.Star,
                isAvailable = true,
                onClick = navigateToSingleCard
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Три карты: доступно для подписки на 3 карты или премиум подписки
            MysticalTarotOption(
                title = context.getString(R.string.spread_three_cards),
                description = context.getString(R.string.spread_three_cards_desc),
                icon = Icons.Filled.GroupWork,
                isAvailable = hasThreeCardSubscription || hasPremiumSubscription,
                onClick = {
                    if (hasThreeCardSubscription || hasPremiumSubscription) {
                        navigateToThreeCards()
                    } else {
                        coroutineScope.launch {
                            showSubscriptionWarning(snackbarHostState, context.getString(R.string.subscription_three_cards))
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Пять карт: доступно только для премиум подписки
            MysticalTarotOption(
                title = context.getString(R.string.spread_five_cards),
                description = context.getString(R.string.spread_five_cards_desc),
                icon = Icons.Filled.Dashboard,
                isAvailable = hasPremiumSubscription,
                onClick = {
                    if (hasPremiumSubscription) {
                        navigateToFiveCards()
                    } else {
                        coroutineScope.launch {
                            showSubscriptionWarning(snackbarHostState, context.getString(R.string.subscription_premium))
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Десять карт: доступно только для премиум подписки
            MysticalTarotOption(
                title = context.getString(R.string.spread_celtic_cross),
                description = context.getString(R.string.spread_celtic_cross_desc),
                icon = Icons.Filled.ViewModule,
                isAvailable = hasPremiumSubscription,
                onClick = {
                    if (hasPremiumSubscription) {
                        navigateToTenCards()
                    } else {
                        coroutineScope.launch {
                            showSubscriptionWarning(snackbarHostState, context.getString(R.string.subscription_premium))
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // История: доступно для всех подписок
            MysticalTarotOption(
                title = context.getString(R.string.spread_history),
                description = context.getString(R.string.spread_history_desc),
                icon = Icons.Filled.History,
                isAvailable = true,
                onClick = navigateToHistory
            )

            Spacer(modifier = Modifier.weight(1f))

            // Кнопка настроек языка
            MysticalTarotOption(
                title = context.getString(R.string.language_settings),
                description = context.getString(R.string.select_language),
                icon = Icons.Filled.Settings,
                isAvailable = true,
                onClick = navigateToLanguageSettings
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Snackbar для отображения предупреждений
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MysticalTarotOption(
    title: String,
    description: String,
    icon: ImageVector,
    isAvailable: Boolean,
    onClick: () -> Unit
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val disabledColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
    val cardColor = if (isAvailable) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    val textColor = if (isAvailable) MaterialTheme.colorScheme.onSurface else disabledColor
    val accentColor = if (isAvailable) primaryColor else disabledColor

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isAvailable) 4.dp else 1.dp
        ),
        border = if (isAvailable) BorderStroke(1.dp, primaryColor.copy(alpha = 0.3f)) else null,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = accentColor.copy(alpha = 0.1f)
                ),
                modifier = Modifier.size(56.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = accentColor,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = textColor
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor.copy(alpha = 0.7f)
                )
            }
        }
    }
}

suspend fun showSubscriptionWarning(
    snackbarHostState: SnackbarHostState,
    subscriptionType: String
) {
    snackbarHostState.showSnackbar(subscriptionType)
} 
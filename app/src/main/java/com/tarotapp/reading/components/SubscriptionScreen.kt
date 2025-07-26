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

// Data classes representing the subscription structure
data class SubscriptionPeriod(
    val years: Int,
    val months: Int,
    val days: Int
)

data class ProductSubscription(
    val subscriptionPeriod: SubscriptionPeriod?,
    val freeTrialPeriod: SubscriptionPeriod?,
    val gracePeriod: SubscriptionPeriod?,
    val introductoryPrice: String?,
    val introductoryPriceAmount: String?,
    val introductoryPricePeriod: SubscriptionPeriod?
)

@Composable
fun SubscriptionScreen(
    currentSubscription: ProductSubscription?,
    onNavigateToTarotScreens: () -> Unit,
    onSubscribe: (String) -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Подписки",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (currentSubscription != null) {
            Text(
                text = "У вас активна подписка",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            currentSubscription.subscriptionPeriod?.let { period ->
                Text(
                    text = "Период подписки: ${period.years} лет, ${period.months} месяцев, ${period.days} дней",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = onNavigateToTarotScreens,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Перейти к Таро")
            }
        } else {
            Text(
                text = "Выберите подписку",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Подписка на 3 карты
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Подписка на 3 карты",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Доступ к раскладам из 3 карт",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Button(
                        onClick = { onSubscribe("three_card_subscription") },
                        enabled = !isLoading,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Подписаться")
                    }
                }
            }

            // Премиум подписка
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Премиум подписка",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Доступ ко всем раскладам (3, 5 и 10 карт)",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Button(
                        onClick = { onSubscribe("premium_subscription") },
                        enabled = !isLoading,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Подписаться")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
} 
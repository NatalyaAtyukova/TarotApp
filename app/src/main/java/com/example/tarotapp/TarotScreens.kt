package com.example.tarotapp.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TarotScreens(
    navigateToSingleCard: () -> Unit,
    navigateToThreeCards: () -> Unit,
    navigateToFiveCards: () -> Unit,
    navigateToTenCards: () -> Unit,
    navigateToHistory: () -> Unit,
    hasBasicSubscription: Boolean,
    hasPremiumSubscription: Boolean
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Экран Таро",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(onClick = navigateToSingleCard, modifier = Modifier.fillMaxWidth()) {
            Text("Одна карта")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (hasBasicSubscription || hasPremiumSubscription) {
                    navigateToThreeCards()
                } else {
                    showSubscriptionWarning(coroutineScope, snackbarHostState, "Базовая подписка")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Три карты")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (hasPremiumSubscription) {
                    navigateToFiveCards()
                } else {
                    showSubscriptionWarning(coroutineScope, snackbarHostState, "Премиум подписка")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Пять карт")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (hasPremiumSubscription) {
                    navigateToTenCards()
                } else {
                    showSubscriptionWarning(coroutineScope, snackbarHostState, "Премиум подписка")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Десять карт")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = navigateToHistory, modifier = Modifier.fillMaxWidth()) {
            Text("История")
        }

        // SnackbarHost для отображения предупреждений
        SnackbarHost(hostState = snackbarHostState)
    }
}

fun showSubscriptionWarning(
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    subscriptionType: String
) {
    coroutineScope.launch {
        snackbarHostState.showSnackbar("Для этого действия требуется $subscriptionType.")
    }
}
package com.example.tarotapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.rustore.sdk.billingclient.RuStoreBillingClient
import ru.rustore.sdk.billingclient.RuStoreBillingClientFactory
import ru.rustore.sdk.billingclient.model.purchase.PaymentResult
import com.example.tarotapp.components.TarotScreens
import com.example.tarotapp.components.SubscriptionScreen
import com.example.tarotapp.components.SingleCardScreen
import com.example.tarotapp.components.MultiCardScreen
import com.example.tarotapp.components.PremiumTarotScreen
import com.example.tarotapp.components.HistoryScreen

class MainActivity : ComponentActivity() {
    private lateinit var billingClient: RuStoreBillingClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация RuStoreBillingClient
        billingClient = RuStoreBillingClientFactory.create(
            context = this,
            consoleApplicationId = "2063587401", // Укажите свой ID из RuStore Консоли
            deeplinkScheme = "com.example.tarotapp" // Укажите схему deeplink
        )

        setContent {
            MainApp(billingClient)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        billingClient.onNewIntent(intent)
    }
}

@Composable
fun MainApp(billingClient: RuStoreBillingClient) {
    val context = LocalContext.current
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("subscriptions", Context.MODE_PRIVATE)

    val navController = rememberNavController()

    var hasThreeCardSubscription by remember { mutableStateOf(false) }
    var hasPremiumSubscription by remember { mutableStateOf(false) }

    // Функция сохранения подписок
    fun saveSubscriptions() {
        sharedPreferences.edit().apply {
            putBoolean("hasThreeCardSubscription", hasThreeCardSubscription)
            putBoolean("hasPremiumSubscription", hasPremiumSubscription)
            apply()
        }
    }

    // Получение подписок
    LaunchedEffect(Unit) {
        billingClient.purchases.getPurchases()
            .addOnSuccessListener { purchases ->
                hasThreeCardSubscription = purchases.any { it.productId == "three_card_subscription" }
                hasPremiumSubscription = purchases.any { it.productId == "premium_monthly_subscription" }
                saveSubscriptions()
            }
            .addOnFailureListener {
                println("Ошибка получения подписок: ${it.message}")
            }
    }

    Scaffold(
        topBar = {
            TabRow(selectedTabIndex = 0) {
                Tab(
                    selected = true,
                    onClick = { navController.navigate("subscription") },
                    text = { Text("Подписки") }
                )
                Tab(
                    selected = false,
                    onClick = { navController.navigate("tarot") },
                    text = { Text("Экраны Таро") }
                )
            }
        },
        content = { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = "subscription",
                modifier = Modifier.padding(paddingValues)
            ) {
                composable("subscription") {
                    SubscriptionScreen(
                        hasThreeCardSubscription = hasThreeCardSubscription,
                        hasPremiumSubscription = hasPremiumSubscription,
                        onThreeCardSubscribe = { /* Ваш код */ },
                        onPremiumSubscribe = { /* Ваш код */ },
                        onNavigateToTarotScreens = { navController.navigate("tarot") }
                    )
                }
                composable("tarot") {
                    TarotScreens(
                        navigateToSingleCard = { navController.navigate("single") },
                        navigateToThreeCards = {
                            if (hasThreeCardSubscription || hasPremiumSubscription) {
                                navController.navigate("three")
                            }
                        },
                        navigateToFiveCards = {
                            if (hasPremiumSubscription) {
                                navController.navigate("five")
                            }
                        },
                        navigateToTenCards = {
                            if (hasPremiumSubscription) {
                                navController.navigate("ten")
                            }
                        },
                        navigateToHistory = { navController.navigate("history") },
                        hasThreeCardSubscription = hasThreeCardSubscription,
                        hasPremiumSubscription = hasPremiumSubscription
                    )
                }
                composable("single") {
                    SingleCardScreen(isSubscribed = hasPremiumSubscription || hasThreeCardSubscription)
                }
                composable("three") {
                    MultiCardScreen(numCards = 3, isSubscribed = hasThreeCardSubscription || hasPremiumSubscription)
                }
                composable("five") {
                    PremiumTarotScreen(numCards = 5, isSubscribed = hasPremiumSubscription)
                }
                composable("ten") {
                    PremiumTarotScreen(numCards = 10, isSubscribed = hasPremiumSubscription)
                }
                composable("history") {
                    HistoryScreen(isSubscribed = hasThreeCardSubscription || hasPremiumSubscription)
                }
            }
        }
    )
}
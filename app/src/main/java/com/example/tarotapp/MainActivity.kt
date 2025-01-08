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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.rustore.sdk.billingclient.RuStoreBillingClient
import ru.rustore.sdk.billingclient.RuStoreBillingClientFactory
import ru.rustore.sdk.billingclient.model.purchase.PaymentResult
import com.example.tarotapp.components.*

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
    var isLoading by remember { mutableStateOf(false) }

    // Отслеживаем текущий маршрут
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        topBar = {
            // TabRow обновляется в зависимости от текущего маршрута
            TabRow(
                selectedTabIndex = when (currentRoute) {
                    "subscription" -> 0
                    "tarot", "single", "three", "five", "ten", "history" -> 1
                    else -> 0
                }
            ) {
                Tab(
                    selected = currentRoute == "subscription",
                    onClick = {
                        if (currentRoute != "subscription") {
                            navController.navigate("subscription") {
                                popUpTo("subscription") { inclusive = false }
                            }
                        }
                    },
                    text = { Text("Подписки") }
                )
                Tab(
                    selected = currentRoute in listOf("tarot", "single", "three", "five", "ten", "history"),
                    onClick = {
                        if (currentRoute !in listOf("tarot", "single", "three", "five", "ten", "history")) {
                            navController.navigate("tarot") {
                                popUpTo("tarot") { inclusive = false }
                            }
                        }
                    },
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
                        onThreeCardSubscribe = { isActivating ->
                            isLoading = true
                            billingClient.purchases.purchaseProduct(
                                productId = "three_card_subscription",
                                orderId = "three_card_order_${System.currentTimeMillis()}",
                                quantity = 1,
                                developerPayload = null
                            ).addOnSuccessListener { result ->
                                if (result is PaymentResult.Success) {
                                    billingClient.purchases.confirmPurchase(result.purchaseId)
                                        .addOnSuccessListener {
                                            hasThreeCardSubscription = isActivating
                                            saveSubscriptions(sharedPreferences)
                                            isLoading = false
                                        }
                                        .addOnFailureListener {
                                            println("Ошибка подтверждения подписки: ${it.message}")
                                            isLoading = false
                                        }
                                } else {
                                    println("Ошибка покупки: ${result}")
                                    isLoading = false
                                }
                            }.addOnFailureListener {
                                println("Ошибка вызова RuStore: ${it.message}")
                                isLoading = false
                            }
                        },
                        onPremiumSubscribe = { isActivating ->
                            isLoading = true
                            billingClient.purchases.purchaseProduct(
                                productId = "premium_monthly_subscription",
                                orderId = "premium_order_${System.currentTimeMillis()}",
                                quantity = 1,
                                developerPayload = null
                            ).addOnSuccessListener { result ->
                                if (result is PaymentResult.Success) {
                                    billingClient.purchases.confirmPurchase(result.purchaseId)
                                        .addOnSuccessListener {
                                            hasPremiumSubscription = isActivating
                                            saveSubscriptions(sharedPreferences)
                                            isLoading = false
                                        }
                                        .addOnFailureListener {
                                            println("Ошибка подтверждения подписки: ${it.message}")
                                            isLoading = false
                                        }
                                } else {
                                    println("Ошибка покупки: ${result}")
                                    isLoading = false
                                }
                            }.addOnFailureListener {
                                println("Ошибка вызова RuStore: ${it.message}")
                                isLoading = false
                            }
                        },
                        onNavigateToTarotScreens = { navController.navigate("tarot") },
                        isLoading = isLoading
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

fun saveSubscriptions(sharedPreferences: SharedPreferences) {
    sharedPreferences.edit().apply {
        putBoolean("hasThreeCardSubscription", true)
        putBoolean("hasPremiumSubscription", true)
        apply()
    }
}
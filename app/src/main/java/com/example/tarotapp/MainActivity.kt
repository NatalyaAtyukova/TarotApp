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
            consoleApplicationId = "2063587401",
            deeplinkScheme = "com.example.tarotapp"
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
    val sharedPreferences = context.getSharedPreferences("subscriptions", Context.MODE_PRIVATE)

    val navController = rememberNavController()

    var hasThreeCardSubscription by remember { mutableStateOf(false) }
    var hasPremiumSubscription by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Проверка состояния подписок при запуске приложения
    LaunchedEffect(Unit) {
        updateSubscriptionsStatus(billingClient, sharedPreferences) { threeCard, premium ->
            hasThreeCardSubscription = threeCard
            hasPremiumSubscription = premium
        }
    }

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        topBar = {
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
                        onThreeCardSubscribe = {
                            isLoading = true
                            handleSubscriptionPurchase(
                                billingClient,
                                sharedPreferences,
                                productId = "three_card_subscription"
                            ) { threeCard, premium ->
                                hasThreeCardSubscription = threeCard
                                hasPremiumSubscription = premium
                                isLoading = false
                            }
                        },
                        onPremiumSubscribe = {
                            isLoading = true
                            handleSubscriptionPurchase(
                                billingClient,
                                sharedPreferences,
                                productId = "premium_monthly_subscription"
                            ) { threeCard, premium ->
                                hasThreeCardSubscription = threeCard
                                hasPremiumSubscription = premium
                                isLoading = false
                            }
                        },
                        onNavigateToTarotScreens = { navController.navigate("tarot") },
                        isLoading = isLoading,
                        errorMessage = errorMessage
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

fun handleSubscriptionPurchase(
    billingClient: RuStoreBillingClient,
    sharedPreferences: SharedPreferences,
    productId: String,
    onUpdate: (Boolean, Boolean) -> Unit
) {
    billingClient.purchases.getPurchases()
        .addOnSuccessListener { purchases ->
            if (purchases.any { it.productId == productId }) {
                println("Подписка $productId уже активна")
                updateSubscriptionsStatus(billingClient, sharedPreferences, onUpdate)
                return@addOnSuccessListener
            }

            billingClient.purchases.purchaseProduct(
                productId = productId,
                orderId = "${productId}_order_${System.currentTimeMillis()}",
                quantity = 1,
                developerPayload = null
            ).addOnSuccessListener { result ->
                if (result is PaymentResult.Success) {
                    billingClient.purchases.confirmPurchase(result.purchaseId)
                        .addOnSuccessListener {
                            updateSubscriptionsStatus(billingClient, sharedPreferences, onUpdate)
                        }
                        .addOnFailureListener { error ->
                            println("Ошибка подтверждения подписки: ${error.message}")
                        }
                } else {
                    println("Ошибка покупки: $result")
                }
            }.addOnFailureListener { error ->
                println("Ошибка вызова RuStore: ${error.message}")
            }
        }
        .addOnFailureListener { error ->
            println("Ошибка получения списка покупок: ${error.message}")
        }
}

fun updateSubscriptionsStatus(
    billingClient: RuStoreBillingClient,
    sharedPreferences: SharedPreferences,
    onUpdate: (Boolean, Boolean) -> Unit
) {
    billingClient.purchases.getPurchases()
        .addOnSuccessListener { purchases ->
            val hasThreeCard = purchases.any { it.productId == "three_card_subscription" }
            val hasPremium = purchases.any { it.productId == "premium_monthly_subscription" }

            sharedPreferences.edit().apply {
                putBoolean("hasThreeCardSubscription", hasThreeCard)
                putBoolean("hasPremiumSubscription", hasPremium)
                apply()
            }

            onUpdate(hasThreeCard, hasPremium)
        }
        .addOnFailureListener { error ->
            println("Ошибка получения подписок: ${error.message}")
        }
}
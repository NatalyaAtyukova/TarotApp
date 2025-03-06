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

    var currentSubscription by remember { mutableStateOf<ProductSubscription?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Проверка состояния подписок при запуске приложения
    LaunchedEffect(Unit) {
        updateSubscriptionsStatus(billingClient, sharedPreferences) { subscription ->
            currentSubscription = subscription
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
                        currentSubscription = currentSubscription,
                        onNavigateToTarotScreens = { navController.navigate("tarot") },
                        onSubscribe = { productId ->
                            isLoading = true
                            handleSubscriptionPurchase(
                                billingClient,
                                sharedPreferences,
                                productId
                            ) { subscription ->
                                currentSubscription = subscription
                                isLoading = false
                            }
                        },
                        isLoading = isLoading,
                        errorMessage = errorMessage
                    )
                }
                composable("tarot") {
                    TarotScreens(
                        navigateToSingleCard = { navController.navigate("single") },
                        navigateToThreeCards = {
                            if (currentSubscription != null) {
                                navController.navigate("three")
                            }
                        },
                        navigateToFiveCards = {
                            if (currentSubscription != null) {
                                navController.navigate("five")
                            }
                        },
                        navigateToTenCards = {
                            if (currentSubscription != null) {
                                navController.navigate("ten")
                            }
                        },
                        navigateToHistory = { navController.navigate("history") },
                        hasThreeCardSubscription = currentSubscription != null,
                        hasPremiumSubscription = currentSubscription != null
                    )
                }
                composable("single") {
                    SingleCardScreen(isSubscribed = currentSubscription != null)
                }
                composable("three") {
                    MultiCardScreen(numCards = 3, isSubscribed = currentSubscription != null)
                }
                composable("five") {
                    PremiumTarotScreen(numCards = 5, isSubscribed = currentSubscription != null)
                }
                composable("ten") {
                    PremiumTarotScreen(numCards = 10, isSubscribed = currentSubscription != null)
                }
                composable("history") {
                    HistoryScreen(isSubscribed = currentSubscription != null)
                }
            }
        }
    )
}

fun handleSubscriptionPurchase(
    billingClient: RuStoreBillingClient,
    sharedPreferences: SharedPreferences,
    productId: String,
    onUpdate: (ProductSubscription?) -> Unit
) {
    billingClient.purchases.purchaseProduct(
        productId = productId,
        orderId = "order_${System.currentTimeMillis()}",
        quantity = 1
    ).addOnSuccessListener { result ->
        if (result is PaymentResult.Success) {
            billingClient.purchases.confirmPurchase(result.purchaseId)
                .addOnSuccessListener {
                    updateSubscriptionsStatus(billingClient, sharedPreferences, onUpdate)
                }
                .addOnFailureListener { error ->
                    println("Ошибка подтверждения подписки: ${error.message}")
                    onUpdate(null)
                }
        } else {
            println("Ошибка покупки: $result")
            onUpdate(null)
        }
    }.addOnFailureListener { error ->
        println("Ошибка вызова RuStore: ${error.message}")
        onUpdate(null)
    }
}

fun updateSubscriptionsStatus(
    billingClient: RuStoreBillingClient,
    sharedPreferences: SharedPreferences,
    onUpdate: (ProductSubscription?) -> Unit
) {
    billingClient.purchases.getPurchases()
        .addOnSuccessListener { purchases ->
            val threeCard = purchases.find { it.productId == "three_card_subscription" }
            val premium = purchases.find { it.productId == "premium_monthly_subscription" }

            val subscription = ProductSubscription(
                subscriptionPeriod = null, // Replace with actual subscription period if available
                freeTrialPeriod = null, // Replace with actual free trial period if available
                gracePeriod = null, // Replace with actual grace period if available
                introductoryPrice = null, // Replace with actual price info if available
                introductoryPriceAmount = null, // Replace with actual price amount if available
                introductoryPricePeriod = null // Replace with actual period if available
            )

            sharedPreferences.edit().apply {
                putString("currentSubscription", subscription.toString())
                apply()
            }

            onUpdate(subscription)
        }
        .addOnFailureListener { error ->
            println("Ошибка получения подписок: ${error.message}")
            onUpdate(null)
        }
}

package com.example.tarotapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
import com.example.tarotapp.components.SubscriptionScreen
import com.example.tarotapp.components.TarotScreens
import com.example.tarotapp.components.HistoryScreen

import java.util.*

class MainActivity : ComponentActivity() {
    private lateinit var billingClient: RuStoreBillingClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        billingClient = RuStoreBillingClientFactory.create(
            context = applicationContext,
            consoleApplicationId = "2063587401",
            deeplinkScheme = "com.example.tarotapp"
        )

        if (savedInstanceState == null) {
            billingClient.onNewIntent(intent)
        }

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

    var hasThreeCardSubscription by remember {
        mutableStateOf(sharedPreferences.getBoolean("hasThreeCardSubscription", false))
    }
    var hasPremiumSubscription by remember {
        mutableStateOf(sharedPreferences.getBoolean("hasPremiumSubscription", false))
    }
    var threeCardSubscriptionEndDate by remember {
        mutableStateOf(sharedPreferences.getString("threeCardSubscriptionEndDate", null))
    }
    var premiumSubscriptionEndDate by remember {
        mutableStateOf(sharedPreferences.getString("premiumSubscriptionEndDate", null))
    }

    fun saveSubscriptions() {
        sharedPreferences.edit().apply {
            putBoolean("hasThreeCardSubscription", hasThreeCardSubscription)
            putBoolean("hasPremiumSubscription", hasPremiumSubscription)
            putString("threeCardSubscriptionEndDate", threeCardSubscriptionEndDate)
            putString("premiumSubscriptionEndDate", premiumSubscriptionEndDate)
            apply()
        }
    }

    fun purchaseSubscription(productId: String) {
        billingClient.products.getProducts(listOf(productId))
            .addOnSuccessListener { products ->
                val product = products.firstOrNull()
                if (product != null) {
                    billingClient.purchases.purchaseProduct(
                        productId = product.productId,
                        orderId = UUID.randomUUID().toString(),
                        quantity = 1,
                        developerPayload = null
                    ).addOnSuccessListener { paymentResult ->
                        when (paymentResult) {
                            is PaymentResult.Success -> {
                                Log.d("BillingClient", "Покупка завершена: ${paymentResult.purchaseId}")
                                if (productId == "three_card_subscription") {
                                    hasThreeCardSubscription = true
                                    threeCardSubscriptionEndDate = "2025-01-01" // Тестовая дата
                                } else if (productId == "premium_monthly_subscription") {
                                    hasPremiumSubscription = true
                                    premiumSubscriptionEndDate = "2025-01-01" // Тестовая дата
                                }
                                saveSubscriptions()
                            }
                            is PaymentResult.Cancelled -> {
                                Log.e("BillingClient", "Покупка отменена пользователем")
                            }
                            is PaymentResult.Failure -> {
                                Log.e("BillingClient", "Ошибка покупки: ${paymentResult.errorCode}")
                            }
                            else -> {
                                Log.e("BillingClient", "Неизвестный результат покупки")
                            }
                        }
                    }.addOnFailureListener { throwable ->
                        Log.e("BillingClient", "Ошибка оформления подписки: ${throwable.message}")
                    }
                } else {
                    Log.e("BillingClient", "Продукт не найден")
                }
            }.addOnFailureListener { throwable ->
                Log.e("BillingClient", "Ошибка получения продукта: ${throwable.message}")
            }
    }

    Scaffold(
        topBar = {
            TabRow(selectedTabIndex = 0) {
                Tab(selected = true, onClick = { navController.navigate("subscription") }) {
                    Text("Подписки")
                }
                Tab(selected = false, onClick = { navController.navigate("tarot") }) {
                    Text("Таро")
                }
                Tab(selected = false, onClick = { navController.navigate("history") }) {
                    Text("История")
                }
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
                        threeCardSubscriptionEndDate = threeCardSubscriptionEndDate,
                        premiumSubscriptionEndDate = premiumSubscriptionEndDate,
                        onThreeCardSubscribe = { purchaseSubscription("three_card_subscription") },
                        onPremiumSubscribe = { purchaseSubscription("premium_monthly_subscription") },
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
                composable("history") {
                    HistoryScreen(
                        isSubscribed = hasThreeCardSubscription || hasPremiumSubscription
                    )
                }
            }
        }
    )
}
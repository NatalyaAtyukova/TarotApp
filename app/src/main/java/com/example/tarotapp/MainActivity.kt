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
import ru.rustore.sdk.billingclient.model.purchase.PurchaseState
import com.example.tarotapp.components.SubscriptionScreen
import com.example.tarotapp.components.TarotScreens
import org.json.JSONObject
import java.util.*

class MainActivity : ComponentActivity() {
    private lateinit var billingClient: RuStoreBillingClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        billingClient = RuStoreBillingClientFactory.create(
            context = applicationContext,
            consoleApplicationId = "YOUR_CONSOLE_APP_ID",
            deeplinkScheme = "yourappscheme"
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

    var hasBasicSubscription by remember {
        mutableStateOf(sharedPreferences.getBoolean("hasBasicSubscription", false))
    }
    var hasPremiumSubscription by remember {
        mutableStateOf(sharedPreferences.getBoolean("hasPremiumSubscription", false))
    }
    var basicSubscriptionEndDate by remember {
        mutableStateOf(sharedPreferences.getString("basicSubscriptionEndDate", null))
    }
    var premiumSubscriptionEndDate by remember {
        mutableStateOf(sharedPreferences.getString("premiumSubscriptionEndDate", null))
    }

    fun saveSubscriptions() {
        sharedPreferences.edit().apply {
            putBoolean("hasBasicSubscription", hasBasicSubscription)
            putBoolean("hasPremiumSubscription", hasPremiumSubscription)
            apply()
        }
    }

    fun saveSubscriptionDates(basicDate: String?, premiumDate: String?) {
        sharedPreferences.edit().apply {
            putString("basicSubscriptionEndDate", basicDate)
            putString("premiumSubscriptionEndDate", premiumDate)
            apply()
        }
    }

    fun extractEndDateFromSubscriptionToken(subscriptionToken: String): String? {
        return try {
            val jsonObject = JSONObject(subscriptionToken)
            jsonObject.getString("endDate") // Предполагается, что токен содержит дату окончания
        } catch (e: Exception) {
            Log.e("SubscriptionToken", "Ошибка обработки токена: ${e.message}")
            null
        }
    }

    fun fetchSubscriptionInfo(purchaseId: String, productId: String) {
        billingClient.purchases.getPurchaseInfo(purchaseId)
            .addOnSuccessListener { purchase ->
                val endDate = purchase.subscriptionToken?.let {
                    extractEndDateFromSubscriptionToken(it)
                }
                when (productId) {
                    "basic_subscription_id" -> {
                        hasBasicSubscription = true
                        basicSubscriptionEndDate = endDate
                        hasPremiumSubscription = false
                        premiumSubscriptionEndDate = null
                    }
                    "premium_subscription_id" -> {
                        hasPremiumSubscription = true
                        premiumSubscriptionEndDate = endDate
                        hasBasicSubscription = false
                        basicSubscriptionEndDate = null
                    }
                }
                saveSubscriptions()
                saveSubscriptionDates(basicSubscriptionEndDate, premiumSubscriptionEndDate)
            }
            .addOnFailureListener { throwable ->
                Log.e("BillingClient", "Ошибка получения информации о подписке", throwable)
            }
    }

    fun purchaseSubscription(productId: String) {
        billingClient.products.getProducts(listOf(productId))
            .addOnSuccessListener { products ->
                val product = products.firstOrNull()
                if (product == null) {
                    Log.e("BillingClient", "Продукт с ID $productId не найден")
                    return@addOnSuccessListener
                }

                billingClient.purchases.purchaseProduct(
                    productId = product.productId,
                    orderId = UUID.randomUUID().toString(),
                    quantity = 1,
                    developerPayload = null
                ).addOnSuccessListener { paymentResult ->
                    when (paymentResult) {
                        is ru.rustore.sdk.billingclient.model.purchase.PaymentResult.Success -> {
                            fetchSubscriptionInfo(
                                purchaseId = paymentResult.purchaseId,
                                productId = productId
                            )
                        }
                        is ru.rustore.sdk.billingclient.model.purchase.PaymentResult.Cancelled -> {
                            Log.e("BillingClient", "Покупка отменена пользователем")
                        }
                        is ru.rustore.sdk.billingclient.model.purchase.PaymentResult.Failure -> {
                            Log.e("BillingClient", "Ошибка покупки: ${paymentResult.errorCode}")
                        }
                        is ru.rustore.sdk.billingclient.model.purchase.PaymentResult.InvalidPaymentState -> {
                            Log.e("BillingClient", "Некорректное состояние оплаты")
                        }
                        else -> {
                            Log.e("BillingClient", "Неизвестный результат покупки")
                        }
                    }
                }.addOnFailureListener { throwable ->
                    Log.e("BillingClient", "Ошибка оформления подписки", throwable)
                }
            }
            .addOnFailureListener { throwable ->
                Log.e("BillingClient", "Ошибка получения продукта", throwable)
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
                        hasBasicSubscription = hasBasicSubscription,
                        hasPremiumSubscription = hasPremiumSubscription,
                        basicSubscriptionEndDate = basicSubscriptionEndDate,
                        premiumSubscriptionEndDate = premiumSubscriptionEndDate,
                        onBasicSubscribe = { isActive ->
                            if (isActive) {
                                purchaseSubscription("basic_subscription_id")
                            } else {
                                hasBasicSubscription = false
                                basicSubscriptionEndDate = null
                                saveSubscriptions()
                                saveSubscriptionDates(basicSubscriptionEndDate, premiumSubscriptionEndDate)
                            }
                        },
                        onPremiumSubscribe = { isActive ->
                            if (isActive) {
                                purchaseSubscription("premium_subscription_id")
                            } else {
                                hasPremiumSubscription = false
                                premiumSubscriptionEndDate = null
                                saveSubscriptions()
                                saveSubscriptionDates(basicSubscriptionEndDate, premiumSubscriptionEndDate)
                            }
                        },
                        onNavigateToTarotScreens = { navController.navigate("tarot") }
                    )
                }
                composable("tarot") {
                    TarotScreens(
                        navigateToSingleCard = { navController.navigate("single") },
                        navigateToThreeCards = {
                            if (hasBasicSubscription || hasPremiumSubscription) {
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
                        hasBasicSubscription = hasBasicSubscription,
                        hasPremiumSubscription = hasPremiumSubscription
                    )
                }
            }
        }
    )
}
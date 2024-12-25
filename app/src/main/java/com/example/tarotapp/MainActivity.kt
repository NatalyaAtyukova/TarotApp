package com.example.tarotapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tarotapp.components.HistoryScreen
import com.example.tarotapp.components.MultiCardScreen
import com.example.tarotapp.components.SingleCardScreen
import ru.rustore.sdk.billingclient.RuStoreBillingClient
import ru.rustore.sdk.billingclient.RuStoreBillingClientFactory
import ru.rustore.sdk.billingclient.model.purchase.PurchaseState
import java.util.UUID

class MainActivity : ComponentActivity() {
    private lateinit var billingClient: RuStoreBillingClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация RuStoreBillingClient
        billingClient = RuStoreBillingClientFactory.create(
            context = this,
            consoleApplicationId = "ваш_consoleApplicationId", // Замените на ваш consoleApplicationId
            deeplinkScheme = "yourappscheme", // Замените на вашу схему deeplink
            debugLogs = true
        )

        setContent {
            TarotApp(billingClient)
        }
    }
}

@Composable
fun TarotApp(billingClient: RuStoreBillingClient) {
    var screen by remember { mutableStateOf("single") }
    var isSubscribed by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Проверка подписки при загрузке
    LaunchedEffect(Unit) {
        billingClient.purchases.getPurchases()
            .addOnSuccessListener { purchases ->
                isSubscribed = purchases.any { purchase ->
                    purchase.productId == "three_card_subscription" &&
                            purchase.purchaseState == PurchaseState.CONFIRMED
                }
            }
            .addOnFailureListener { throwable ->
                // Обработка ошибок
                throwable.printStackTrace()
            }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Гадание на картах Таро",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { screen = "single" }) { Text("Одна карта") }
                Button(onClick = { screen = "three" }) { Text("Три карты") }
                Button(onClick = { screen = "history" }) { Text("История") }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Управление подпиской
            Button(
                onClick = {
                    if (!isSubscribed) {
                        // Покупка подписки
                        billingClient.purchases.purchaseProduct(
                            productId = "three_card_subscription",
                            orderId = UUID.randomUUID().toString()
                        ).addOnSuccessListener { result ->
                            when (result) {
                                is ru.rustore.sdk.billingclient.model.purchase.PaymentResult.Success -> {
                                    isSubscribed = true
                                }
                                is ru.rustore.sdk.billingclient.model.purchase.PaymentResult.Cancelled -> {
                                    // Пользователь отменил покупку
                                }
                                is ru.rustore.sdk.billingclient.model.purchase.PaymentResult.Failure -> {
                                    // Ошибка при покупке
                                }
                                ru.rustore.sdk.billingclient.model.purchase.PaymentResult.InvalidPaymentState -> {
                                    // Обработка состояния InvalidPaymentState
                                    // Это состояние возникает при ошибке в работе SDK платежей.
                                }
                            }
                        }.addOnFailureListener { throwable ->
                            // Обработка ошибок
                            throwable.printStackTrace()
                        }
                    } else {
                        // Отмена подписки (логика для вашего приложения)
                        isSubscribed = false
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isSubscribed) "Отключить подписку" else "Активировать подписку")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Навигация между экранами
            when (screen) {
                "single" -> SingleCardScreen(isSubscribed = isSubscribed)
                "three" -> MultiCardScreen(numCards = 3, isSubscribed = isSubscribed)
                "history" -> HistoryScreen(isSubscribed = isSubscribed)
            }
        }
    }
}
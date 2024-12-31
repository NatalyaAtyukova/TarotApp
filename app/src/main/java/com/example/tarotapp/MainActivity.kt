package com.example.tarotapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tarotapp.components.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()
        }
    }
}

@Composable
fun MainApp() {
    val context = LocalContext.current
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("subscriptions", Context.MODE_PRIVATE)

    val navController = rememberNavController()

    // Переменные состояния подписок
    var selectedTab by remember { mutableStateOf(0) }
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

    // Функция сохранения подписок
    fun saveSubscriptions() {
        sharedPreferences.edit().apply {
            putBoolean("hasBasicSubscription", hasBasicSubscription)
            putBoolean("hasPremiumSubscription", hasPremiumSubscription)
            putString("basicSubscriptionEndDate", basicSubscriptionEndDate)
            putString("premiumSubscriptionEndDate", premiumSubscriptionEndDate)
            apply()
        }
    }

    Scaffold(
        topBar = {
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = {
                        selectedTab = 0
                        navController.navigate("subscription") {
                            popUpTo("subscription") { inclusive = true }
                        }
                    },
                    text = { Text("Подписки") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                        navController.navigate("tarot") {
                            popUpTo("tarot") { inclusive = true }
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
                // Экран подписок
                composable("subscription") {
                    SubscriptionScreen(
                        hasBasicSubscription = hasBasicSubscription,
                        hasPremiumSubscription = hasPremiumSubscription,
                        basicSubscriptionEndDate = basicSubscriptionEndDate,
                        premiumSubscriptionEndDate = premiumSubscriptionEndDate,
                        onBasicSubscribe = { isSubscribed ->
                            hasBasicSubscription = isSubscribed
                            basicSubscriptionEndDate = if (isSubscribed) "2024-12-31" else null

                            if (hasPremiumSubscription && isSubscribed) {
                                hasPremiumSubscription = false
                                premiumSubscriptionEndDate = null
                            }

                            saveSubscriptions()
                        },
                        onPremiumSubscribe = { isSubscribed ->
                            hasPremiumSubscription = isSubscribed
                            premiumSubscriptionEndDate = if (isSubscribed) "2025-12-31" else null

                            if (isSubscribed) {
                                hasBasicSubscription = false
                                basicSubscriptionEndDate = null
                            }

                            saveSubscriptions()
                        },
                        onNavigateToTarotScreens = {
                            selectedTab = 1
                            navController.navigate("tarot")
                        }
                    )
                }

                // Экран Таро
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

                // Экран одной карты
                composable("single") {
                    SingleCardScreen(isSubscribed = true)
                }

                // Экран трёх карт
                composable("three") {
                    MultiCardScreen(numCards = 3, isSubscribed = hasBasicSubscription || hasPremiumSubscription)
                }

                // Экран пяти карт
                composable("five") {
                    PremiumTarotScreen(numCards = 5, isSubscribed = hasPremiumSubscription)
                }

                // Экран десяти карт
                composable("ten") {
                    PremiumTarotScreen(numCards = 10, isSubscribed = hasPremiumSubscription)
                }

                // Экран истории
                composable("history") {
                    HistoryScreen(isSubscribed = true)
                }
            }
        }
    )
}
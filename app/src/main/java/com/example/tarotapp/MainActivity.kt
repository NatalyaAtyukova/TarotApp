package com.example.tarotapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
    val navController = rememberNavController()
    var selectedTab by remember { mutableStateOf(0) }
    var hasBasicSubscription by remember { mutableStateOf(false) }
    var hasPremiumSubscription by remember { mutableStateOf(false) }
    var basicSubscriptionEndDate by remember { mutableStateOf<String?>(null) }
    var premiumSubscriptionEndDate by remember { mutableStateOf<String?>(null) }

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
                composable("subscription") {
                    SubscriptionScreen(
                        hasBasicSubscription = hasBasicSubscription,
                        hasPremiumSubscription = hasPremiumSubscription,
                        basicSubscriptionEndDate = basicSubscriptionEndDate,
                        premiumSubscriptionEndDate = premiumSubscriptionEndDate,
                        onBasicSubscribe = { isSubscribed ->
                            hasBasicSubscription = isSubscribed
                            if (isSubscribed) {
                                basicSubscriptionEndDate = "2024-12-31" // Пример даты
                            } else {
                                basicSubscriptionEndDate = null
                            }
                        },
                        onPremiumSubscribe = { isSubscribed ->
                            hasPremiumSubscription = isSubscribed
                            if (isSubscribed) {
                                premiumSubscriptionEndDate = "2025-12-31" // Пример даты
                            } else {
                                premiumSubscriptionEndDate = null
                            }
                        },
                        onNavigateToTarotScreens = {
                            selectedTab = 1
                            navController.navigate("tarot")
                        }
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
                composable("single") { SingleCardScreen(isSubscribed = true) }
                composable("three") {
                    MultiCardScreen(numCards = 3, isSubscribed = hasBasicSubscription || hasPremiumSubscription)
                }
                composable("five") {
                    PremiumTarotScreen(numCards = 5, isSubscribed = hasPremiumSubscription)
                }
                composable("ten") {
                    PremiumTarotScreen(numCards = 10, isSubscribed = hasPremiumSubscription)
                }
                composable("history") { HistoryScreen(isSubscribed = true) }
            }
        }
    )
}
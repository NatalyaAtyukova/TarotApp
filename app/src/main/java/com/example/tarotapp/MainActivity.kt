package com.example.tarotapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tarotapp.components.*
import com.example.tarotapp.ui.theme.TarotAppTheme
import ru.rustore.sdk.billingclient.RuStoreBillingClient
import ru.rustore.sdk.billingclient.RuStoreBillingClientFactory
import ru.rustore.sdk.billingclient.model.purchase.PaymentResult

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
            TarotAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp(billingClient)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        billingClient.onNewIntent(intent)
    }
}

sealed class Screen(val route: String, val icon: @Composable () -> Unit, val label: String) {
    object Home : Screen(
        "tarot",
        { Icon(Icons.Filled.Home, contentDescription = "Главная") },
        "Главная"
    )
    object History : Screen(
        "history",
        { Icon(Icons.Filled.History, contentDescription = "История") },
        "История"
    )
    object Subscription : Screen(
        "subscription",
        { Icon(Icons.Filled.Subscriptions, contentDescription = "Подписки") },
        "Подписки"
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainApp(billingClient: RuStoreBillingClient) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("subscriptions", Context.MODE_PRIVATE)

    val navController = rememberNavController()
    val items = listOf(Screen.Home, Screen.History, Screen.Subscription)

    var currentSubscription by remember { mutableStateOf<ProductSubscription?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Проверка состояния подписок при запуске приложения
    LaunchedEffect(Unit) {
        updateSubscriptionsStatus(billingClient, sharedPreferences) { subscription ->
            currentSubscription = subscription
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                items.forEach { screen ->
                    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                    
                    NavigationBarItem(
                        icon = { 
                            AnimatedContent(
                                targetState = selected,
                                transitionSpec = {
                                    fadeIn(animationSpec = tween(150, 150)).togetherWith(
                                    fadeOut(animationSpec = tween(150)))
                                }
                            ) { isSelected ->
                                Box(
                                    modifier = Modifier.size(24.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    screen.icon()
                                }
                            }
                        },
                        label = { 
                            Text(
                                text = screen.label,
                                style = MaterialTheme.typography.labelMedium
                            ) 
                        },
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "tarot",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(
                "tarot",
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) {
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
            
            composable(
                "single",
                enterTransition = { slideInHorizontally() + fadeIn() },
                exitTransition = { slideOutHorizontally() + fadeOut() },
                popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn() },
                popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() }
            ) {
                SingleCardScreen(
                    isSubscribed = currentSubscription != null,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            
            composable(
                "three",
                enterTransition = { slideInHorizontally() + fadeIn() },
                exitTransition = { slideOutHorizontally() + fadeOut() },
                popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn() },
                popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() }
            ) {
                MultiCardScreen(
                    numCards = 3, 
                    isSubscribed = currentSubscription != null,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            
            composable(
                "five",
                enterTransition = { slideInHorizontally() + fadeIn() },
                exitTransition = { slideOutHorizontally() + fadeOut() },
                popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn() },
                popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() }
            ) {
                PremiumTarotScreen(
                    numCards = 5, 
                    isSubscribed = currentSubscription != null,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            
            composable(
                "ten",
                enterTransition = { slideInHorizontally() + fadeIn() },
                exitTransition = { slideOutHorizontally() + fadeOut() },
                popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn() },
                popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() }
            ) {
                PremiumTarotScreen(
                    numCards = 10, 
                    isSubscribed = currentSubscription != null,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            
            composable(
                "history",
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) {
                HistoryScreen(isSubscribed = currentSubscription != null)
            }
            
            composable(
                "subscription",
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) {
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
        }
    }
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

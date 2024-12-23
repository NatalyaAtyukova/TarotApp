package com.example.tarotapp

import androidx.compose.ui.unit.sp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tarotapp.components.MultiCardScreen
import com.example.tarotapp.components.SingleCardScreen
import com.example.tarotapp.components.HistoryScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TarotApp()
        }
    }
}

@Composable
fun TarotApp() {
    var screen by remember { mutableStateOf("single") }

    // Статус подписки: заменить на реальную проверку (например, SharedPreferences)
    var isSubscribed by remember { mutableStateOf(false) }

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

            // Экран для управления подпиской
            Button(
                onClick = { isSubscribed = !isSubscribed },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isSubscribed) "Отключить подписку" else "Активировать подписку")
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (screen) {
                "single" -> SingleCardScreen(isSubscribed = isSubscribed)
                "three" -> MultiCardScreen(numCards = 3, isSubscribed = isSubscribed)
                "history" -> HistoryScreen(isSubscribed = isSubscribed)
            }
        }
    }
}
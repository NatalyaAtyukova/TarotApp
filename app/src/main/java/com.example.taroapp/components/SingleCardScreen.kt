package com.example.tarotapp.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tarotapp.TarotCard
import com.example.tarotapp.tarotCards
import com.example.tarotapp.components.loadImageFromAssets
import kotlin.random.Random

@Composable
fun SingleCardScreen(isSubscribed: Boolean) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("tarot_prefs", Context.MODE_PRIVATE)

    // Загружаем количество оставшихся попыток из SharedPreferences
    var selectedCard by remember { mutableStateOf<TarotCard?>(null) }
    var remainingAttempts by remember {
        mutableStateOf(sharedPreferences.getInt("remaining_attempts", 3)) // Дефолтное значение — 3
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Отображение выбранной карты или подсказки
        if (selectedCard == null) {
            Text("Выберите карту Таро", fontSize = 24.sp)
        } else {
            val imageBitmap = loadImageFromAssets(context, selectedCard!!.imagePath)
            imageBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = selectedCard!!.name,
                    modifier = Modifier.size(200.dp)
                )
            }
            Text(selectedCard!!.name, fontSize = 20.sp, modifier = Modifier.padding(top = 8.dp))
            Text(selectedCard!!.description, fontSize = 16.sp, modifier = Modifier.padding(top = 4.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Кнопка вытягивания карты
        Button(
            onClick = {
                if (isSubscribed || remainingAttempts > 0) {
                    selectedCard = tarotCards[Random.nextInt(tarotCards.size)]
                    if (!isSubscribed) {
                        remainingAttempts--
                        sharedPreferences.edit().putInt("remaining_attempts", remainingAttempts).apply() // Сохраняем обновленное значение
                    }
                } else {
                    Toast.makeText(context, "Лимит гаданий на сегодня исчерпан!", Toast.LENGTH_SHORT).show()
                }
            },
            enabled = isSubscribed || remainingAttempts > 0 // Кнопка отключается, если попыток больше нет
        ) {
            Text("Вытянуть карту")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Информация о количестве оставшихся попыток
        if (!isSubscribed) {
            Text("Осталось гаданий: $remainingAttempts", fontSize = 14.sp)
        }
    }
}
package com.example.tarotapp

import android.content.Context
import android.graphics.BitmapFactory
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.InputStream
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TarotApp() // Запуск основного UI
        }
    }
}

@Composable
fun TarotApp() {
    val tarotCards = listOf(
        Pair("cards/00.jpg", "Шут: Новые начинания, приключения."),
        Pair("cards/01.jpg", "Маг: Возможности, потенциал."),
        Pair("cards/02.jpg", "Верховная Жрица: Интуиция, тайны.")
    )

    var selectedCard by remember { mutableStateOf<Pair<String, String>?>(null) }
    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Проверка на выбранную карту
            if (selectedCard == null) {
                Text(
                    text = "Выберите карту Таро",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            } else {
                // Загружаем изображение из assets
                val imageBitmap = loadImageFromAssets(context, selectedCard!!.first)
                if (imageBitmap != null) {
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = "Tarot Card",
                        modifier = Modifier
                            .size(200.dp)
                            .padding(16.dp),
                        contentScale = ContentScale.Fit
                    )
                } else {
                    Text(
                        text = "Ошибка загрузки изображения!",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                // Текст значения карты
                Text(
                    text = selectedCard!!.second,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Кнопка для вытягивания карты
            Button(
                onClick = {
                    val randomIndex = Random.nextInt(tarotCards.size)
                    selectedCard = tarotCards[randomIndex]
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Вытянуть карту")
            }
        }
    }
}

// Функция для загрузки изображения из папки assets
fun loadImageFromAssets(context: Context, fileName: String): ImageBitmap? {
    return try {
        val inputStream: InputStream = context.assets.open(fileName)
        BitmapFactory.decodeStream(inputStream)?.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
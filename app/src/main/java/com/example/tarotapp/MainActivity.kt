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
        // Старшие Арканы (22 карты)
        Pair("cards/00.jpg", "Шут: Новые начинания, приключения."),
        Pair("cards/01.jpg", "Маг: Возможности, потенциал."),
        Pair("cards/02.jpg", "Верховная Жрица: Интуиция, тайны."),
        Pair("cards/03.jpg", "Императрица: Изобилие, творчество."),
        Pair("cards/04.jpg", "Император: Структура, стабильность."),
        Pair("cards/05.jpg", "Иерофант: Традиции, мудрость."),
        Pair("cards/06.jpg", "Влюблённые: Выбор, отношения."),
        Pair("cards/07.jpg", "Колесница: Решительность, движение вперёд."),
        Pair("cards/08.jpg", "Сила: Внутренняя энергия, храбрость."),
        Pair("cards/09.jpg", "Отшельник: Самоанализ, уединение."),
        Pair("cards/10.jpg", "Колесо Фортуны: Судьба, изменения."),
        Pair("cards/11.jpg", "Справедливость: Баланс, честность."),
        Pair("cards/12.jpg", "Повешенный: Жертвенность, новый взгляд."),
        Pair("cards/13.jpg", "Смерть: Конец и начало, трансформация."),
        Pair("cards/14.jpg", "Умеренность: Гармония, баланс."),
        Pair("cards/15.jpg", "Дьявол: Привязанности, искушение."),
        Pair("cards/16.jpg", "Башня: Кризис, разрушение старого."),
        Pair("cards/17.jpg", "Звезда: Надежда, вдохновение."),
        Pair("cards/18.jpg", "Луна: Иллюзии, неясность."),
        Pair("cards/19.jpg", "Солнце: Радость, успех."),
        Pair("cards/20.jpg", "Суд: Возрождение, призыв к действию."),
        Pair("cards/21.jpg", "Мир: Завершение, достижение цели."),

        // Жезлы (14 карт)
        Pair("cards/22.jpg", "Туз Жезлов: Вдохновение, потенциал."),
        Pair("cards/23.jpg", "Двойка Жезлов: Планирование, перспективы."),
        Pair("cards/24.jpg", "Тройка Жезлов: Дальновидность, расширение."),
        Pair("cards/25.jpg", "Четвёрка Жезлов: Празднование, стабильность."),
        Pair("cards/26.jpg", "Пятёрка Жезлов: Конфликт, борьба."),
        Pair("cards/27.jpg", "Шестёрка Жезлов: Победа, признание."),
        Pair("cards/28.jpg", "Семёрка Жезлов: Защита, стойкость."),
        Pair("cards/29.jpg", "Восьмёрка Жезлов: Скорость, прогресс."),
        Pair("cards/30.jpg", "Девятка Жезлов: Упорство, выносливость."),
        Pair("cards/31.jpg", "Десятка Жезлов: Бремя, ответственность."),
        Pair("cards/32.jpg", "Паж Жезлов: Любопытство, исследование."),
        Pair("cards/33.jpg", "Рыцарь Жезлов: Энергия, путешествие."),
        Pair("cards/34.jpg", "Королева Жезлов: Уверенность, страсть."),
        Pair("cards/35.jpg", "Король Жезлов: Лидерство, визионер."),

        // Кубки (14 карт)
        Pair("cards/36.jpg", "Туз Кубков: Любовь, новые эмоции."),
        Pair("cards/37.jpg", "Двойка Кубков: Союз, партнёрство."),
        Pair("cards/38.jpg", "Тройка Кубков: Радость, празднование."),
        Pair("cards/39.jpg", "Четвёрка Кубков: Скука, неудовлетворённость."),
        Pair("cards/40.jpg", "Пятёрка Кубков: Потеря, разочарование."),
        Pair("cards/41.jpg", "Шестёрка Кубков: Воспоминания, ностальгия."),
        Pair("cards/42.jpg", "Семёрка Кубков: Иллюзии, выбор."),
        Pair("cards/43.jpg", "Восьмёрка Кубков: Уход, поиск себя."),
        Pair("cards/44.jpg", "Девятка Кубков: Исполнение желаний."),
        Pair("cards/45.jpg", "Десятка Кубков: Счастье, семья."),
        Pair("cards/46.jpg", "Паж Кубков: Воображение, интуиция."),
        Pair("cards/47.jpg", "Рыцарь Кубков: Романтика, предложение."),
        Pair("cards/48.jpg", "Королева Кубков: Сострадание, забота."),
        Pair("cards/49.jpg", "Король Кубков: Эмоциональная стабильность."),

        // Мечи (14 карт)
        Pair("cards/50.jpg", "Туз Мечей: Ясность, новые идеи."),
        Pair("cards/51.jpg", "Двойка Мечей: Нерешительность, баланс."),
        Pair("cards/52.jpg", "Тройка Мечей: Боль, разочарование."),
        Pair("cards/53.jpg", "Четвёрка Мечей: Отдых, восстановление."),
        Pair("cards/54.jpg", "Пятёрка Мечей: Конфликт, поражение."),
        Pair("cards/55.jpg", "Шестёрка Мечей: Переход, перемены."),
        Pair("cards/56.jpg", "Семёрка Мечей: Обман, стратегия."),
        Pair("cards/57.jpg", "Восьмёрка Мечей: Ограничения, страх."),
        Pair("cards/58.jpg", "Девятка Мечей: Тревога, беспокойство."),
        Pair("cards/59.jpg", "Десятка Мечей: Конец, завершение."),
        Pair("cards/60.jpg", "Паж Мечей: Любознательность, наблюдение."),
        Pair("cards/61.jpg", "Рыцарь Мечей: Решительность, стремительность."),
        Pair("cards/62.jpg", "Королева Мечей: Независимость, ясность."),
        Pair("cards/63.jpg", "Король Мечей: Логика, авторитет."),

        // Пентакли (14 карт)
        Pair("cards/64.jpg", "Туз Пентаклей: Процветание, новые возможности."),
        Pair("cards/65.jpg", "Двойка Пентаклей: Баланс, адаптация."),
        Pair("cards/66.jpg", "Тройка Пентаклей: Командная работа, мастерство."),
        Pair("cards/67.jpg", "Четвёрка Пентаклей: Стабильность, контроль."),
        Pair("cards/68.jpg", "Пятёрка Пентаклей: Бедность, трудности."),
        Pair("cards/69.jpg", "Шестёрка Пентаклей: Щедрость, поддержка."),
        Pair("cards/70.jpg", "Семёрка Пентаклей: Терпение, инвестиции."),
        Pair("cards/71.jpg", "Восьмёрка Пентаклей: Трудолюбие, мастерство."),
        Pair("cards/72.jpg", "Девятка Пентаклей: Самодостаточность, успех."),
        Pair("cards/73.jpg", "Десятка Пентаклей: Богатство, наследие."),
        Pair("cards/74.jpg", "Паж Пентаклей: Учёба, возможности."),
        Pair("cards/75.jpg", "Рыцарь Пентаклей: Упорство, ответственность."),
        Pair("cards/76.jpg", "Королева Пентаклей: Практичность, забота."),
        Pair("cards/77.jpg", "Король Пентаклей: Успех, стабильность.")
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
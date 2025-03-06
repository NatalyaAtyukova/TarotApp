package com.example.tarotapp

data class TarotCard(
    val name: String,         // Название карты
    val description: String,  // Основное значение карты
    val situation: String,    // Значение в ситуации
    val imagePath: String,    // Путь к изображению карты
    val keywords: List<String>, // Ключевые слова
    val reversedMeaning: String, // Значение в перевернутом положении
    val advice: String,       // Совет карты
    val element: String,      // Стихия карты
    val planet: String? = null // Планета (если применимо)
)
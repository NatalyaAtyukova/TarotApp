package com.example.tarotapp

data class TarotCard(
    val name: String,         // Название карты
    val description: String,  // Основное значение карты
    val situation: String,    // Значение в ситуации
    val imagePath: String     // Путь к изображению карты
)
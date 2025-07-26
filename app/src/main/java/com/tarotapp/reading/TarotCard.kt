package com.tarotapp.reading

data class TarotCard(
    val name: String,
    val description: String,
    val situation: String,
    val advice: String,
    val keywords: List<String>,
    val element: String,
    val imagePath: String,
    val planet: String? = null
)
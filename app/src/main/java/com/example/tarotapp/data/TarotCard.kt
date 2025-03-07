package com.example.tarotapp.data

data class TarotCard(
    val name: String,
    val image: String,
    val element: String,
    val description: String,
    val situation: String,
    val keywords: List<String>,
    val advice: String
) 
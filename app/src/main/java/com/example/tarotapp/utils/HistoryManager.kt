package com.example.tarotapp.utils

import android.content.Context
import com.example.tarotapp.TarotCard
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class TarotSpread(
    val date: String,
    val cards: List<String>
)

object HistoryManager {
    private const val PREFS_NAME = "tarot_history"
    private const val KEY_HISTORY = "tarot_spreads"
    private val gson = Gson()

    fun saveTarotSpread(context: Context, cards: List<TarotCard>, date: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val history = loadHistory(context).toMutableList()
        history.add(0, TarotSpread(date, cards.map { it.name }))
        prefs.edit().putString(KEY_HISTORY, gson.toJson(history)).apply()
    }

    fun loadHistory(context: Context): List<TarotSpread> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_HISTORY, "[]")
        val type = object : TypeToken<List<TarotSpread>>() {}.type
        return try {
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun clearHistory(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
} 
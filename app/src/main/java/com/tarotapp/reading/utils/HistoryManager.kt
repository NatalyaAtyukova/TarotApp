package com.tarotapp.reading.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tarotapp.reading.data.SavedSpread
import com.tarotapp.reading.TarotCard

data class TarotSpread(
    val cards: List<String>,
    val date: String
)

object HistoryManager {
    private const val PREFS_NAME = "tarot_history"
    private const val KEY_HISTORY = "tarot_spreads"
    private val gson = Gson()

    fun saveTarotSpread(context: Context, cards: List<TarotCard>, date: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val history = loadHistory(context).toMutableList()
        
        val cardNames = cards.map { it.name }
        val spread = TarotSpread(cardNames, date)
        history.add(spread)
        
        val json = gson.toJson(history)
        prefs.edit().putString(KEY_HISTORY, json).apply()
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
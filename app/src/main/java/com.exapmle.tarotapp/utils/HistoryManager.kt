package com.example.tarotapp.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.tarotapp.TarotCard

data class TarotSpread(val cards: List<String>, val date: String)

object HistoryManager {
    private const val PREF_NAME = "TarotHistory"
    private const val HISTORY_KEY = "history"
    private const val SUBSCRIPTION_KEY = "isSubscribed" // Ключ для подписки
    private const val MAX_HISTORY_FREE = 5 // Максимум записей для бесплатной версии

    // Сохранение нового расклада
    fun saveSpread(context: Context, spread: TarotSpread): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Загружаем текущую историю
        val history = loadHistory(context).toMutableList()

        // Проверяем лимит для бесплатной версии
        if (!isSubscribed(context) && history.size >= MAX_HISTORY_FREE) {
            return false // Если подписки нет, не добавляем больше записей
        }

        // Добавляем новый расклад
        history.add(spread)

        // Сохраняем историю обратно
        val json = Gson().toJson(history)
        editor.putString(HISTORY_KEY, json)
        editor.apply()
        return true
    }

    // Метод для сохранения расклада
    fun saveTarotSpread(context: Context, cards: List<TarotCard>, date: String): Boolean {
        val cardNames = cards.map { it.name } // Проверка, что у TarotCard есть поле name
        val spread = TarotSpread(cardNames, date)
        return saveSpread(context, spread)
    }

    // Загрузка всей истории
    fun loadHistory(context: Context): List<TarotSpread> {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(HISTORY_KEY, null)
        return if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<List<TarotSpread>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }

    // Очистка всей истории
    fun clearHistory(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(HISTORY_KEY)
        editor.apply()
    }

    // Обновление конкретного расклада (по индексу)
    fun updateSpread(context: Context, index: Int, newSpread: TarotSpread) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Загружаем текущую историю
        val history = loadHistory(context).toMutableList()

        // Проверяем валидность индекса
        if (index in history.indices) {
            history[index] = newSpread
        }

        // Сохраняем обновленную историю
        val json = Gson().toJson(history)
        editor.putString(HISTORY_KEY, json)
        editor.apply()
    }

    // Проверка подписки
    fun isSubscribed(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(SUBSCRIPTION_KEY, false)
    }

    // Установка подписки (включить или отключить)
    fun setSubscription(context: Context, subscribed: Boolean) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(SUBSCRIPTION_KEY, subscribed)
        editor.apply()
    }

    // Проверка доступности сохранения для бесплатной версии
    fun canSaveMoreHistory(context: Context): Boolean {
        val history = loadHistory(context)
        return isSubscribed(context) || history.size < MAX_HISTORY_FREE
    }

    // Получение количества оставшихся доступных записей для бесплатной версии
    fun getRemainingHistorySlots(context: Context): Int {
        val history = loadHistory(context)
        return if (isSubscribed(context)) Int.MAX_VALUE else MAX_HISTORY_FREE - history.size
    }
}
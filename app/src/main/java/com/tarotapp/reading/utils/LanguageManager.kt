package com.tarotapp.reading.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import java.util.Locale

object LanguageManager {
    private const val PREFS_NAME = "language_prefs"
    private const val KEY_LANGUAGE = "selected_language"
    
    // Поддерживаемые языки
    enum class Language(val code: String, val displayName: String) {
        RUSSIAN("ru", "Русский"),
        ENGLISH("en", "English"),
        SPANISH("es", "Español"),
        HINDI("hi", "हिन्दी"),
        CHINESE("zh", "中文")
    }
    
    // Получить текущий язык из SharedPreferences
    fun getCurrentLanguage(context: Context): Language {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val languageCode = prefs.getString(KEY_LANGUAGE, null)
        
        return if (languageCode != null) {
            Language.values().find { it.code == languageCode } ?: Language.RUSSIAN
        } else {
            // Если язык не выбран, используем системный
            val systemLocale = context.resources.configuration.locales[0]
            when (systemLocale.language) {
                "en" -> Language.ENGLISH
                "es" -> Language.SPANISH
                "hi" -> Language.HINDI
                "zh" -> Language.CHINESE
                else -> Language.RUSSIAN
            }
        }
    }
    
    // Установить язык
    fun setLanguage(context: Context, language: Language) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_LANGUAGE, language.code).apply()
    }
    
    // Применить язык к контексту
    fun applyLanguage(context: Context, language: Language): Context {
        val locale = Locale(language.code)
        Locale.setDefault(locale)
        
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        
        return context.createConfigurationContext(config)
    }
    
    // Получить все поддерживаемые языки
    fun getSupportedLanguages(): List<Language> = Language.values().toList()
    
    // Проверить, изменился ли язык
    fun hasLanguageChanged(context: Context): Boolean {
        val currentLanguage = getCurrentLanguage(context)
        val systemLocale = context.resources.configuration.locales[0]
        
        return when (currentLanguage) {
            Language.ENGLISH -> systemLocale.language != "en"
            Language.SPANISH -> systemLocale.language != "es"
            Language.HINDI -> systemLocale.language != "hi"
            Language.CHINESE -> systemLocale.language != "zh"
            Language.RUSSIAN -> systemLocale.language != "ru"
        }
    }
} 
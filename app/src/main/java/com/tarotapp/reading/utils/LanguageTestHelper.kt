package com.tarotapp.reading.utils

import android.content.Context
import android.util.Log
import com.tarotapp.reading.getTarotCardsForLanguage

object LanguageTestHelper {
    
    /**
     * Проверяет полноту локализации для всех языков
     */
    fun checkLocalizationCompleteness(context: Context) {
        val supportedLanguages = LanguageManager.getSupportedLanguages()
        
        Log.d("LanguageTest", "=== Проверка мультиязычности ===")
        
        supportedLanguages.forEach { language ->
            Log.d("LanguageTest", "Проверка языка: ${language.displayName} (${language.code})")
            
            // Проверяем основные строки
            val testStrings = listOf(
                "app_name",
                "nav_home", 
                "nav_history",
                "main_title",
                "main_subtitle",
                "spread_single_card",
                "spread_three_cards",
                "btn_back",
                "btn_save",
                "loading",
                "language_settings"
            )
            
            var missingStrings = 0
            testStrings.forEach { stringName ->
                try {
                    val resourceId = context.resources.getIdentifier(stringName, "string", context.packageName)
                    if (resourceId == 0) {
                        Log.w("LanguageTest", "Отсутствует строка: $stringName для языка ${language.code}")
                        missingStrings++
                    }
                } catch (e: Exception) {
                    Log.e("LanguageTest", "Ошибка при проверке строки $stringName для языка ${language.code}: ${e.message}")
                    missingStrings++
                }
            }
            
            if (missingStrings == 0) {
                Log.d("LanguageTest", "✅ Язык ${language.code} полностью локализован")
            } else {
                Log.w("LanguageTest", "⚠️ Язык ${language.code} имеет $missingStrings отсутствующих строк")
            }
        }
        
        Log.d("LanguageTest", "=== Конец проверки ===")
    }
    
    /**
     * Проверяет работу LanguageManager
     */
    fun testLanguageManager(context: Context) {
        Log.d("LanguageTest", "=== Тест LanguageManager ===")
        
        val currentLanguage = LanguageManager.getCurrentLanguage(context)
        Log.d("LanguageTest", "Текущий язык: ${currentLanguage.displayName} (${currentLanguage.code})")
        
        val supportedLanguages = LanguageManager.getSupportedLanguages()
        Log.d("LanguageTest", "Поддерживаемые языки: ${supportedLanguages.size}")
        supportedLanguages.forEach { language ->
            Log.d("LanguageTest", "- ${language.displayName} (${language.code})")
        }
        
        // Тест смены языка
        val testLanguage = LanguageManager.Language.ENGLISH
        LanguageManager.setLanguage(context, testLanguage)
        val newLanguage = LanguageManager.getCurrentLanguage(context)
        Log.d("LanguageTest", "После смены на английский: ${newLanguage.displayName} (${newLanguage.code})")
        
        // Возвращаем исходный язык
        LanguageManager.setLanguage(context, currentLanguage)
        
        Log.d("LanguageTest", "=== Конец теста LanguageManager ===")
    }
    
    /**
     * Проверяет данные карт Таро для всех языков
     */
    fun testTarotCardData(context: Context) {
        Log.d("LanguageTest", "=== Тест данных карт Таро ===")
        
        val supportedLanguages = LanguageManager.getSupportedLanguages()
        
        supportedLanguages.forEach { language ->
            Log.d("LanguageTest", "Проверка карт для языка: ${language.displayName}")
            
            try {
                // Временно устанавливаем язык
                LanguageManager.setLanguage(context, language)
                
                // Получаем карты для текущего языка
                val cards = getTarotCardsForLanguage(context)
                
                if (cards.isNotEmpty()) {
                    Log.d("LanguageTest", "✅ Найдено ${cards.size} карт для языка ${language.code}")
                    
                    // Проверяем первые несколько карт
                    for (card in cards.take(3)) {
                        Log.d("LanguageTest", "- ${card.name}: ${card.description.take(50)}...")
                    }
                } else {
                    Log.w("LanguageTest", "⚠️ Нет карт для языка ${language.code}")
                }
            } catch (e: Exception) {
                Log.e("LanguageTest", "Ошибка при проверке карт для языка ${language.code}: ${e.message}")
            }
        }
        
        Log.d("LanguageTest", "=== Конец теста карт Таро ===")
    }
} 
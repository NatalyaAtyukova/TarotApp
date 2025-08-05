package com.tarotapp.reading

import android.app.Application
import android.content.res.Configuration
import android.util.Log
import com.yandex.mobile.ads.common.MobileAds
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.tarotapp.reading.utils.LanguageManager
import java.util.Locale

class TarotApplication : Application() {

    companion object {
        private val _isSdkInitialized = MutableStateFlow(false)
        val isSdkInitialized = _isSdkInitialized.asStateFlow()
    }

    override fun onCreate() {
        super.onCreate()
        
        // Применяем выбранный язык
        applySelectedLanguage()
        
        Log.d("TarotAppAds", "Initializing Yandex Mobile Ads SDK...")
        MobileAds.initialize(this) {
            Log.d("TarotAppAds", "Yandex Mobile Ads SDK initialized successfully.")
            _isSdkInitialized.value = true
        }
    }
    
    private fun applySelectedLanguage() {
        val selectedLanguage = LanguageManager.getCurrentLanguage(this)
        val locale = Locale(selectedLanguage.code)
        Locale.setDefault(locale)
        
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        createConfigurationContext(config)
        
        Log.d("TarotApp", "Applied language: ${selectedLanguage.code}")
    }
} 
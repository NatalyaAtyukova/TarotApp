package com.example.tarotapp

import android.app.Application
import android.util.Log
import com.yandex.mobile.ads.common.MobileAds
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TarotApplication : Application() {

    companion object {
        private val _isSdkInitialized = MutableStateFlow(false)
        val isSdkInitialized = _isSdkInitialized.asStateFlow()
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("TarotAppAds", "Initializing Yandex Mobile Ads SDK...")
        MobileAds.initialize(this) {
            Log.d("TarotAppAds", "Yandex Mobile Ads SDK initialized successfully.")
            _isSdkInitialized.value = true
        }
    }
} 
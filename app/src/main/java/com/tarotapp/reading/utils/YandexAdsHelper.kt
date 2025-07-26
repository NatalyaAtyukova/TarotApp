package com.tarotapp.reading.utils

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader
import com.yandex.mobile.ads.appopenad.AppOpenAd
import com.yandex.mobile.ads.appopenad.AppOpenAdEventListener
import com.yandex.mobile.ads.appopenad.AppOpenAdLoadListener
import com.yandex.mobile.ads.appopenad.AppOpenAdLoader
import android.util.Log

@Composable
fun YandexBannerAd(
    modifier: Modifier = Modifier,
    adUnitId: String = "R-M-14492209-3"
) {
    Log.d("TarotAppAds", "Creating Banner Ad with ID: $adUnitId")
    AndroidView(
        modifier = modifier,
        factory = { context ->
            BannerAdView(context).apply {
                setAdUnitId(adUnitId)
                setAdSize(BannerAdSize.inlineSize(context, 320, 50))
                val adRequest = AdRequest.Builder().build()
                Log.d("TarotAppAds", "Loading Banner Ad...")
                loadAd(adRequest)
            }
        }
    )
}

fun showYandexInterstitialAd(
    context: Context,
    adUnitId: String,
    onAdClosed: (() -> Unit)? = null
) {
    Log.d("TarotAppAds", "Requesting Interstitial Ad with ID: $adUnitId")
    val interstitialAdLoader = InterstitialAdLoader(context).apply {
        setAdLoadListener(object : InterstitialAdLoadListener {
            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d("TarotAppAds", "Interstitial Ad loaded successfully (ID: $adUnitId)")
                interstitialAd.setAdEventListener(object : InterstitialAdEventListener {
                    override fun onAdShown() {
                        Log.d("TarotAppAds", "Interstitial Ad shown (ID: $adUnitId)")
                    }
                    override fun onAdFailedToShow(adError: AdError) {
                        Log.e("TarotAppAds", "Interstitial Ad failed to show (ID: $adUnitId): ${adError.description}")
                        onAdClosed?.invoke()
                    }
                    override fun onAdDismissed() {
                        Log.d("TarotAppAds", "Interstitial Ad dismissed (ID: $adUnitId)")
                        onAdClosed?.invoke()
                    }
                    override fun onAdClicked() {}
                    override fun onAdImpression(impressionData: ImpressionData?) {}
                })
                
                if (context is Activity) {
                    interstitialAd.show(context)
                } else {
                    onAdClosed?.invoke()
                }
            }

            override fun onAdFailedToLoad(adRequestError: AdRequestError) {
                Log.e("TarotAppAds", "Interstitial Ad failed to load (ID: $adUnitId): ${adRequestError.description}")
                onAdClosed?.invoke()
            }
        })
    }
    val adRequestConfiguration = AdRequestConfiguration.Builder(adUnitId).build()
    interstitialAdLoader.loadAd(adRequestConfiguration)
}

fun showYandexAppOpenAd(
    context: Context,
    adUnitId: String,
    onAdClosed: (() -> Unit)? = null
) {
    Log.d("TarotAppAds", "Requesting App Open Ad with ID: $adUnitId")
    val appOpenAdLoader = AppOpenAdLoader(context).apply {
        setAdLoadListener(object : AppOpenAdLoadListener {
            override fun onAdLoaded(appOpenAd: AppOpenAd) {
                Log.d("TarotAppAds", "App Open Ad loaded successfully (ID: $adUnitId)")
                appOpenAd.setAdEventListener(object : AppOpenAdEventListener {
                    override fun onAdShown() {
                        Log.d("TarotAppAds", "App Open Ad shown (ID: $adUnitId)")
                    }
                    override fun onAdFailedToShow(adError: AdError) {
                        Log.e("TarotAppAds", "App Open Ad failed to show (ID: $adUnitId): ${adError.description}")
                        onAdClosed?.invoke()
                    }
                    override fun onAdDismissed() {
                        Log.d("TarotAppAds", "App Open Ad dismissed (ID: $adUnitId)")
                        onAdClosed?.invoke()
                    }
                    override fun onAdClicked() {}
                    override fun onAdImpression(impressionData: ImpressionData?) {}
                })
                
                if (context is Activity) {
                    appOpenAd.show(context)
                } else {
                    onAdClosed?.invoke()
                }
            }

            override fun onAdFailedToLoad(adRequestError: AdRequestError) {
                Log.e("TarotAppAds", "App Open Ad failed to load (ID: $adUnitId): ${adRequestError.description}")
                onAdClosed?.invoke()
            }
        })
    }
    val adRequestConfiguration = AdRequestConfiguration.Builder(adUnitId).build()
    appOpenAdLoader.loadAd(adRequestConfiguration)
} 
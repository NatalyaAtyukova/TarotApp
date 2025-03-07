package com.example.tarotapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = MysticGold,
    onPrimary = DeepPurple,
    secondary = SoftPurple,
    onSecondary = StardustWhite,
    tertiary = CrystalBlue,
    background = DarkBlue,
    surface = CardBackground,
    onSurface = StardustWhite,
    error = MysticRed,
    onError = StardustWhite
)

private val LightColorScheme = lightColorScheme(
    primary = DeepPurple,
    onPrimary = StardustWhite,
    secondary = SoftPurple,
    onSecondary = DeepPurple,
    tertiary = CrystalBlue,
    background = StardustWhite,
    surface = MoonlightSilver,
    onSurface = DeepPurple,
    error = MysticRed,
    onError = StardustWhite
)

@Composable
fun TarotAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val activity = LocalContext.current as Activity
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            activity.window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(activity.window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
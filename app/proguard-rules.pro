# Сохраняем основной Activity
-keep class com.example.tarotapp.MainActivity { *; }

# Сохраняем все классы в папке components
-keep class com.example.tarotapp.components.** { *; }

# Сохраняем все классы в папке utils
-keep class com.example.tarotapp.utils.** { *; }

# Сохраняем все классы в папке ui.theme
-keep class com.example.tarotapp.ui.theme.** { *; }

# Сохраняем публичные классы и методы Android
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Сохраняем классы Jetpack Compose
-keep class androidx.compose.** { *; }
-keep class androidx.lifecycle.** { *; }

# Сохраняем аннотации
-keepattributes *Annotation*

# Gson: сохраняем классы для сериализации/десериализации
-keep class com.example.tarotapp.** {
    *;
}
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-dontwarn com.google.gson.**

# Убираем предупреждения
-ignorewarnings

# Убираем отладочные логи
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}
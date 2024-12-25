plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.tarotapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tarotapp"
        minSdk = 31
        targetSdk = 34
        versionCode = 2
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("/Volumes/KINGSTON/AndroidStudioProjects/TarotApp/keystore/tarotapp-release-key.jks") // Путь к вашему Keystore
            storePassword = "s7(F4*Clj0" // Пароль для Keystore
            keyAlias = "tarotapp_key_alias" // Alias ключа
            keyPassword = "s7(F4*Clj0" // Пароль ключа
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug" // Добавляет суффикс к ID для debug-версии
            versionNameSuffix = "-debug" // Добавляет суффикс версии для debug-версии
        }
        release {
            isMinifyEnabled = false // Отключаем обфускацию

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release") // Привязываем конфигурацию подписи
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    sourceSets {
        getByName("main") {
            java {
                srcDirs("src/main/java", "src/main/java/com.example.taroapp/components",
                    "src/main/java/utils", "src/main/java/com.exapmle.tarotapp/utils"
                )
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Новые зависимости
    implementation("com.google.code.gson:gson:2.10.1") // Для работы с JSON
    implementation("androidx.compose.foundation:foundation:1.4.0") // LazyColumn и другие layout-компоненты
    implementation("androidx.compose.runtime:runtime:1.7.6") // Для @Composable функций
    implementation(platform("ru.rustore.sdk:bom:7.0.0"))
    implementation("ru.rustore.sdk:billingclient")
}
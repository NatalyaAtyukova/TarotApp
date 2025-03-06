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
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    sourceSets {
        getByName("main") {
            java {
                srcDirs("src/main/java")
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
    implementation("androidx.navigation:navigation-compose:2.6.0-alpha01")
    // Material Design
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.compose.material:material-icons-core:1.5.4")
    implementation("androidx.compose.material:material-icons-extended:1.5.4")
    // Другие зависимости Compose
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.compose.runtime:runtime:1.5.4")
    
    // Coil
    implementation("io.coil-kt:coil:2.5.0")
    implementation("io.coil-kt:coil-compose:2.5.0")
    
    // Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")
    implementation("com.google.accompanist:accompanist-pager:0.32.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.32.0")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.32.0")
    implementation("com.google.accompanist:accompanist-placeholder:0.32.0")
    implementation("com.google.accompanist:accompanist-flowlayout:0.32.0")
}
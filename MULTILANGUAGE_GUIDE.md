# Руководство по мультиязычности в Tarot App

## Обзор

Приложение Tarot поддерживает 5 языков:
- 🇷🇺 **Русский** (основной)
- 🇺🇸 **Английский**
- 🇪🇸 **Испанский**
- 🇮🇳 **Хинди**
- 🇨🇳 **Китайский**

## Архитектура мультиязычности

### 1. Управление языками
- **LanguageManager.kt** - основной класс для управления языками
- **LanguageSettingsScreen.kt** - экран настроек языка
- **LanguageTestHelper.kt** - вспомогательный класс для тестирования

### 2. Строковые ресурсы
Файлы локализации находятся в:
```
app/src/main/res/
├── values/strings.xml          # Русский (основной)
├── values-en/strings.xml       # Английский
├── values-es/strings.xml       # Испанский
├── values-hi/strings.xml       # Хинди
└── values-zh/strings.xml       # Китайский
```

### 3. Данные карт Таро
Данные карт локализованы в:
- **TarotCardData.kt** - русский (основной)
- **TarotCardDataEnglish.kt** - английский
- **TarotCardDataSpanish.kt** - испанский
- **TarotCardDataHindi.kt** - хинди
- **TarotCardDataChinese.kt** - китайский

## Как добавить новый язык

### 1. Создать файл строковых ресурсов
Создайте файл `values-[код_языка]/strings.xml`:
```xml
<resources>
    <string name="app_name">Название приложения</string>
    <!-- Добавьте все необходимые строки -->
</resources>
```

### 2. Добавить данные карт Таро
Создайте файл `TarotCardData[Язык].kt`:
```kotlin
object [Язык]TarotCardDataLoader : TarotCardDataLoader {
    override fun getTarotCards(context: Context): List<TarotCard> = tarotCards
}

val tarotCards = listOf(
    TarotCard(
        id = 0,
        name = "Название карты",
        description = "Описание карты",
        // ... остальные поля
    ),
    // ... остальные карты
)
```

### 3. Обновить LanguageManager
Добавьте новый язык в enum:
```kotlin
enum class Language(val code: String, val displayName: String) {
    // ... существующие языки
    NEW_LANGUAGE("new", "Название языка")
}
```

### 4. Обновить функцию getTarotCardsForLanguage
Добавьте новый case в функцию:
```kotlin
fun getTarotCardsForLanguage(context: Context): List<TarotCard> {
    val locale = context.resources.configuration.locales[0]
    val language = locale.language
    
    return when (language) {
        // ... существующие языки
        "new" -> NewLanguageTarotCardDataLoader.getTarotCards(context)
        else -> RussianTarotCardDataLoader.getTarotCards(context)
    }
}
```

## Как использовать мультиязычность

### 1. Получение строк
```kotlin
// В Compose
Text(text = stringResource(R.string.app_name))

// В обычном коде
context.getString(R.string.app_name)
```

### 2. Смена языка
```kotlin
// Установить язык
LanguageManager.setLanguage(context, LanguageManager.Language.ENGLISH)

// Получить текущий язык
val currentLanguage = LanguageManager.getCurrentLanguage(context)
```

### 3. Получение карт для языка
```kotlin
val cards = getTarotCardsForLanguage(context)
```

## Тестирование мультиязычности

### Автоматические тесты
При запуске приложения автоматически выполняются тесты:
- `LanguageTestHelper.checkLocalizationCompleteness()` - проверка полноты локализации
- `LanguageTestHelper.testLanguageManager()` - тест работы LanguageManager
- `LanguageTestHelper.testTarotCardData()` - тест данных карт

### Ручное тестирование
1. Запустите приложение
2. Перейдите в "Настройки языка"
3. Выберите другой язык
4. Проверьте, что интерфейс изменился
5. Проверьте, что карты Таро отображаются на выбранном языке

## Лучшие практики

### 1. Строковые ресурсы
- ✅ Используйте `stringResource()` в Compose
- ✅ Используйте `context.getString()` в обычном коде
- ❌ Не хардкодите строки в коде

### 2. Форматирование строк
```xml
<string name="welcome_message">Добро пожаловать, %s!</string>
```
```kotlin
context.getString(R.string.welcome_message, userName)
```

### 3. Множественные формы
```xml
<plurals name="cards_count">
    <item quantity="one">%d карта</item>
    <item quantity="few">%d карты</item>
    <item quantity="other">%d карт</item>
</plurals>
```

### 4. Проверка полноты локализации
Всегда проверяйте, что все строки переведены на все языки:
```kotlin
LanguageTestHelper.checkLocalizationCompleteness(context)
```

## Известные проблемы и решения

### 1. Язык не применяется сразу
**Проблема**: После смены языка интерфейс не обновляется
**Решение**: Приложение перезапускается для применения изменений

### 2. Отсутствующие переводы
**Проблема**: Некоторые строки не переведены
**Решение**: Добавьте недостающие строки в файлы локализации

### 3. Проблемы с RTL языками
**Проблема**: Некоторые языки требуют RTL поддержки
**Решение**: Добавьте `android:supportsRtl="true"` в AndroidManifest.xml

## Мониторинг и логи

Логи мультиязычности можно найти по тегу `LanguageTest`:
```bash
adb logcat | grep LanguageTest
```

## Контакты

При возникновении проблем с мультиязычностью:
1. Проверьте логи приложения
2. Убедитесь, что все строки переведены
3. Проверьте, что данные карт доступны для всех языков 
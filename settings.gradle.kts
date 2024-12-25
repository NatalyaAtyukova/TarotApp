pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        maven { url = uri("https://artifactory-external.vkpartner.ru/artifactory/maven") } // Репозиторий RuStore
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://artifactory-external.vkpartner.ru/artifactory/maven") } // Репозиторий RuStore
    }
}

rootProject.name = "TarotApp"
include(":app")
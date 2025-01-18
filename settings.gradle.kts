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
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {

        google()
        mavenCentral()

        maven {
            name = "Weather-Utils"
            credentials {
                username = "khattab-dev"
                password = "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"
            }
            url = uri("https://maven.pkg.github.com/khattab-dev/Weather-App")

        }
    }
}
rootProject.name = "Advansys Task"
include(":app")
include(":core:navigation")
include(":data")
include(":domain")
include(":features:home")
include(":features:search_city")
include(":features:forecast")
include(":core:common")
include(":core:di")
include(":core:common-ui")
include(":weather_formatter")

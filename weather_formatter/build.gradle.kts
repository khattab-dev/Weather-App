

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "unilever.it.org.weather_formatter"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

publishing {
    publications.create<MavenPublication>("myPluginRelease") {
        groupId = "dev.khattab"
        artifactId = "weather-utils"
        version = "1.4"

        artifact("${layout.buildDirectory.get()}/outputs/aar/weather_formatter-release.aar")
    }

    repositories {
        maven {
            name = "Weather-Utils"
            url = uri("https://maven.pkg.github.com/khattab-dev/Weather-App")

            credentials {
                username = "khattab-dev"
                password = "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"
            }
        }
    }
}

    dependencies {

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
    }

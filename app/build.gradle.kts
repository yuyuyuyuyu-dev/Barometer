import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.aboutlibraries)
    id("kotlin-parcelize")
}

// Release metadata injected by CI (see .github/workflows/release.yml).
// All are null for local builds, which then fall back to debug signing and the
// hardcoded version below.
val releaseVersionName: String? = System.getenv("VERSION_NAME")
val releaseKeystoreFile: String? = System.getenv("KEYSTORE_FILE")

// Maps a semantic version name to a strictly increasing Int versionCode.
// minor and patch each occupy 3 digits (0..999); major can go up to 2099
// before exceeding Google Play's 2,100,000,000 limit.
fun versionNameToCode(name: String): Int {
    val parts = name.split(".")
    require(parts.size == 3) { "VERSION_NAME must be MAJOR.MINOR.PATCH, but was: $name" }
    val (major, minor, patch) = parts.map(String::toInt)
    require(minor in 0..999 && patch in 0..999) { "minor/patch must be 0..999, but was: $name" }
    return major * 1_000_000 + minor * 1_000 + patch
}

android {
    namespace = "dev.yuyuyuyuyu.barometer"
    compileSdk = 36

    defaultConfig {
        applicationId = "dev.yuyuyuyuyu.barometer"
        minSdk = 24
        targetSdk = 35
        versionCode = releaseVersionName?.let(::versionNameToCode) ?: 3
        versionName = releaseVersionName ?: "0.5.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        if (releaseKeystoreFile != null) {
            create("release") {
                storeFile = file(releaseKeystoreFile)
                storePassword = System.getenv("KEYSTORE_PASSWORD")
                keyAlias = System.getenv("KEY_ALIAS")
                keyPassword = System.getenv("KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName(
                if (releaseKeystoreFile != null) "release" else "debug"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.yuyuyuyuyu.createtypography)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.mikepenz.aboutlibraries.core)
    implementation(libs.mikepenz.aboutlibraries.compose)
    implementation(libs.yuyuyuyuyu.simpletopappbar)
    implementation(libs.circuit)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.result)
    implementation(libs.timber)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)

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
}

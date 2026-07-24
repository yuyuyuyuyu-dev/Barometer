// Top-level build file where you can add configuration options common to all sub-projects/modules.

// AGP 9 compiles Kotlin itself and pins the Kotlin Gradle plugin to the version it
// bundles. Putting a newer KGP on the buildscript classpath is the only supported way
// to raise it, and it has to match the `kotlin` version the compiler plugins below use.
buildscript {
    dependencies {
        classpath(libs.kotlin.gradle.plugin)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    // Applied here as well as in :app so that the root build scripts are linted too.
    alias(libs.plugins.ktlint)
    alias(libs.plugins.versionCatalogUpdate)
}

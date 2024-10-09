// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.compose) apply false
    kotlin("plugin.serialization") version "2.0.20" apply false
    id("org.jetbrains.kotlin.plugin.parcelize") version "2.1.0-Beta1" apply false
    alias(libs.plugins.secrets.gradle.plugin) apply false
}
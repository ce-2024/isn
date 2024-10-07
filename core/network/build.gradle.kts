plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.instantsystem.android.core.network"
    compileSdk = 35

    defaultConfig {
        minSdk = 28

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        buildConfig = true
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    //ktor
    api(libs.ktor.client.core)
    api(libs.ktor.client.android)
    api(libs.ktor.client.cio)
    api(libs.ktor.serialization.kotlinx.json)
    api(libs.ktor.client.serialization)
    api(libs.ktor.client.content.negotiation)
    api(libs.ktor.client.logging)

    //koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.core.coroutines)
    implementation(libs.koin.android)

    // Koin testing tools
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit4)
    // Koin instrumentation test tools
    androidTestImplementation(libs.koin.test)
    androidTestImplementation(libs.koin.test.junit4)


    // kotlin serialization
    implementation(libs.kotlinx.serialization.json)

    // kotlin coroutine test
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    debugImplementation(libs.kotlinx.coroutines.test)

    // test
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.ktor.client.mock.jvm)
    // instrumentation test
    androidTestImplementation(libs.kotlin.test)
    androidTestImplementation(libs.kotlin.test.junit)

}
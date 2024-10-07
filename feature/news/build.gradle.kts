plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization")
}

android {
    namespace = "com.instantsystem.android.feature.news"
    compileSdk = 35

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner =
            "com.instantsystem.android.feature.news.test.runner.NewsAppInstrumentationTestRunner"
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":core:designsystem"))
    implementation(project(":core:network"))
    implementation(project(":core:common"))

    // android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    //koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.core.coroutines)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.compose.navigation)

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
    androidTestImplementation(libs.androidx.test.runner)

    //compose test
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //paging
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    // coil
    implementation(libs.coil)
    implementation(libs.coil.compose)

}
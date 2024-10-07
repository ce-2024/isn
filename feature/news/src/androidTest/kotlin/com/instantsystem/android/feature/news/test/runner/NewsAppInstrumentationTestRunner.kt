package com.instantsystem.android.feature.news.test.runner

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.instantsystem.android.feature.news.debug.app.NewsDebugAppApplication

class NewsAppInstrumentationTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        classLoader: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(classLoader, NewsDebugAppApplication::class.java.name, context)
    }
}
package com.instantsystem.android.feature.news.debug.app

import android.app.Application
import com.instantsystem.android.core.network.di.coreNetworkModule
import com.instantsystem.android.feature.news.debug.di.newsDebugFeatureModules
import com.instantsystem.android.feature.news.di.newsFeatureDataModule
import com.instantsystem.android.feature.news.di.newsFeatureNetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NewsDebugAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            printLogger(Level.DEBUG)
            androidLogger()
            androidContext(applicationContext)
            modules(coreNetworkModule)

            // main news feature modules
            modules(newsFeatureNetworkModule)
            modules(newsFeatureDataModule)

            // news debug feature modules
            modules(newsDebugFeatureModules)
        }
    }
}
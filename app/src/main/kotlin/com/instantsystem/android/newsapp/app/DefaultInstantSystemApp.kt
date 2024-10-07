package com.instantsystem.android.newsapp.app

import android.app.Application
import com.instantsystem.android.core.network.di.coreNetworkModule
import com.instantsystem.android.newsapp.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DefaultInstantSystemApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(coreNetworkModule)
            modules(appModules)
        }
    }
}
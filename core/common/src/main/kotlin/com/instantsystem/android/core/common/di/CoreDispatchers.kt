package com.instantsystem.android.core.common.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module


val commonDispatcherModule = module {
    single(named("IoDispatcher")) { Dispatchers.IO }
    single(named("DefaultDispatcher")) { Dispatchers.Default }
    single(named("MainDispatcher")) { Dispatchers.Main }
}
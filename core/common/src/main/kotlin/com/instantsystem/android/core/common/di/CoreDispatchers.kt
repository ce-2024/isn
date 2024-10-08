package com.instantsystem.android.core.common.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val IO_DISPATCHER = "IoDispatcher"
const val DEFAULT_DISPATCHER = "DefaultDispatcher"
const val MAIN_DISPATCHER = "MainDispatcher"

val commonDispatcherModule = module {
    single(named(IO_DISPATCHER)) { Dispatchers.IO }
    single(named(DEFAULT_DISPATCHER)) { Dispatchers.Default }
    single(named(MAIN_DISPATCHER)) { Dispatchers.Main }
}
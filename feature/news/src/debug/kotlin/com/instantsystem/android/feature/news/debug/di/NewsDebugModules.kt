package com.instantsystem.android.feature.news.debug.di

import com.instantsystem.android.feature.news.domain.interactor.GetTopHeadlinesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.koin.dsl.module

@OptIn(ExperimentalCoroutinesApi::class)
val newsDebugFeatureModules = module {
    factory { GetTopHeadlinesUseCase(get(), UnconfinedTestDispatcher()) }
}
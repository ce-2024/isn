package com.instantsystem.android.feature.news.debug.di

import com.instantsystem.android.feature.news.domain.interactor.GetNewsPagingSource
import com.instantsystem.android.feature.news.domain.interactor.GetTopHeadlinesUseCase
import com.instantsystem.android.feature.news.ui.NewsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

@OptIn(ExperimentalCoroutinesApi::class)
val newsDebugFeatureModules = module {
    factory { GetTopHeadlinesUseCase(get(), UnconfinedTestDispatcher()) }
    factory { GetTopHeadlinesUseCase(get(), UnconfinedTestDispatcher()) }
    factory { GetNewsPagingSource(get()) }
    viewModel { NewsViewModel(get()) }
}
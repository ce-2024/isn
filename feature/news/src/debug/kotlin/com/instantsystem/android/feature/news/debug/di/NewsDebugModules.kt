package com.instantsystem.android.feature.news.debug.di

import androidx.lifecycle.SavedStateHandle
import com.instantsystem.android.feature.news.domain.interactor.GetNewsPagingSource
import com.instantsystem.android.feature.news.domain.interactor.GetTopHeadlinesUseCase
import com.instantsystem.android.feature.news.ui.NewsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Debug module to replace all IoDispatcher by an unconfined dispatcher for debug purpose
 */
@OptIn(ExperimentalCoroutinesApi::class)
val newsDebugFeatureModules = module {

    // replace all IoDispatcher by an unconfined dispatcher for debug purpose
    factory { GetTopHeadlinesUseCase(get(), UnconfinedTestDispatcher()) }
    factory { GetTopHeadlinesUseCase(get(), UnconfinedTestDispatcher()) }

    single { GetNewsPagingSource(get()) }
    viewModel { (savedStateHandle: SavedStateHandle) ->
        NewsViewModel(savedStateHandle, get())
    }
}
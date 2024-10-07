package com.instantsystem.android.feature.news.di

import com.instantsystem.android.feature.news.domain.interactor.GetNewsPagingSource
import com.instantsystem.android.feature.news.domain.interactor.GetTopHeadlinesUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val newsFeatureDomainModule = module {
    factory { GetTopHeadlinesUseCase(get(), get(named("IoDispatcher"))) }
    factory { GetNewsPagingSource(get()) }
}
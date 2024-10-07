package com.instantsystem.android.feature.news.di

import com.instantsystem.android.feature.news.data.api.NewsApiService
import com.instantsystem.android.feature.news.data.api.NewsRemoteApiService
import com.instantsystem.android.feature.news.data.repository.NewsRemoteRepository
import com.instantsystem.android.feature.news.data.repository.NewsRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val newsFeatureDataModule = module {
    single<NewsApiService> { NewsRemoteApiService(get(named(NEWS_HTTP_CLIENT))) }
    single<NewsRepository> { NewsRemoteRepository(get()) }
}
package com.instantsystem.android.feature.news.di

import org.koin.dsl.module

/**
 * Contain all required dependencies for news feature
 */
val newsFeatureModules = module {
    includes(
        newsFeatureNetworkModule,
        newsFeatureDataModule,
        newsFeatureDomainModule,
        newsFeatureViewModelModule
    )
}
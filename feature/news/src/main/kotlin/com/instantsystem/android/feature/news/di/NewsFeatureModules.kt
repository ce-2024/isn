package com.instantsystem.android.feature.news.di

import org.koin.dsl.module

val newsFeatureModules = module {
    includes(
        newsFeatureNetworkModule,
        newsFeatureDataModule,
        newsFeatureDomainModule,
    )
}
package com.instantsystem.android.feature.news.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val NEWS_API_KEY = ""

val newsFeatureNetworkModule = module {
    // using core httpclient and overridden only needed configuration
    factory(named("newsHttpClient")) { provideMediaHttpClient(get(named("coreHttpClient"))) }
}

private fun provideMediaHttpClient(coreHttpClient: HttpClient): HttpClient {
    return coreHttpClient.config {
        install(DefaultRequest) {
            header(HttpHeaders.Authorization, NEWS_API_KEY)
        }
    }
}
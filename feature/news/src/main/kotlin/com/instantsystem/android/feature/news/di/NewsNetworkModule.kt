package com.instantsystem.android.feature.news.di

import com.instantsystem.android.core.network.di.CORE_HTTP_CLIENT
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import org.koin.core.qualifier.named
import org.koin.dsl.module

//TODO put here the API KEY for https://newsapi.org
const val NEWS_API_KEY = ""

// News feature http client name
internal const val NEWS_HTTP_CLIENT = "newsHttpClient"

/**
 * News feature network module
 * it will use the singleton core http client and override it only needed configuration.
 */
internal val newsFeatureNetworkModule = module {
    // using core httpclient and overridden only needed configuration
    factory(named(NEWS_HTTP_CLIENT)) { provideMediaHttpClient(get(named(CORE_HTTP_CLIENT))) }
}

private fun provideMediaHttpClient(coreHttpClient: HttpClient): HttpClient {
    return coreHttpClient.config {
        install(DefaultRequest) {
            header(HttpHeaders.Authorization, NEWS_API_KEY)
        }
    }
}
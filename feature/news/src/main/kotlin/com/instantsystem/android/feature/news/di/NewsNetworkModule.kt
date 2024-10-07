package com.instantsystem.android.feature.news.di

import com.instantsystem.android.core.network.di.CORE_HTTP_CLIENT
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val NEWS_API_KEY = ""
internal const val NEWS_HTTP_CLIENT = "newsHttpClient"

val newsFeatureNetworkModule = module {
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
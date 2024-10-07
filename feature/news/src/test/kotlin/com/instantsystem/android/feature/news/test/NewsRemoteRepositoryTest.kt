package com.instantsystem.android.feature.news.test

import com.instantsystem.android.core.network.di.coreNetworkModule
import com.instantsystem.android.feature.news.data.api.NewsApiService
import com.instantsystem.android.feature.news.data.entity.TopHeadlinesRequest
import com.instantsystem.android.feature.news.di.newsFeatureModules
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test


class NewsRemoteRepositoryTest : KoinTest {

    private val apiService by inject<NewsApiService>()

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(coreNetworkModule)
            modules(newsFeatureModules)
        }
    }

    @Test
    fun requestTopHeadlines_assert_response_is_not_null() = runTest {
        val articlesResponse = apiService.topHeadlines(TopHeadlinesRequest(country = "en"))
        assert(articlesResponse?.articles?.isNotEmpty() == true)
    }

    @AfterTest
    fun tearDown() = runTest {
        stopKoin()
    }


}
package com.instantsystem.android.feature.news.test.data

import com.instantsystem.android.core.network.di.coreNetworkModule
import com.instantsystem.android.feature.news.data.entity.TopHeadlinesRequest
import com.instantsystem.android.feature.news.data.repository.NewsRepository
import com.instantsystem.android.feature.news.di.newsFeatureDataModule
import com.instantsystem.android.feature.news.di.newsFeatureNetworkModule
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.Locale
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Basic check for NewsRepository implementation
 */
class NewsRepositoryTest : KoinTest {

    private val newsRepository by inject<NewsRepository>()
    private lateinit var usCountryCode: String

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(coreNetworkModule)
            modules(newsFeatureNetworkModule)
            modules(newsFeatureDataModule)
        }
        usCountryCode = Locale.US.country
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun given_us_country_getTopHeadlines_assert_response_not_empty() = runTest {
        val topHeadlines =
            newsRepository.topHeadlines(TopHeadlinesRequest(country = usCountryCode))
        assertNotNull(topHeadlines)
    }

    @Test
    fun given_us_country_getTopHeadlines_assert_articles_not_empty() = runTest {
        val topHeadlines =
            newsRepository.topHeadlines(TopHeadlinesRequest(country = usCountryCode))
        assertNotNull(topHeadlines?.articles?.isNotEmpty())
    }

    @Test
    fun given_bad_country_getTopHeadlines_assert_no_articles_returned() = runTest {
        val topHeadlines = newsRepository.topHeadlines(TopHeadlinesRequest(country = "xyz"))
        assertEquals(topHeadlines?.totalResults, 0)
    }
}
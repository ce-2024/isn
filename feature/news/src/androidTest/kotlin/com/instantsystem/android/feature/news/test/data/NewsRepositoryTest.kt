package com.instantsystem.android.feature.news.test.data

import com.instantsystem.android.feature.news.data.entity.TopHeadlinesRequest
import com.instantsystem.android.feature.news.data.repository.NewsRepository
import kotlinx.coroutines.test.runTest
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.Locale
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Basic check for NewsRepository implementation
 */
class NewsRepositoryTest : KoinTest {

    private val newsRepository by inject<NewsRepository>()
    private lateinit var defaultCountry: String

    @BeforeTest
    fun setUp() {
        defaultCountry = Locale.getDefault().country
    }

    @Test
    fun given_default_country_getTopHeadlines_assert_response_not_empty() = runTest {
        val topHeadlines =
            newsRepository.topHeadlines(TopHeadlinesRequest(country = defaultCountry))
        assertNotNull(topHeadlines)
    }

    @Test
    fun given_default_country_getTopHeadlines_assert_articles_not_empty() = runTest {
        val topHeadlines =
            newsRepository.topHeadlines(TopHeadlinesRequest(country = defaultCountry))
        assertNotNull(topHeadlines?.articles?.isNotEmpty())
    }

    @Test
    fun given_bad_country_getTopHeadlines_assert_no_articles_returned() = runTest {
        val topHeadlines = newsRepository.topHeadlines(TopHeadlinesRequest(country = "xyz"))
        assertEquals(topHeadlines?.totalResults, 0)
    }
}
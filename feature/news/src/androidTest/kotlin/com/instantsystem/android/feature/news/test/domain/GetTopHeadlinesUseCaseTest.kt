package com.instantsystem.android.feature.news.test.domain

import com.instantsystem.android.feature.news.domain.interactor.GetTopHeadlinesUseCase
import com.instantsystem.android.feature.news.domain.interactor.TopHeadlinesParam
import kotlinx.coroutines.test.runTest
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.Test

/**
 * Basic check for GetTopHeadlinesUseCaseTest implementation
 */
class GetTopHeadlinesUseCaseTest : KoinTest {

    val topHeadlinesUseCase by inject<GetTopHeadlinesUseCase>()

    @Test
    fun given_country_us_getTopHeadlines_assert_response_not_empty() = runTest {
        val topHeadlines = topHeadlinesUseCase(TopHeadlinesParam(country = "us"))
        assert(topHeadlines.isNotEmpty())
    }

    @Test
    fun given_country_us_getTopHeadlines_assert_articles_not_empty() = runTest {
        val topHeadlines = topHeadlinesUseCase(TopHeadlinesParam(country = "us"))
        assert(topHeadlines.first().title.isNotEmpty())
    }
}
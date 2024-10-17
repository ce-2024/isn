package com.instantsystem.android.feature.news.test.domain

import com.instantsystem.android.core.network.di.coreNetworkModule
import com.instantsystem.android.feature.news.di.newsFeatureDataModule
import com.instantsystem.android.feature.news.di.newsFeatureNetworkModule
import com.instantsystem.android.feature.news.domain.interactor.GetTopHeadlinesUseCase
import com.instantsystem.android.feature.news.domain.interactor.TopHeadlinesParam
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.Locale
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 * Basic check for GetTopHeadlinesUseCaseTest implementation
 */
class GetTopHeadlinesUseCaseTest : KoinTest {

    val topHeadlinesUseCase by inject<GetTopHeadlinesUseCase>()

    private val testScheduler = TestCoroutineScheduler()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher(testScheduler)
    private val testScope = TestScope(testDispatcher)
    private lateinit var usCountryCode: String

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(coreNetworkModule)
            modules(newsFeatureNetworkModule)
            modules(newsFeatureDataModule)
            modules(module {
                factory { GetTopHeadlinesUseCase(get(), testDispatcher) }
            })
        }
        usCountryCode = Locale.US.country
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun given_country_us_getTopHeadlines_assert_response_not_empty() = testScope.runTest {
        val topHeadlines = topHeadlinesUseCase(TopHeadlinesParam(country = usCountryCode))
        assert(topHeadlines.isNotEmpty())
    }

    @Test
    fun given_country_us_getTopHeadlines_assert_articles_not_empty() = testScope.runTest {
        val topHeadlines = topHeadlinesUseCase(TopHeadlinesParam(country = usCountryCode))
        assert(topHeadlines.first().title.isNotEmpty())
    }
}
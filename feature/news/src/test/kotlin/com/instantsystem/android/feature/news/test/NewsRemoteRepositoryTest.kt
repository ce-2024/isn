package com.instantsystem.android.feature.news.test

import com.instantsystem.android.feature.news.BuildConfig
import com.instantsystem.android.feature.news.data.api.NewsApiService
import com.instantsystem.android.feature.news.data.api.NewsApiService.Companion.SUCCESS_SERVER_RESULT_RESPONSE
import com.instantsystem.android.feature.news.data.api.NewsRemoteApiService
import com.instantsystem.android.feature.news.data.entity.TopHeadlinesRequest
import com.instantsystem.android.feature.news.data.repository.NewsRemoteRepository
import com.instantsystem.android.feature.news.data.repository.NewsRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Rule
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull


class NewsRemoteRepositoryTest : KoinTest {

    private fun createHttpClient(isApiEnabled: Boolean = false): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            if (isApiEnabled) {
                install(DefaultRequest) {
                    header(HttpHeaders.Authorization, BuildConfig.NEWS_API_KEY)
                }
            }
        }
    }

    private val httpClientWithNoApi = module {
        single<HttpClient> { createHttpClient() }
    }
    private val httpClientWithApi = module {
        single<HttpClient> { createHttpClient(isApiEnabled = true) }
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            httpClientWithApi,
            module {
                single<NewsApiService> { NewsRemoteApiService(get()) }
                single<NewsRepository> { NewsRemoteRepository(get()) }
            })
    }

    private val repository by inject<NewsRepository>()

    @Test
    fun `request topHeadlines with api key should assert response success`() = runTest {
        val topHeadlinesResponse = repository.topHeadlines(TopHeadlinesRequest(country = "us"))
        assertEquals(topHeadlinesResponse?.status, SUCCESS_SERVER_RESULT_RESPONSE)
        assertNull(topHeadlinesResponse?.code)
    }

    @Test
    fun `request topHeadlines with bad country should assert response error`() = runTest {
        val topHeadlinesResponse = repository.topHeadlines(TopHeadlinesRequest(country = "12345"))
        assertEquals(topHeadlinesResponse?.status, SUCCESS_SERVER_RESULT_RESPONSE)
        assertNull(topHeadlinesResponse?.code)
    }

    @Test
    fun `request topHeadlines with good country should return article list`() = runTest {
        val topHeadlinesResponse = repository.topHeadlines(TopHeadlinesRequest(country = "us"))
        assertEquals(topHeadlinesResponse?.status, SUCCESS_SERVER_RESULT_RESPONSE)
        assertNotNull(topHeadlinesResponse?.articles)
    }

    @Test
    fun `request topHeadlines without api key should assert response error`() = runTest {
        koinTestRule.koin.loadModules(listOf(httpClientWithNoApi))
        val topHeadlinesResponse = repository.topHeadlines(TopHeadlinesRequest(country = "us"))
        assert(topHeadlinesResponse?.status == "error")
        assert(topHeadlinesResponse?.code == "apiKeyMissing")
    }

}
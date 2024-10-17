package com.instantsystem.android.feature.news.test.ui

import android.content.Context
import android.content.res.Configuration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.lifecycle.SavedStateHandle
import androidx.test.platform.app.InstrumentationRegistry
import com.instantsystem.android.core.network.di.coreNetworkModule
import com.instantsystem.android.feature.news.R
import com.instantsystem.android.feature.news.di.NEWS_HTTP_CLIENT
import com.instantsystem.android.feature.news.di.newsFeatureDataModule
import com.instantsystem.android.feature.news.di.newsFeatureNetworkModule
import com.instantsystem.android.feature.news.domain.interactor.GetNewsTopHeadlinesPagingSource
import com.instantsystem.android.feature.news.ui.NewsHomeScreen
import com.instantsystem.android.feature.news.ui.NewsViewModel
import com.instantsystem.android.feature.news.ui.tag.NewsHomeScreenTestTags
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Rule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import java.util.Locale
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class NewsArticleUiScreenTest : KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var errorMsg: String

    private val context by lazy {
        InstrumentationRegistry.getInstrumentation().targetContext
    }

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(coreNetworkModule)
            modules(newsFeatureNetworkModule)
            modules(newsFeatureDataModule)
            modules(module {
                single { GetNewsTopHeadlinesPagingSource(get()) }
                viewModel { (savedStateHandle: SavedStateHandle) ->
                    NewsViewModel(savedStateHandle, get())
                }
            })
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    private val newsHttpClientWithNoApi = module {
        factory(named(NEWS_HTTP_CLIENT)) {
            HttpClient(CIO) {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                    })
                }
            }
        }
    }

    private fun setLocale(context: Context, locale: Locale) {
        Locale.setDefault(locale)
        val resources = context.resources
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        context.createConfigurationContext(config)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun when_news_app_launched_and_locale_is_fr_assert_empty_screen_displayed() = runTest {
        setLocale(context, Locale.FRANCE)
        assertEquals(Locale.getDefault(), Locale.FRANCE)
        with(composeTestRule) {
            setContent {
                NewsHomeScreen()
                errorMsg =
                    stringResource(R.string.empty_result_message, Locale.getDefault().country)
            }
            waitForIdle()
            waitUntilExactlyOneExists(
                hasText(errorMsg)
            )
            onNodeWithTag(
                NewsHomeScreenTestTags.NEWS_ARTICLE_EMPTY_SCREEN
            ).assertIsDisplayed()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun when_news_app_launched_and_api_key_missing_assert_error_screen_displayed() = runTest {
        loadKoinModules(newsHttpClientWithNoApi)
        setLocale(context, Locale.US)
        assertEquals(Locale.getDefault(), Locale.US)
        with(composeTestRule) {
            setContent {
                NewsHomeScreen()
                errorMsg = stringResource(R.string.loading_error)
            }
            waitForIdle()
            waitUntilExactlyOneExists(hasText(errorMsg))
            // Check if the error screen is displayed
            onNodeWithTag(NewsHomeScreenTestTags.NEWS_ARTICLE_ERROR_SCREEN)
                .assertIsDisplayed()
        }
    }
}
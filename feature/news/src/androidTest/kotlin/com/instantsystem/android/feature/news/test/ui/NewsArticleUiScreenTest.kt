package com.instantsystem.android.feature.news.test.ui

import android.content.Context
import android.content.res.Configuration
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.platform.app.InstrumentationRegistry
import com.instantsystem.android.feature.news.di.NEWS_HTTP_CLIENT
import com.instantsystem.android.feature.news.ui.NewsHomeScreen
import com.instantsystem.android.feature.news.ui.tag.NewsHomeScreenTestTags
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Rule
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import java.util.Locale
import kotlin.test.Test
import kotlin.test.assertEquals

class NewsArticleUiScreenTest : KoinTest {

    private val context by lazy {
        InstrumentationRegistry.getInstrumentation().targetContext
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

    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun when_news_app_launched_and_locale_is_fr_assert_empty_screen_displayed() = runTest {
        setLocale(context, Locale.FRANCE)
        assertEquals(Locale.getDefault(), Locale.FRANCE)
        with(composeTestRule) {
            setContent {
                NewsHomeScreen()
            }
            waitUntilExactlyOneExists(hasText("No articles for country : ${Locale.FRANCE.country}"))
            onNodeWithTag(
                NewsHomeScreenTestTags.NEWS_ARTICLE_EMPTY_SCREEN
            ).assertIsDisplayed()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun when_news_app_launched_and_api_key_missing_assert_error_screen_displayed() = runTest {
        loadKoinModules(newsHttpClientWithNoApi)
        with(composeTestRule) {
            setContent {
                NewsHomeScreen()
            }
            waitUntilExactlyOneExists(hasText("Some error happened!!"))
            // Check if the error screen is displayed
            onNodeWithTag(NewsHomeScreenTestTags.NEWS_ARTICLE_ERROR_SCREEN)
                .assertIsDisplayed()
        }
    }

    private fun setLocale(context: Context, locale: Locale) {
        Locale.setDefault(locale)
        val resources = context.resources
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        context.createConfigurationContext(config)
    }


}
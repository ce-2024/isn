package com.instantsystem.android.feature.news.test.ui

import android.content.Context
import android.content.res.Configuration
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.platform.app.InstrumentationRegistry
import com.instantsystem.android.feature.news.ui.NewsHomeScreen
import com.instantsystem.android.feature.news.ui.tag.NewsHomeScreenTestTags
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.koin.test.KoinTest
import java.util.Locale
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class NewsArticleUiScreenTest : KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setUp() {
//        val dispatcher = UnconfinedTestDispatcher()
//        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun test_news_app_given_country_us_assert_empty_list_articles() = runTest {
        composeTestRule.setContent {
            NewsHomeScreen()
        }
        composeTestRule.onNodeWithTag(
            NewsHomeScreenTestTags.NEWS_ARTICLE_ITEM_SCREEN
        ).assertIsDisplayed()
    }

    @Test
    fun test_news_app_given_country_fr_assert_empty_list_articles() = runTest {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        setLocale(context, Locale("zyw"))
        composeTestRule.setContent {
            NewsHomeScreen()
        }

        composeTestRule.onNodeWithTag(
            NewsHomeScreenTestTags.NEWS_ARTICLE_EMPTY_SCREEN
        ).assertIsDisplayed()
    }

    @Test
    fun test_news_app_given_max_request_response_assert_news_error_displayed() = runTest {
        composeTestRule.setContent {
            NewsHomeScreen()
        }

        Thread.sleep(2000)
        // Check if the error screen is displayed
        composeTestRule.onNodeWithTag(NewsHomeScreenTestTags.NEWS_ARTICLE_ERROR_SCREEN)
            .assertIsDisplayed()

    }

    private fun setLocale(context: Context, locale: Locale) {
        Locale.setDefault(locale)
        val resources = context.resources
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        context.createConfigurationContext(config)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun tearDown() {
        //Dispatchers.resetMain()
    }


}
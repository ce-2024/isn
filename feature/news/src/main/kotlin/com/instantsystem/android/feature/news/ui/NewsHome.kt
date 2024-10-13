package com.instantsystem.android.feature.news.ui

import android.os.Parcelable
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.instantsystem.android.feature.news.domain.model.NewsArticle
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel

/**
 * sealed class to distinguish between the two screens being displayed.
 * the NewsArticlesListScreen and NewsArticleDetailScreenState
 */
@Parcelize
internal sealed class NewsState : Parcelable {
    data object NewsArticlesListScreenState : NewsState()
    data class NewsArticleDetailScreenState(val article: NewsArticle) : NewsState()
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NewsHomeScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<NewsViewModel>()
    val articlesFlowState = viewModel.paginatedNewsFlow.collectAsLazyPagingItems()
    val listState = rememberLazyListState()
    var newsState by rememberSaveable {
        mutableStateOf<NewsState>(NewsState.NewsArticlesListScreenState)
    }
    SharedTransitionLayout {
        // we will use animated content as we have all information to build
        // NewsArticles and NewsArticleDetail
        AnimatedContent(
            modifier = modifier,
            targetState = newsState,
            label = "News List and details"
        ) { targetState ->
            when (targetState) {
                NewsState.NewsArticlesListScreenState -> {
                    NewsArticlesListScreen(
                        articlesFlowState,
                        listState,
                        onArticleClicked = {
                            newsState = NewsState.NewsArticleDetailScreenState(it)
                        },
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout
                    )
                }

                is NewsState.NewsArticleDetailScreenState -> {
                    NewsArticleDetailScreen(
                        article = targetState.article,
                        onBackPressed = {
                            newsState = NewsState.NewsArticlesListScreenState
                        },
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout
                    )
                }
            }
        }
    }
}
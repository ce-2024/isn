package com.instantsystem.android.feature.news.ui

import android.os.Parcelable
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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

@Composable
fun NewsHomeScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<NewsViewModel>()
    val articlesFlowState = viewModel.paginatedNewsFlow.collectAsLazyPagingItems()
    val searchResultsFlowState = viewModel.pagingSearchResults.collectAsLazyPagingItems()
    val listState = rememberLazyListState()
    var newsState by rememberSaveable {
        mutableStateOf<NewsState>(NewsState.NewsArticlesListScreenState)
    }

    val searchQueryUiState by viewModel.searchQueryUiState.collectAsState()
    // we will use animated content as we have all information to build
    // NewsArticles and NewsArticleDetail
    AnimatedContent(
        modifier = modifier,
        targetState = newsState,
        label = "News List and details",
        transitionSpec = {
            fadeIn() + slideInVertically(animationSpec = tween(400),
                initialOffsetY = { fullHeight -> fullHeight }) togetherWith
                    fadeOut(animationSpec = tween(200))
        }
    ) { targetState ->
        when (targetState) {
            NewsState.NewsArticlesListScreenState -> {
                Column {
                    NewsSearchBox(searchQueryUiState.query) { query ->
                        viewModel.onSearchQueryChanged(query)
                    }
                    // choose the flow based on the search query
                    val result = if (searchQueryUiState.isSearching)
                        searchResultsFlowState else articlesFlowState
                    NewsArticlesListScreen(result, listState) {
                        newsState = NewsState.NewsArticleDetailScreenState(it)
                    }
                }
            }

            is NewsState.NewsArticleDetailScreenState -> {
                NewsArticleDetailScreen(article = targetState.article) {
                    newsState = NewsState.NewsArticlesListScreenState
                }
            }
        }
    }
}
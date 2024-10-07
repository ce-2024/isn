package com.instantsystem.android.feature.news.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.instantsystem.android.feature.news.R
import com.instantsystem.android.feature.news.domain.model.NewsArticle
import org.koin.compose.koinInject
import java.util.Locale

internal sealed class NewsState {
    data object NewsArticleScreenState : NewsState()
    data class NewsArticleDetailScreenState(val article: NewsArticle) : NewsState()
}


@Composable
fun NewsArticleScreen() {
    val viewModel = koinInject<NewsViewModel>()
    val uiState = viewModel.paginatedNewsFlow.collectAsLazyPagingItems()
    val listState = rememberLazyListState()
    var newsState by remember {
        mutableStateOf<NewsState>(NewsState.NewsArticleScreenState)
    }
    AnimatedContent(
        targetState = newsState,
        label = "News List and details",
        transitionSpec = {
            fadeIn() + slideInVertically(animationSpec = tween(400),
                initialOffsetY = { fullHeight -> fullHeight }) togetherWith
                    fadeOut(animationSpec = tween(200))
        }
    ) { targetState ->
        when (targetState) {
            NewsState.NewsArticleScreenState -> {
                NewsArticleList(uiState, listState) {
                    newsState = NewsState.NewsArticleDetailScreenState(it)
                }
            }

            is NewsState.NewsArticleDetailScreenState -> {
                NewsArticleDetails(article = targetState.article) {
                    newsState = NewsState.NewsArticleScreenState
                }
            }
        }
    }
}

@Composable
private fun NewsArticleList(
    uiState: LazyPagingItems<NewsArticle>,
    listState: LazyListState,
    onArticleClicked: (NewsArticle) -> Unit = {}
) {
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (uiState.loadState.refresh == LoadState.Loading) {
            item {
                Text(
                    text = stringResource(R.string.loading),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }

        if (uiState.loadState.refresh is LoadState.Error) {
            val errorState = uiState.loadState.refresh as LoadState.Error
            item {
                NewsErrorScreen(errorState.error)
            }
        }

        if (uiState.itemCount == 0 && uiState.loadState.refresh is LoadState.NotLoading) {
            item {
                NewsEmptyScreen()
            }
        }

        items(count = uiState.itemCount) { index ->
            val newsArticle = uiState[index]
            newsArticle?.let {
                NewsArticleItem(it, onArticleClicked)
            }
        }

        if (uiState.loadState.append == LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 25.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }

}

@Composable
fun NewsEmptyScreen() {
    Text(
        modifier = Modifier.padding(vertical = 10.dp),
        text = stringResource(R.string.empty_result, Locale.getDefault().country),
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun NewsErrorScreen(exception: Throwable?) {
    Text(
        modifier = Modifier.padding(vertical = 10.dp),
        text = stringResource(R.string.loading_error),
        color = Color.Red,
        style = MaterialTheme.typography.titleLarge
    )
    Text(
        modifier = Modifier.padding(10.dp),
        text = "Message: ${exception?.message}",
        color = Color.Red.copy(alpha = 0.7f),
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
private fun NewsArticleItem(
    article: NewsArticle,
    onArticleClicked: (NewsArticle) -> Unit = {}
) {
    val defaultPadding = 10.dp
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable {
            onArticleClicked(article)
        }) {

        Text(
            text = article.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(defaultPadding),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.headlineLarge,
        )
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(350.dp)
                .fillMaxWidth()
                .padding(defaultPadding),
            model = article.urlToImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }
}

@Preview
@Composable
fun NewsArticleItemPreview() {
    NewsArticleItem(
        NewsArticle(
            title = "Title",
            description = "",
            url = "",
            urlToImage = "UrlToImage"
        )
    )
}
package com.instantsystem.android.feature.news.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.instantsystem.android.feature.news.R
import com.instantsystem.android.feature.news.domain.model.NewsArticle
import com.instantsystem.android.feature.news.ui.tag.NewsHomeScreenTestTags
import java.util.Locale

/**
 *
 */
@Composable
fun NewsArticlesListScreen(
    savedPagingItems: LazyPagingItems<NewsArticle>,
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

        when (savedPagingItems.loadState.refresh) {
            is LoadState.Loading -> {
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

            is LoadState.Error -> {
                val errorState = savedPagingItems.loadState.refresh as LoadState.Error
                item {
                    NewsErrorScreen(errorState.error.message.orEmpty())
                }
            }

            else -> {
                if (savedPagingItems.itemCount == 0)
                    item {
                        NewsEmptyScreen()
                    }
            }
        }

        when (savedPagingItems.loadState.append) {
            is LoadState.Loading -> {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 25.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }

            is LoadState.Error -> {
                //TODO add a button to retry op.
            }

            else -> {}
        }

        items(count = savedPagingItems.itemCount) { index ->
            val newsArticle = savedPagingItems[index]
            newsArticle?.let {
                NewsArticleItem(it, onArticleClicked)
            }
        }
    }
}

@Composable
private fun NewsArticleItem(
    article: NewsArticle,
    onArticleClicked: (NewsArticle) -> Unit = {}
) {
    val defaultPadding = 10.dp
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(NewsHomeScreenTestTags.NEWS_ARTICLE_ITEM_SCREEN)
            .padding(10.dp)
            .clickable {
                onArticleClicked(article)
            }
    ) {

        Text(
            text = article.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(defaultPadding),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge,
        )
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(350.dp)
                .fillMaxWidth()
                .padding(defaultPadding)
                .clip(RoundedCornerShape(12.dp)),
            model = article.urlToImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun NewsEmptyScreen() {
    Text(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .testTag(
                NewsHomeScreenTestTags.NEWS_ARTICLE_EMPTY_SCREEN
            ),
        text = stringResource(R.string.empty_result, Locale.getDefault().country),
        style = MaterialTheme.typography.titleLarge
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NewsArticleItemPreview() {
    NewsArticleItem(
        NewsArticle(
            title = "Title",
            description = "",
            url = "",
            urlToImage = "UrlToImage"
        )
    )
}
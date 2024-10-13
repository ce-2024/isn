package com.instantsystem.android.feature.news.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
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
import androidx.compose.material3.Surface
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
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.instantsystem.android.core.designsystem.theme.InstantSystemNewsTheme
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
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        when (savedPagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                items(count = 10) {
                    ShimmerNewsArticleItem()
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

        items(
            count = savedPagingItems.itemCount,
            key = savedPagingItems.itemKey { article -> article.id },
            contentType = savedPagingItems.itemContentType { "News Article" }
        ) { index ->
            val newsArticle = savedPagingItems[index]
            newsArticle?.let {
                NewsArticleItem(it, onArticleClicked)
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
            .testTag(NewsHomeScreenTestTags.NEWS_ARTICLE_ITEM_SCREEN)
            .padding(10.dp)
            .clickable {
                onArticleClicked(article)
            }
    ) {
        // Article Date
        Text(
            text = stringResource(R.string.published_at, article.date),
            modifier = Modifier.padding(defaultPadding),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelSmall,
        )
        // Article title
        Text(
            text = article.title,
            modifier = Modifier
                .padding(horizontal = defaultPadding),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge,
        )
        // Article image
        AsyncImage(
            modifier = Modifier
                .requiredHeight(256.dp)
                .padding(defaultPadding)
                .clip(RoundedCornerShape(8.dp)),
            model = article.urlToImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        // Article description
        Text(
            text = article.description,
            modifier = Modifier
                .padding(defaultPadding),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
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

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
private fun NewsArticleItemPreview() {
    InstantSystemNewsTheme {
        Surface {
            NewsArticleItem(
                NewsArticle(
                    id = "1",
                    publishedAt = "2024-10-06T16:07:08Z",
                    author = "author",
                    source = "source",
                    title = stringResource(R.string.big_text_description),
                    description = stringResource(R.string.big_text_description),
                    url = "url",
                    urlToImage = "UrlToImage",
                    content = "The unformatted content of the article",
                )
            )
        }
    }
}
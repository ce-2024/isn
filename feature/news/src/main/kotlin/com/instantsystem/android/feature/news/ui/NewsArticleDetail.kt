package com.instantsystem.android.feature.news.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.instantsystem.android.core.designsystem.theme.InstantSystemNewsTheme
import com.instantsystem.android.feature.news.R
import com.instantsystem.android.feature.news.domain.model.NewsArticle
import com.instantsystem.android.feature.news.ui.tag.NewsHomeScreenTestTags

/**
 * Display News Article detail screen
 * @param article: Article to show
 * @param onBackPressed : handle back press to return to list of news articles
 */
@Composable
fun NewsArticleDetailScreen(
    article: NewsArticle,
    onBackPressed: () -> Unit = {}
) {
    val currentArticle by rememberSaveable { mutableStateOf(article) }
    val scrollState = rememberScrollState()
    val uriHandler = LocalUriHandler.current
    val defaultPadding = 10.dp
    BackHandler {
        onBackPressed()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag(NewsHomeScreenTestTags.NEWS_ARTICLE_DETAIL_SCREEN)
            .verticalScroll(scrollState)
            .padding(defaultPadding)
    ) {
        Row {
            IconButton(onClick = { onBackPressed() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(defaultPadding),
                style = MaterialTheme.typography.titleLarge,
                text = currentArticle.title
            )
        }
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .padding(defaultPadding)
                .clip(RoundedCornerShape(8.dp)),
            model = currentArticle.urlToImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(defaultPadding),
            text = currentArticle.description,
            style = MaterialTheme.typography.headlineMedium
        )
        Button(
            modifier = Modifier
                .align(Alignment.End)
                .padding(defaultPadding),
            onClick = { uriHandler.openUri(currentArticle.url) }) {
            Text(
                text = stringResource(R.string.article_link)
            )
        }
    }
}

@Preview
@Composable
private fun NewsArticleDetailScreenPreview() {
    InstantSystemNewsTheme {
        Surface {
            NewsArticleDetailScreen(
                NewsArticle(
                    title = "Title",
                    description = stringResource(R.string.big_text_description),
                    url = "Url",
                    urlToImage = "UrlToImage"
                )
            )
        }
    }
}
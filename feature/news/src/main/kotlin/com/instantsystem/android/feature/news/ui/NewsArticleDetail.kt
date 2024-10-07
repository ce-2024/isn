package com.instantsystem.android.feature.news.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.instantsystem.android.feature.news.R
import com.instantsystem.android.feature.news.domain.model.NewsArticle
import com.instantsystem.android.feature.news.ui.tag.NewsHomeScreenTestTags

@Composable
fun NewsArticleDetails(
    article: NewsArticle,
    onBackPressed: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val uriHandler = LocalUriHandler.current
    val defaultPadding = 10.dp
    BackHandler {
        onBackPressed()
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(NewsHomeScreenTestTags.NEWS_ARTICLE_DETAIL_SCREEN)
                    .verticalScroll(scrollState)
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,

                ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(defaultPadding),
                    style = MaterialTheme.typography.headlineLarge,
                    text = article.title
                )
                AsyncImage(
                    modifier = Modifier
                        .requiredHeight(350.dp)
                        .padding(defaultPadding),
                    model = article.urlToImage,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(defaultPadding),
                    text = article.description,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    modifier = Modifier
                        .clickable {
                            uriHandler.openUri(article.url)
                        }
                        .padding(defaultPadding)
                        .fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    text = buildAnnotatedString {
                        append(stringResource(R.string.article_link))
                        pushStyle(SpanStyle(color = Color.Blue))
                        append(article.url)
                        toAnnotatedString()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun NewsArticleDetailScreenPreview() {

    NewsArticleDetails(
        NewsArticle(
            title = "Title",
            description = stringResource(R.string.big_text_description),
            url = "Url",
            urlToImage = "UrlToImage"
        )
    )
}
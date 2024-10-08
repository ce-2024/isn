package com.instantsystem.android.feature.news.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.instantsystem.android.feature.news.R
import com.instantsystem.android.feature.news.ui.tag.NewsHomeScreenTestTags


/**
 * Display error message
 * @param message message to show
 */
@Composable
fun NewsErrorScreen(message: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .testTag(NewsHomeScreenTestTags.NEWS_ARTICLE_ERROR_SCREEN),
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.loading_error),
            color = Color.Red,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            modifier = Modifier.padding(10.dp),
            text = stringResource(R.string.news_error_message, message),
            color = Color.Red.copy(alpha = 0.7f),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Composable
private fun NewsErrorScreenPreview(modifier: Modifier = Modifier) {
    NewsErrorScreen("very long message Exception Message")
}
package com.instantsystem.android.feature.news.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.instantsystem.android.core.designsystem.theme.InstantSystemNewsTheme

@Composable
fun NewsSearchBox(
    query: String,
    onSearch: (String) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf(query) }

    TextField(
        value = searchQuery,
        onValueChange = {
            searchQuery = it
            onSearch(searchQuery)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
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
fun NewsSearchBoxPreview() {
    InstantSystemNewsTheme {
        Surface {
            NewsSearchBox(
                query = "query",
                onSearch = {}
            )
        }
    }
}
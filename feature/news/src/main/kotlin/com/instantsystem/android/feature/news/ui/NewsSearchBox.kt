package com.instantsystem.android.feature.news.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.instantsystem.android.core.designsystem.theme.InstantSystemNewsTheme
import com.instantsystem.android.feature.news.R


@Composable
fun NewsSearchBoxScreen(
    queryUiState: SearchQueryUiState,
    onSearch: (String) -> Unit = {}
) {
    var showAdvancedSearch by remember { mutableStateOf(false) }
    NewsSearchBox(queryUiState.query, onSearch)
    Text(
        text = if (showAdvancedSearch) {
            stringResource(R.string.hide_advanced_search_options)
        } else {
            stringResource(R.string.show_advanced_search_options)
        },
        textAlign = TextAlign.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                showAdvancedSearch = !showAdvancedSearch
            }
    )
    AnimatedVisibility(
        visible = showAdvancedSearch,
        modifier = Modifier
            .fillMaxWidth(),
        enter = remember {
            fadeIn(animationSpec = tween(200))
        },
        exit = remember {
            fadeOut(animationSpec = tween(200))
        }
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            AdvancedNewsSearchParam(queryUiState.searchFilter)
            Button({
                showAdvancedSearch = !showAdvancedSearch
            }) {
                Text(
                    text = stringResource(R.string.hide),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}


@Composable
private fun NewsSearchBox(
    query: String,
    onSearch: (String) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf(query) }
    TextField(
        value = query,
        onValueChange = {
            searchQuery = it
            onSearch(searchQuery)
        },
        label = { Text(text = stringResource(R.string.search_news)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    )
}

@Composable
private fun AdvancedNewsSearchParam(searchFilter: SearchQueryFilter) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        SearchInOptionsContent(searchFilter.searchInFilter)
        SourcesOptionsContent(searchFilter.sourceFilter)
        DomainOptionsContent(searchFilter.domainFilter)
        ExcludeDomainsOptionsContent(searchFilter.excludeDomainFilter)
        DateOptionsContent()
        LanguageOptionsContent(searchFilter.languageFilter)
        SortByOptionsContent(searchFilter.sortByFilter)
    }
}

@Composable
fun SortByOptionsContent(sortByFilter: List<String>) {
    val sortByFilterOptions by remember { mutableStateOf(sortByFilter) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        sortByFilterOptions.forEach { sortByName ->
            FilterChip(
                selected = true,
                onClick = {},
                label = {
                    Text(text = sortByName)
                },
            )
        }
    }
}


@Composable
fun LanguageOptionsContent(languageFilter: List<String>) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateOptionsContent() {
    val datePickerState = rememberDatePickerState()
    DatePicker(
        state = datePickerState, modifier = Modifier.padding(8.dp),
        showModeToggle = true
    )
}

@Composable
fun ExcludeDomainsOptionsContent(excludeDomainFilter: List<String>) {

}

@Composable
fun DomainOptionsContent(domainFilter: List<String>) {

}

@Composable
fun SourcesOptionsContent(sourceFilter: List<String>) {

}

@Composable
private fun SearchInOptionsContent(searchInFilter: List<String>) {
    val searchInOptions by remember { mutableStateOf(searchInFilter) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        searchInOptions.forEach { filterName ->
            FilterChip(
                selected = true,
                onClick = {},
                label = {
                    Text(text = filterName)
                },
            )
        }
    }
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

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun AdvancedNewsSearchParamPreview() {
    InstantSystemNewsTheme {
        Surface {
            AdvancedNewsSearchParam(
                searchFilter = SearchQueryFilter
            )
        }
    }
}
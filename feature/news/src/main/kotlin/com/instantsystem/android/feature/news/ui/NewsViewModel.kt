package com.instantsystem.android.feature.news.ui

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.instantsystem.android.feature.news.data.api.NewsApiService.Companion.MAX_PER_PAGE
import com.instantsystem.android.feature.news.domain.interactor.GetNewsPagingSource
import com.instantsystem.android.feature.news.domain.interactor.GetNewsPagingSourceParam
import com.instantsystem.android.feature.news.domain.interactor.SearchNewsUseCase
import com.instantsystem.android.feature.news.domain.interactor.SearchQuery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.parcelize.Parcelize
import java.util.Locale

/**
 * News viewModel
 * depends on [GetNewsPagingSource] to fetch news from server
 */
class NewsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getTopHeadlinesPagingSource: GetNewsPagingSource,
    private val getSearchNewsPagingSource: SearchNewsUseCase
) : ViewModel() {

    // save state keys
    companion object {
        private const val COUNTRY_STATE_KEY = "country_key"
        private const val SEARCH_QUERY_UI_STATE_KEY = "search_query_ui_key"
    }

    private val savedCountry: String =
        savedStateHandle.get<String>(COUNTRY_STATE_KEY) ?: Locale.getDefault().country

    private val _searchQueryUiState = MutableStateFlow(
        savedStateHandle[SEARCH_QUERY_UI_STATE_KEY]
            ?: SearchQueryUiState(country = savedCountry)
    )
    val searchQueryUiState = _searchQueryUiState.asStateFlow()

    /**
     * Triggered each time user enter a query to search
     */
    fun onSearchQueryChanged(query: String) {
        _searchQueryUiState.update {
            it.copy(
                isSearching = query.isNotBlank(),
                query = query
            )
        }
        savedStateHandle[SEARCH_QUERY_UI_STATE_KEY] = _searchQueryUiState.value
    }

    /**
     * Each time search query change, this will returns a new flow
     * When the search flow emits a new value the previous flow is cancelled.
     * The flow is cached in viewModel to persist through configuration changes.
     */
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val newsPagingResults = _searchQueryUiState.flatMapLatest { searchQueryState ->
        Pager(
            config = PagingConfig(pageSize = MAX_PER_PAGE, enablePlaceholders = true),
            pagingSourceFactory = { getNewsPagingSourceFactory(searchQueryState) }
        ).flow
    }.debounce(800)
        .distinctUntilChanged()
        .cachedIn(viewModelScope)

    /**
     * PagingSourceFactory : will provide the correct paging source based on searchQueryState
     */
    private fun getNewsPagingSourceFactory(searchQueryState: SearchQueryUiState) =
        if (searchQueryState.isSearching) {
            getSearchNewsPagingSource(
                SearchQuery(
                    query = searchQueryState.query,
                    searchIn = searchQueryState.searchIn,
                    language = searchQueryState.language
                )
            )
        } else {
            getTopHeadlinesPagingSource(
                GetNewsPagingSourceParam(
                    country = searchQueryState.country
                )
            )
        }
}

@Parcelize
data class SearchQueryUiState(
    val isSearching: Boolean = false,
    val query: String = "",
    val searchIn: String = "",
    val language: String = Locale.getDefault().language,
    val country: String = Locale.getDefault().country,
    val searchFilter: SearchQueryFilter = SearchQueryFilter,
) : Parcelable


data object SearchQueryFilter {
    val sourceFilter: List<String> = emptyList()
    val domainFilter: List<String> = emptyList()
    val excludeDomainFilter: List<String> = emptyList()
    val languageFilter: List<String> = listOf(
        "ar", "de", "en", "es", "fr", "he", "it", "nl", "no", "pt", "ru", "sv", "ud", "zh"
    )
    val sortByFilter: List<String> = listOf("relevancy", "popularity", "publishedAt")
    val searchInFilter: List<String> = listOf("title", "description", "content")
}
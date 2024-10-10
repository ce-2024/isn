package com.instantsystem.android.feature.news.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.instantsystem.android.feature.news.data.api.NewsApiService.Companion.MAX_PER_PAGE
import com.instantsystem.android.feature.news.domain.interactor.GetNewsPagingSource
import com.instantsystem.android.feature.news.domain.interactor.GetNewsPagingSourceParam
import com.instantsystem.android.feature.news.domain.interactor.SearchNewsUseCase
import com.instantsystem.android.feature.news.domain.interactor.SearchQuery
import com.instantsystem.android.feature.news.domain.model.NewsArticle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import java.util.Locale

/**
 * News viewModel
 * depends on [GetNewsPagingSource] to fetch news from server
 */
class NewsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getTopHeadlinesPagingSource: GetNewsPagingSource,
    private val getSearchNewsPagingSource: SearchNewsUseCase
) : ViewModel() {

    private val country: String =
        savedStateHandle.get<String>("country") ?: Locale.getDefault().country


    private val searchQuery: SearchQueryUiState =
        savedStateHandle["searchQuery"] ?: SearchQueryUiState()

    private val _searchQuery = MutableStateFlow(searchQuery)

    fun onSearchQueryChanged(query: String) {
        _searchQuery.update {
            it.copy(query = query)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingSearchResults = _searchQuery.flatMapLatest { query ->
        if (query.query.isEmpty()) return@flatMapLatest emptyFlow()
        Pager(
            config = PagingConfig(pageSize = MAX_PER_PAGE, enablePlaceholders = true),
            pagingSourceFactory = {
                getSearchNewsPagingSource(
                    SearchQuery(
                        query = query.query,
                        searchIn = query.searchIn,
                        language = query.language
                    )
                )
            }
        ).flow
            // this ensure the flow is cached in viewModel and prevent loading more data
            // when orientation changed
            .cachedIn(viewModelScope)
    }

    /**
     * Paging flow of news articles
     */
    val paginatedNewsFlow: Flow<PagingData<NewsArticle>> = Pager(
        config = PagingConfig(pageSize = MAX_PER_PAGE, enablePlaceholders = true),
        pagingSourceFactory = {
            getTopHeadlinesPagingSource(
                GetNewsPagingSourceParam(
                    country = country
                )
            )
        }
    ).flow
        // this ensure the flow is cached in viewModel and prevent loading more data
        // when orientation changed
        .cachedIn(viewModelScope)
}

data class SearchQueryUiState(
    val query: String = "",
    val searchIn: String = "",
    val language: String = "",
)
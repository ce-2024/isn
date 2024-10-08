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
import com.instantsystem.android.feature.news.domain.model.NewsArticle
import kotlinx.coroutines.flow.Flow
import java.util.Locale

/**
 * News viewModel
 * depends on [GetNewsPagingSource] to fetch news from server
 */
class NewsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getTopHeadlinesPagingSource: GetNewsPagingSource,
) : ViewModel() {

    private val country: String =
        savedStateHandle.get<String>("country") ?: Locale.getDefault().country

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
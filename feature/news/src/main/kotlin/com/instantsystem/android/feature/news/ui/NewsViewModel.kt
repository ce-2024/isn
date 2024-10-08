package com.instantsystem.android.feature.news.ui

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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import java.util.Locale

/**
 * News viewModel
 * depends on [GetNewsPagingSource] to fetch news from server
 */
class NewsViewModel(
    private val getTopHeadlinesPagingSource: GetNewsPagingSource,
) : ViewModel() {

    /**
     * Paging flow of news articles
     */
    @OptIn(FlowPreview::class)
    val paginatedNewsFlow: Flow<PagingData<NewsArticle>> = Pager(
        config = PagingConfig(pageSize = MAX_PER_PAGE, enablePlaceholders = true),
        pagingSourceFactory = {
            getTopHeadlinesPagingSource(
                GetNewsPagingSourceParam(
                    country = Locale.getDefault().country
                )
            )
        }
    ).flow
        // this ensure the flow is cached in viewModel and prevent loading more data
        // when orientation changed
        .cachedIn(viewModelScope)
        .debounce(5000)
}
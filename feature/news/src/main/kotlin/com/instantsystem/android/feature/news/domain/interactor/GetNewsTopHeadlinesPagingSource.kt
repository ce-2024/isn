package com.instantsystem.android.feature.news.domain.interactor

import com.instantsystem.android.feature.news.data.repository.NewsRepository
import com.instantsystem.android.feature.news.domain.paging.NewsTopHeadlinesPagingSource

class GetNewsTopHeadlinesPagingSource(
    private val repository: NewsRepository
) {
    operator fun invoke(param: TopHeadlinesPagingSourceParam) =
        NewsTopHeadlinesPagingSource(param.country, repository)
}

data class TopHeadlinesPagingSourceParam(
    val country: String
)
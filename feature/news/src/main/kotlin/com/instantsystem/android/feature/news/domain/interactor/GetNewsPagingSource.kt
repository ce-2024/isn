package com.instantsystem.android.feature.news.domain.interactor

import com.instantsystem.android.feature.news.data.repository.NewsRepository
import com.instantsystem.android.feature.news.domain.paging.NewsPagingSource

class GetNewsPagingSource(
    private val repository: NewsRepository
) {
    operator fun invoke(param: GetNewsPagingSourceParam) =
        NewsPagingSource(param.country, repository)
}

data class GetNewsPagingSourceParam(
    val country: String
)
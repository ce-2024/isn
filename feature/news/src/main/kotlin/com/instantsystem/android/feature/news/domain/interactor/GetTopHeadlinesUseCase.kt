package com.instantsystem.android.feature.news.domain.interactor

import com.instantsystem.android.feature.news.data.entity.TopHeadlinesRequest
import com.instantsystem.android.feature.news.data.repository.NewsRepository
import com.instantsystem.android.feature.news.domain.model.NewsArticle
import com.instantsystem.android.feature.news.toModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetTopHeadlinesUseCase(
    private val repository: NewsRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {


    suspend operator fun invoke(param: TopHeadlinesParam): List<NewsArticle> =
        withContext(ioDispatcher) {
            repository.topHeadlines(
                TopHeadlinesRequest(
                    country = param.country
                )
            )?.toModel() ?: emptyList()
        }
}

data class TopHeadlinesParam(
    val country: String
)
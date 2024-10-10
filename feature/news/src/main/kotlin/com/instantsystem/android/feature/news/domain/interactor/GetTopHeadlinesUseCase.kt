package com.instantsystem.android.feature.news.domain.interactor

import com.instantsystem.android.feature.news.data.entity.TopHeadlinesRequest
import com.instantsystem.android.feature.news.data.entity.TopHeadlinesResponse
import com.instantsystem.android.feature.news.data.repository.NewsRepository
import com.instantsystem.android.feature.news.domain.model.NewsArticle
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

fun TopHeadlinesResponse.toModel(): List<NewsArticle> = articles.map {
    NewsArticle(
        title = it.title.orEmpty(),
        description = it.description.orEmpty(),
        url = it.url.orEmpty(),
        urlToImage = it.urlToImage.orEmpty(),
        content = it.content?.take(200).orEmpty(),
        author = it.author.orEmpty(),
        publishedAt = it.publishedAt.orEmpty(),
        source = it.source?.name.orEmpty()
    )
}
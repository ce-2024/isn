package com.instantsystem.android.feature.news.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.instantsystem.android.feature.news.data.entity.Articles
import com.instantsystem.android.feature.news.data.entity.TopHeadlinesRequest
import com.instantsystem.android.feature.news.data.repository.NewsRepository
import com.instantsystem.android.feature.news.domain.model.NewsArticle

class NewsPagingSource(
    private val country: String,
    private val repository: NewsRepository
) : PagingSource<Int, NewsArticle>() {

    override fun getRefreshKey(state: PagingState<Int, NewsArticle>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult.Page<Int, NewsArticle> {
        try {
            val nextPageNumber = params.key ?: 1
            val response = repository.topHeadlines(
                TopHeadlinesRequest(
                    page = nextPageNumber,
                    pageSize = params.loadSize,
                    country = country
                )
            )
            return LoadResult.Page(
                data = response?.articles.toDomain(),
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (exception: Exception) {
            throw exception
        }
    }
}

fun List<Articles>?.toDomain(): List<NewsArticle> {
    return this?.filterNot {
        it.title.isNullOrBlank() || it.urlToImage.isNullOrBlank()
    }?.map {
        NewsArticle(
            title = it.title.orEmpty(),
            description = it.description.orEmpty(),
            url = it.url.orEmpty(),
            urlToImage = it.urlToImage.orEmpty()
        )
    } ?: emptyList()
}
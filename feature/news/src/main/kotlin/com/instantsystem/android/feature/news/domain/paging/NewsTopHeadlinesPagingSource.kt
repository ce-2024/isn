package com.instantsystem.android.feature.news.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.instantsystem.android.feature.news.data.api.NewsApiService.Companion.SUCCESS_SERVER_RESULT_RESPONSE
import com.instantsystem.android.feature.news.data.entity.TopHeadlinesRequest
import com.instantsystem.android.feature.news.data.repository.NewsRepository
import com.instantsystem.android.feature.news.domain.model.NewsArticle
import com.instantsystem.android.feature.news.ext.toDomain

class NewsTopHeadlinesPagingSource(
    private val country: String,
    private val repository: NewsRepository
) : PagingSource<Int, NewsArticle>() {

    override fun getRefreshKey(state: PagingState<Int, NewsArticle>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsArticle> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = repository.topHeadlines(
                TopHeadlinesRequest(
                    page = nextPageNumber,
                    pageSize = params.loadSize,
                    country = country
                )
            )
            if (response?.status != SUCCESS_SERVER_RESULT_RESPONSE) {
                LoadResult.Error(Exception(response?.message))
            } else
                LoadResult.Page(
                    data = response.articles.toDomain(),
                    prevKey = null,
                    nextKey = nextPageNumber + 1
                )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
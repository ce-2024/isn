package com.instantsystem.android.feature.news.data.repository

import com.instantsystem.android.feature.news.data.api.NewsApiService
import com.instantsystem.android.feature.news.data.entity.TopHeadlinesRequest
import com.instantsystem.android.feature.news.data.entity.TopHeadlinesResponse

// Remote News Repository
class NewsRemoteRepository(
    private val apiService: NewsApiService
) : NewsRepository {

    override suspend fun topHeadlines(topHeadlinesRequest: TopHeadlinesRequest): TopHeadlinesResponse? {
        return apiService.topHeadlines(topHeadlinesRequest)
    }
}
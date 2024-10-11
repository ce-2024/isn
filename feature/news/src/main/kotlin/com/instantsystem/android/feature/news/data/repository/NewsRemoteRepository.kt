package com.instantsystem.android.feature.news.data.repository

import com.instantsystem.android.feature.news.data.api.NewsApiService
import com.instantsystem.android.feature.news.data.entity.DefaultNewsResponse
import com.instantsystem.android.feature.news.data.entity.SearchEverythingRequest
import com.instantsystem.android.feature.news.data.entity.TopHeadlinesRequest

// Remote News Repository
class NewsRemoteRepository(
    private val apiService: NewsApiService
) : NewsRepository {

    override suspend fun topHeadlines(topHeadlinesRequest: TopHeadlinesRequest): DefaultNewsResponse? {
        return apiService.topHeadlines(topHeadlinesRequest)
    }

    override suspend fun everything(searchEverythingRequest: SearchEverythingRequest): DefaultNewsResponse? {
        return apiService.everything(searchEverythingRequest)
    }
}
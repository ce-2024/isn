package com.instantsystem.android.feature.news.data.repository

import com.instantsystem.android.feature.news.data.entity.DefaultNewsResponse
import com.instantsystem.android.feature.news.data.entity.TopHeadlinesRequest

interface NewsRepository {
    suspend fun topHeadlines(topHeadlinesRequest: TopHeadlinesRequest): DefaultNewsResponse?
}
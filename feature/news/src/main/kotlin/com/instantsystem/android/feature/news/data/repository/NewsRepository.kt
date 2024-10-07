package com.instantsystem.android.feature.news.data.repository

import com.instantsystem.android.feature.news.data.entity.TopHeadlinesRequest
import com.instantsystem.android.feature.news.data.entity.TopHeadlinesResponse

interface NewsRepository {
    suspend fun topHeadlines(topHeadlinesRequest: TopHeadlinesRequest): TopHeadlinesResponse?
}
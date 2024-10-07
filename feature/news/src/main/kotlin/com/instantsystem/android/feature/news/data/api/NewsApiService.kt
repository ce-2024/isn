package com.instantsystem.android.feature.news.data.api

import com.instantsystem.android.feature.news.data.entity.TopHeadlinesRequest
import com.instantsystem.android.feature.news.data.entity.TopHeadlinesResponse

interface NewsApiService {

    suspend fun topHeadlines(topHeadlinesRequest: TopHeadlinesRequest): TopHeadlinesResponse?

    companion object {
        private const val BASE_URL = "https://newsapi.org"
        internal const val TOP_HEADLINES = "$BASE_URL/v2/top-headlines"

        // Pagination
        internal const val PAGE = "page"
        internal const val COUNTRY = "country"
        internal const val PAGE_SIZE = "pageSize"
        internal const val MAX_PER_PAGE = 5
        internal const val SUCCESS_SERVER_RESULT_RESPONSE = "ok"

    }
}
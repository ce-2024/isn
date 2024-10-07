package com.instantsystem.android.feature.news.data.api

import com.instantsystem.android.feature.news.data.entity.TopHeadlinesRequest
import com.instantsystem.android.feature.news.data.entity.TopHeadlinesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

// Remote News ApiService implementation
class NewsRemoteApiService(
    private val newsHttpClient: HttpClient
) : NewsApiService {

    override suspend fun topHeadlines(topHeadlinesRequest: TopHeadlinesRequest): TopHeadlinesResponse? {
        return newsHttpClient.get(NewsApiService.TOP_HEADLINES) {
            parameter(NewsApiService.PAGE, topHeadlinesRequest.page)
            parameter(NewsApiService.COUNTRY, topHeadlinesRequest.country)
            parameter(
                NewsApiService.PAGE_SIZE,
                topHeadlinesRequest.pageSize?.coerceAtMost(NewsApiService.MAX_PER_PAGE)
            )
        }.body()
    }

}

package com.instantsystem.android.feature.news.domain.interactor

import com.instantsystem.android.feature.news.data.entity.SearchEverythingRequest
import com.instantsystem.android.feature.news.data.repository.NewsRepository
import com.instantsystem.android.feature.news.domain.paging.NewsSearchPagingSource

/**
 * Search News Use Case
 * Where any business logic for searching news is located
 * like transforming and validate any parameters before passing to the repository
 */
class SearchNewsUseCase(private val repository: NewsRepository) {

    operator fun invoke(param: SearchQuery): NewsSearchPagingSource {
        val query = SearchEverythingRequest(
            query = param.query,
            searchIn = param.searchIn,
            sources = param.sources,
            excludeDomains = param.excludeDomains,
            language = param.language,
        )
        return NewsSearchPagingSource(query, repository)
    }
}

data class SearchQuery(
    val query: String = "",
    val searchIn: String = "",
    val sources: String = "",
    val excludeDomains: String = "",
    val language: String = "",
    val sortBy: String = ""
)
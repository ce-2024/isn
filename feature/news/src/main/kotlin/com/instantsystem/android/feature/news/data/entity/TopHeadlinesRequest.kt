package com.instantsystem.android.feature.news.data.entity


data class TopHeadlinesRequest(
    //The 2-letter ISO 3166-1 code of the country
    val country: String,
    //The category you want to get headlines
    val category: String? = null,
    //A comma-seperated string of identifiers for the news sources or blogs
    val sources: String? = null,
    // Keywords or a phrase to search for.
    val query: String? = null,
    // The number of results to return per page (request). 20 is the default, 100 is the maximum.
    val pageSize: Int? = null,
    // Use this to page through the results if the total results found is greater than the page size.
    val page: Int? = null,
)

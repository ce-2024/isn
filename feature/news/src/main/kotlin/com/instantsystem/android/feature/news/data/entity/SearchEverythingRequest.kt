package com.instantsystem.android.feature.news.data.entity

data class SearchEverythingRequest(
    // Keywords or phrases to search for in the article title and body.
    val query: String = "",
    // The possible options are: title description content
    val searchIn: String = "title,description,content",
    //A comma-seperated string of identifiers (maximum 20) for the news sources or blogs you want headlines from
    val sources: String = "",
    //A comma-seperated string of domains to restrict the search to.
    // (eg bbc.co.uk, techcrunch.com, engadget.com)
    val domain: String = "",
    // A comma-seperated string of domains to remove from the results.
    val excludeDomains: String = "",
    // A date and optional time for the oldest article allowed. This should be in ISO 8601 format
    val from: String = "",
    // A date and optional time for the newest article allowed. This should be in ISO 8601 format
    val to: String = "",
    // The 2-letter ISO-639-1 code of the language you want to get headlines for.
    // Possible options: ar de en es fr he it nl no pt ru sv ud zh
    val language: String = "",
    // The order to sort the articles in. Possible options: relevancy, popularity, publishedAt
    val sortBy: String = "publishedAt",
    // The number of results to return per page. Default: 100. Maximum: 100
    val pageSize: Int = 5,
    // Use this to page through the results. Default: 1
    val page: Int? = 1,
)

fun SearchEverythingRequest.toQueryParameters(): String {
    return listOf(
        "q" to query,
        "searchIn" to searchIn,
        "sources" to sources,
        "domain" to domain,
        "excludeDomains" to excludeDomains,
        "from" to from,
        "to" to to,
        "sortBy" to sortBy,
        "pageSize" to pageSize.toString(),
        "page" to page.toString(),
    ).joinToString("&") { (key, value) -> "$key=$value" }
}
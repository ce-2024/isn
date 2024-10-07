package com.instantsystem.android.feature.news.domain.model

data class NewsArticle(
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String
)
package com.instantsystem.android.feature.news.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    @SerialName("source") var source: Source? = Source(),
    @SerialName("author") var author: String? = null,
    @SerialName("title") var title: String? = null,
    @SerialName("description") var description: String? = null,
    @SerialName("url") var url: String? = null,
    @SerialName("urlToImage") var urlToImage: String? = null,
    @SerialName("publishedAt") var publishedAt: String? = null,
    @SerialName("content") var content: String? = null

)

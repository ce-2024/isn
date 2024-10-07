package com.instantsystem.android.feature.news.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopHeadlinesResponse(
    @SerialName("status") var status: String? = null,
    @SerialName("totalResults") var totalResults: Int? = null,
    @SerialName("articles") var articles: ArrayList<Articles> = arrayListOf()
)

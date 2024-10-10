package com.instantsystem.android.feature.news.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class NewsArticle(
    val title: String,
    val publishedAt: String,
    val source: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val content: String,
    val author: String
) : Parcelable
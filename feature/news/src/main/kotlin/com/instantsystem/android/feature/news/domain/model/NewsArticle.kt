package com.instantsystem.android.feature.news.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

private const val YMD_DATE_PATTERN = "yyyy MMM dd"

// 2024-10-12T18:15:42Z
private const val ARTICLE_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"

@Parcelize
data class NewsArticle(
    val id: String,
    val title: String,
    val publishedAt: String,
    val source: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val content: String,
    val author: String
) : Parcelable {
    val date: String
        get() = formatDate(publishedAt)
}

fun formatDate(dateString: String): String = try {
    val formatter = DateTimeFormatter.ofPattern(ARTICLE_DATE_PATTERN).withZone(ZoneOffset.UTC)
    val dateFormatter = DateTimeFormatter.ofPattern(YMD_DATE_PATTERN)
    val dateTime = ZonedDateTime.parse(dateString, formatter)
    dateTime.format(dateFormatter)
} catch (exception: Exception) {
    dateString
}

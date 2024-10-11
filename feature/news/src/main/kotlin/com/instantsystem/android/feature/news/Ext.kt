package com.instantsystem.android.feature.news

import com.instantsystem.android.feature.news.data.entity.Article
import com.instantsystem.android.feature.news.domain.model.NewsArticle


const val MAX_CONTENT_LENGTH = 200

fun List<Article>?.toDomain(): List<NewsArticle> {
    return this?.filterNot {
        it.title.isNullOrBlank() || it.urlToImage.isNullOrBlank()
    }?.map {
        NewsArticle(
            title = it.title.orEmpty(),
            description = it.description.orEmpty(),
            url = it.url.orEmpty(),
            urlToImage = it.urlToImage.orEmpty(),
            content = it.content?.take(MAX_CONTENT_LENGTH).orEmpty(),
            author = it.author.orEmpty(),
            publishedAt = it.publishedAt.orEmpty(),
            source = it.source?.name.orEmpty()
        )
    } ?: emptyList()
}
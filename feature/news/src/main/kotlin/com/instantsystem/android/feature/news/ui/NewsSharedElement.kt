package com.instantsystem.android.feature.news.ui

data class NewsSharedElementKey(
    val id: String,
    val type: NewsSharedElementType
)

enum class NewsSharedElementType {
    TITLE,
    IMAGE
}
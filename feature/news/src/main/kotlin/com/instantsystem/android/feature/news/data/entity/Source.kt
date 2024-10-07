package com.instantsystem.android.feature.news.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Source(
    @SerialName("id") var id: String? = null,
    @SerialName("name") var name: String? = null
)

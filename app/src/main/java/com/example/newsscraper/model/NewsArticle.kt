package com.example.newsscraper.model

import androidx.compose.runtime.Immutable
import java.util.Date

@Immutable
data class NewsArticle(
    val title: String,
    val author: String,
    val imageUrl: String,
    val uploadDate: Date,
    val likesCount: Int,
    val articleUrl: String,
)
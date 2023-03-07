package com.example.newsscraper.model

import androidx.compose.runtime.Immutable
import java.util.Date

@Immutable
data class NewsArticle(
    val text: String,
    val name: String,
    val imageUrl: String,
    val createdDate: String,
    val favorites: Int,
)
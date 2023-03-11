package com.example.newsscraper.model

import androidx.compose.runtime.Immutable

@Immutable
data class NewsArticle(
    val title: String,
    val author: String,
    val imageUrl: String,
    val createdDate: String,
    val likes: Int,
)
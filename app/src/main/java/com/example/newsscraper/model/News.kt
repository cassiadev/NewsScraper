package com.example.newsscraper.model

import androidx.compose.runtime.Immutable
import java.util.Date

@Immutable
data class News(
    val id: Long,
    val title: String,
    val writer: String,
    val imageUrl: String,
    val uploadDate: Date,
    val likeCount: Long,
)
package com.example.newsscraper

import android.content.res.Configuration
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsscraper.model.NewsArticle
import kotlinx.coroutines.*
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class NewsViewModel : ViewModel() {
//    private val _data = mutableStateOf("Data to be scraped in")
//    val data: State<String> = _data

    private val _articles = mutableStateOf(emptyList<NewsArticle>())
    val articles: MutableState<List<NewsArticle>> = _articles

    private val url = "https://qiita.com/api/v2?token=67f6b095940f27470c91158406f003f5b74732b6"

    suspend fun searchNews(keyword: String) {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response: Response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val news = response.body?.string() ?: ""
                
            }
        }
    }
}
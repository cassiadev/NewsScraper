package com.example.newsscraper

import android.content.res.Configuration
import android.nfc.Tag
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
import okio.IOException
import org.jsoup.Jsoup

class NewsViewModel : ViewModel() {
    private val _articles = mutableStateOf(emptyList<NewsArticle>())
    var articles: MutableState<List<NewsArticle>> = _articles

    private val url = "https://qiita.com"
    private val token = "67f6b095940f27470c91158406f003f5b74732b6"

    suspend fun searchNews(keyword: String): List<NewsArticle> = withContext(Dispatchers.IO) {
        try {
            val jsoupConnection = Jsoup.connect(url).header("Authorization", "Bearer $token")
            val jsoupDocument = jsoupConnection.get()
            Log.d(url, jsoupDocument.body().toString())
            /*TODO Break down jsoupDocument to be fit into List<NewsArticle>*/
            return@withContext emptyList<NewsArticle>() // return@withContext articles
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext emptyList<NewsArticle>()
        }
    }
}
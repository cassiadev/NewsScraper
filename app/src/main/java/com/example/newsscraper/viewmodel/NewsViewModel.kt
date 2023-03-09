package com.example.newsscraper

import android.content.res.Configuration
import android.nfc.Tag
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
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
    val articles: MutableState<List<NewsArticle>> = _articles

    private val url = "https://qiita.com"
    private val token = "67f6b095940f27470c91158406f003f5b74732b6"

    suspend fun searchNews(keyword: String) = withContext(Dispatchers.IO) {
        val articlesMutable: MutableList<NewsArticle> = arrayListOf()
        try {
            val jsoupConnection = Jsoup.connect(url).header("Authorization", "Bearer $token")
            val jsoupDocument = jsoupConnection.get()

            for (i in 0 until jsoupDocument.select("article.style-1w5j724").count()) {
                val jsoupArticle = jsoupDocument.select("article.style-1w5j724").get(i)

                val authorId = jsoupArticle.select("p.style-mpxsrl").first()!!.text()
                val authorName = jsoupArticle.select("a").select("header.style-fv3lde").first()?.text() ?: ""
                val authorOrganization = jsoupArticle.select("p").select("span.style-5hz1ob").first()?.attr("href") ?: ""

                val article: NewsArticle = NewsArticle(
                    title = jsoupArticle.select("a.style-14xjz8f").first()!!.text(),
                    author = authorId.substring(1, authorId.length)
                            + authorName
                            + authorOrganization,
                    imageUrl = jsoupArticle.select("img.style-1u747qi").first()!!.attr("src"),
                    createdDate = jsoupArticle.select("span.style-1elrt2j").first()!!.text(),
                    likes = jsoupArticle.select("span.style-176d67y").first()!!.text().toInt(),
                )
                articlesMutable.add(article)
            }
            _articles.value = articlesMutable.toList()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
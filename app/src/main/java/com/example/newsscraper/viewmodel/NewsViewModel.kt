package com.example.newsscraper

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsscraper.model.NewsArticle
import kotlinx.coroutines.*
import org.jsoup.Jsoup

class NewsViewModel : ViewModel() {
    private val _articles = mutableStateListOf<NewsArticle>()
    val articles: List<NewsArticle> = _articles

    private val url = "https://qiita.com"
    private val token = "67f6b095940f27470c91158406f003f5b74732b6"

    private var isLoading = false
    private var page = 1

    fun loadItems(keyword: String) {
        if (!isLoading) {
            viewModelScope.launch {
                isLoading = true
                val newArticles = searchNews(keyword, page)
                _articles.addAll(newArticles)
                isLoading = false
                page++
            }
        }
    }

    private suspend fun searchNews(keyword: String, page: Int): List<NewsArticle> = withContext(Dispatchers.IO) {
        val articlesMutable: MutableList<NewsArticle> = arrayListOf()
        try {
            /*TODO Use library such as Moshi to obtain json response from https://qiita.com/api/v2/items, not jsoup which is for scraping html*/
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

                // Filter each article with keyword
                if (!article.title.contains(keyword)
                    && !article.author.contains(keyword)
                    && !article.createdDate.contains(keyword)
                    && !keyword.contains(article.likes.toString())) continue

                articlesMutable.add(article)
            }
            return@withContext articlesMutable
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext emptyList()
        }
    }
}
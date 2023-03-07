package com.example.newsscraper

import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainViewModel : ViewModel() {
    private val _data = mutableStateOf("Data to be scraped in")
    val data: State<String> = _data

    fun startScraping(searchKeyword: String) {
        val apiUrl = "https://qiita.com/api/v2/items?page=1&per_page=20"
        val token = "67f6b095940f27470c91158406f003f5b74732b6"

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val apiService = ApiService()
                val response = apiService.get(apiUrl, token)
                Log.d("ApiService", response)
            } catch (e: Exception) {
                Log.e("ApiService", e.toString())
            }
        }
        _data.value = "Value of data to be scraped in"
    }
}

class ApiService {
    private val client = OkHttpClient()

    suspend fun get(url: String, token: String): String {
        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer $token")
            .build()

        return withContext(Dispatchers.IO) {
            val response: Response = client.newCall(request).execute()
            return@withContext response.body?.string() ?: ""
        }
    }
}
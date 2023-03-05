package com.example.newsscraper

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _data = mutableStateOf("Data to be scraped in")
    val data: State<String> = _data

    fun startScraping(searchKeyword: String) {
        _data.value = "Value of data to be scraped in"
    }
}
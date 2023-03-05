package com.example.newsscraper

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _data = mutableStateOf("Data to be shown")
    val data: State<String> = _data

    fun startScraping() {
        _data.value = "Data to be scraped in"
    }
}
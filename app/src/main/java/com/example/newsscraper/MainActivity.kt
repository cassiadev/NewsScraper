package com.example.newsscraper

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsscraper.ui.theme.NewsScraperTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ScraperScreen()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancel all coroutines running in this Activity's scope
        CoroutineScope(Dispatchers.Main).cancel()
    }
}

@Composable
fun ScraperScreen() {
    val viewModel = viewModel<MainViewModel>()
    var searchKeyword by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.padding(horizontal = 50.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            text = "News Scraper",
        )
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            modifier = Modifier.align(Alignment.End),
            fontWeight = FontWeight.Light,
            fontSize = 10.sp,
            text = "Source from: qiita.com",
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchKeyword,
            onValueChange = {newKeyword -> searchKeyword = newKeyword},
            label = { Text(text = "Input Keyword")}
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            viewModel.startScraping(searchKeyword)
        }) {
            Text(text = "Search")
        }
    }
}

@Preview(
    name = "light",
    group = "theme",
    showBackground = true,
    showSystemUi = true,
)
@Preview(
    name = "dark",
    group = "theme",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "tablet",
    showBackground = true,
    showSystemUi = true,
    device = Devices.TABLET,
)
@Composable
fun DefaultPreview() {
    ScraperScreen()
}
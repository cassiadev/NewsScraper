package com.example.newsscraper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.newsscraper.model.NewsArticle
import kotlinx.coroutines.*

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
                    NewsScraperApp()
                }
            }
        }
    }
}

@Composable
fun NewsScraperApp() {
    val viewModel = viewModel<NewsViewModel>()
    var searchKeyword by remember { mutableStateOf("") }
    var articles by remember { mutableStateOf<List<NewsArticle>>(emptyList()) }
    val lazyListState = rememberLazyListState()
    val isReachedToListEnd by remember {
        derivedStateOf {
            lazyListState.layoutInfo.visibleItemsInfo.size < lazyListState.layoutInfo.totalItemsCount
                    && lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == lazyListState.layoutInfo.totalItemsCount
        }
    }

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
        Button(
            onClick = {
                viewModel.viewModelScope.launch {
                    viewModel.loadItems(searchKeyword)
                    articles = viewModel.articles
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
                Text(text = "Search")
        }

        if (articles.isNotEmpty()) {
            LazyColumn {
                items(articles) {newsItem ->
                    NewsItem(newsItem)
                }
            }

            // Try loading additional articles when the list is reached to an end
            val coroutineScope = rememberCoroutineScope()
            LaunchedEffect(coroutineScope) {
                if (isReachedToListEnd) viewModel.loadItems(keyword = searchKeyword)
            }
        }
    }
}

@Composable
fun NewsItem(article: NewsArticle) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row() {
                Image(
                    painter = rememberAsyncImagePainter(model = article.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(top = 5.dp, bottom = 10.dp)
                        .aspectRatio(1f / 1f)
                        .clip(CircleShape)
                )
                Column() {
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = article.author,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 50.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Date",
                    modifier = Modifier.size(10.dp)
                )
                Text(
                    text = article.createdDate,
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(start = 2.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Likes",
                    modifier = Modifier.size(10.dp)
                )
                Text(
                    text = article.likes.toString(),
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(start = 2.dp)
                )
            }
        }
    }
}

@Preview(
    group = "theme",
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun DefaultPreview() {
    NewsScraperApp()
}

@Preview
@Composable
fun NewsItemPreview() {
    NewsItem(article = NewsArticle(
        title = "Title",
        author = "author123(Author)???AuthorCorporation",
        imageUrl = "",
        createdDate = "2023-01-01",
        likes = 100
    ))
}
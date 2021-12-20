package com.sw.newsapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sw.newsapi.composables.Layout
import com.sw.newsapi.ui.theme.NewsApiTheme
import com.sw.newsapi.viewmodels.NewsApiViewModel

class MainActivity : ComponentActivity() {
    val newsViewModel by viewModels<NewsApiViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsApiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Layout(
                        apple = newsViewModel.appleNews,
                        loadingApple = newsViewModel.loadingApple,
                        refreshApple = newsViewModel::refreshAppleNews,
                        refreshingApple = newsViewModel.refreshingApple,
                        google = newsViewModel.googleNews,
                        loadingGoogle = newsViewModel.loadingGoogle,
                        refreshingGoogle= newsViewModel.refreshingGoogle,
                        refreshGoogle= newsViewModel::refreshGoogleNews,
                        facebook = newsViewModel.facebookNews,
                        refreshFacebook = newsViewModel::refreshFacebookNews,
                        loadingFacebook = newsViewModel.loadingFacebook,
                        refreshingFacebook = newsViewModel.refreshingFacebook,
                    )
                    LaunchedEffect(Unit) {
                        newsViewModel.fetchAll()
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewsApiTheme {
        Greeting("Android")
    }
}
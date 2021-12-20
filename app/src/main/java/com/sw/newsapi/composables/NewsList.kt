package com.sw.newsapi.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.sw.newsapi.api.model.NewsResponse

@Composable
fun NewsList(
    navController: NavHostController,
    subject: String,
    news: NewsResponse,
    loading: Boolean,
    refreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var progress by remember { mutableStateOf(0.1f) }
    if (loading && !refreshing) {
        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                progress = animatedProgress
            )
        }
    } else if (news?.articles != null)
        SwipeRefresh(
            state = rememberSwipeRefreshState(refreshing),
            onRefresh = { onRefresh() },
        ) {
            LazyColumn {
                items(news?.articles) { article ->
                    NewsRow(
                        navController = navController,
                        subject,
                        article
                    )
                }
            }
        } else {
        Text(
            "Empty List",
        )
    }
}

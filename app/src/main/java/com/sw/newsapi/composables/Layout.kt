package com.sw.newsapi.composables


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sw.newsapi.R
import com.sw.newsapi.api.model.Article
import com.sw.newsapi.api.model.NewsResponse
import java.net.URLDecoder

@Composable
fun Layout(
    apple: NewsResponse,
    loadingApple: Boolean,
    refreshApple: () -> Unit,
    refreshingApple: Boolean,
    google: NewsResponse,
    loadingGoogle: Boolean,
    refreshGoogle: () -> Unit,
    refreshingGoogle: Boolean,
    facebook: NewsResponse,
    loadingFacebook: Boolean,
    refreshFacebook: () -> Unit,
    refreshingFacebook: Boolean,
) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            TopAppBar(title = {
                Text(text = "News Api Reader - ${currentDestination?.route}")
            },
                actions = {
                })
        },
        bottomBar = {
            BottomNavigation() {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                BottomNavigationItem(
                    icon = {
                        Icon(painter = painterResource(id = R.drawable.apple), contentDescription = "Apple")
                    },
                    selected = currentDestination?.hierarchy?.any { it.route == "apple" } == true,
                    onClick = { navController.navigate("apple") })
                BottomNavigationItem(
                    icon = {
                        Icon(painter = painterResource(id = R.drawable.google), contentDescription = "Google")
                    },
                    selected = currentDestination?.hierarchy?.any { it.route == "google" } == true,
                    onClick = { navController.navigate("google") })
                BottomNavigationItem(
                    icon = {
                        Icon(painter = painterResource(id = R.drawable.facebook), contentDescription = "Facebook")
                    },
                    selected = currentDestination?.hierarchy?.any { it.route == "facebook" } == true,
                    onClick = { navController.navigate("facebook") })
            }
        },
    ) { innerPadding ->
        NavHost(navController, startDestination = "apple") {
            composable("apple") { backStackEntry ->
                BodyContent(
                    navController = navController,
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(8.dp),
                    title = "apple",
                    articles = apple,
                    loading = loadingApple,
                    refreshing = refreshingApple,
                    onRefresh = refreshApple
                )
            }
            composable("google") { backStackEntry ->
                BodyContent(
                    navController = navController,
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(8.dp),
                    title = "google",
                    articles = google,
                    loading = loadingGoogle,
                    refreshing = refreshingGoogle,
                    onRefresh = refreshGoogle
                )
            }
            composable("facebook") { backStackEntry ->
                BodyContent(
                    navController = navController,
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(8.dp),
                    title = "facebook",
                    articles = facebook,
                    loading = loadingFacebook,
                    refreshing = refreshingFacebook,
                    onRefresh = refreshFacebook
                )
            }
            composable(
                "newsDetails/{articleId}/{subject}",
            ) { backStackEntry ->
                val subject = backStackEntry.arguments?.getString("subject")
                val articleId =
                    URLDecoder.decode(backStackEntry.arguments?.getString("articleId"), "utf-8")
                var article = Article()
                if (subject == "apple") {
                    article = apple.articles.first { article -> article.url == articleId }
                } else if (subject == "google") {

                    article = google.articles.first { article -> article.url == articleId }
                } else if (subject == "facebook") {

                    article = facebook.articles.first { article -> article.url == articleId }
                }
                NewsDetails(
                    navController = navController,
                    article = article
                )
            }
        }
    }
}

@Composable
fun BodyContent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    title: String,
    articles: NewsResponse,
    loading: Boolean,
    refreshing: Boolean,
    onRefresh: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        NewsList(
            navController = navController,
            subject = title,
            articles,
            loading,
            refreshing,
            onRefresh
        )
    }
}



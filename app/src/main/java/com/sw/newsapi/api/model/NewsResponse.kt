package com.sw.newsapi.api.model

data class NewsResponse(
    val status: String = "",
    val totalResults: Int = 0,
    val articles: List<Article> = listOf<Article>()
)

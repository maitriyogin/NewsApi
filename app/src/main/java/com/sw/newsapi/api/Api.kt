package com.sw.newsapi.api

import com.sw.newsapi.NewsApp
import com.sw.newsapi.api.model.NewsResponse
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


val APIKEY = "821ea0956fc74409a224fd208d5e0152";

private fun getLogger(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
    return logging
}

/*
curl https://newsapi.org/v2/everything -G \
    -d q=Apple \
    -d from=2021-12-18 \
    -d sortBy=popularity \
    -d apiKey=821ea0956fc74409a224fd208d5e0152
 */

val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
val cache = Cache(NewsApp.context!!.cacheDir, cacheSize)

private const val BASE_URL = "https://newsapi.org/v2/"
private val service: INewsApi by lazy {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(getLogger())
        .addInterceptor(offlineInterceptor)
        .addNetworkInterceptor(onlineInterceptor)
        .cache(cache)
        .build()
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    retrofit.create(INewsApi::class.java)
}

fun getApi() = service

interface INewsApi {
    @GET("everything")
    suspend fun appleStories(
        @Query("q") q: String = "Apple",
        @Query("from") from: String = "2021-12-18",
        @Query("sortBy") sortBy: String = "popularity",
        @Query("apiKey") apiKey: String = APIKEY
    ): NewsResponse

    @GET("everything")
    suspend fun googleStories(
        @Query("q") q: String = "Google",
        @Query("from") from: String = "2021-12-18",
        @Query("sortBy") sortBy: String = "popularity",
        @Query("apiKey") apiKey: String = APIKEY
    ): NewsResponse

    @GET("everything")
    suspend fun facebookStories(
        @Query("q") q: String = "Facebook",
        @Query("from") from: String = "2021-12-18",
        @Query("sortBy") sortBy: String = "popularity",
        @Query("apiKey") apiKey: String = APIKEY
    ): NewsResponse
}
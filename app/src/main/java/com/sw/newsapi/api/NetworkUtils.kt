package com.sw.newsapi.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.sw.newsapi.NewsApp
import okhttp3.Interceptor
import okhttp3.Request


var onlineInterceptor = Interceptor { chain ->
    val response = chain.proceed(chain.request())
    val maxAge = 60 // read from cache for 60 seconds even if there is internet connection
    response.newBuilder()
        .header("Cache-Control", "public, max-age=$maxAge")
        .removeHeader("Pragma")
        .build()
}
var offlineInterceptor = Interceptor { chain ->
    var request: Request = chain.request()
    if (!isConnected(NewsApp.context!!)) {
        val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
        request = request.newBuilder()
            .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
            .removeHeader("Pragma")
            .build()
    }
    chain.proceed(request)
}

fun isConnected(context:Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
    println("------ is connected ${isConnected}")
    return isConnected
}

package com.sw.newsapi

import android.app.Application
import android.content.Context

class NewsApp : Application() {
    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    companion object {
        var instance: NewsApp? = null
            private set
        val context: Context?
            get() = instance
    }
}
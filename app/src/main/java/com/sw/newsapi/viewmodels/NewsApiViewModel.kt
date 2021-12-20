package com.sw.newsapi.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sw.newsapi.api.getApi
import com.sw.newsapi.api.model.NewsResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsApiViewModel : ViewModel() {
    var loadingApple by mutableStateOf(false)
    var loadingGoogle by mutableStateOf(false)
    var loadingFacebook by mutableStateOf(false)
    var refreshingApple by mutableStateOf(false)
    var refreshingGoogle by mutableStateOf(false)
    var refreshingFacebook by mutableStateOf(false)
    var searchApplJob: Job? = null
    var searchGoogleJob: Job? = null
    var searchFacebookJob: Job? = null
    var appleNews by mutableStateOf(NewsResponse())
    var googleNews by mutableStateOf(NewsResponse())
    var facebookNews by mutableStateOf(NewsResponse())

    open fun searchAppleNews() {
        searchApplJob?.cancel()
        loadingApple = true
        searchApplJob = viewModelScope.launch {
            appleNews = getApi().appleStories()
            loadingApple = false
            refreshingApple = false
        }
    }

    open fun refreshAppleNews() {
        refreshingApple = true
        searchAppleNews()
    }

    open fun searchGoogleNews() {
        searchGoogleJob?.cancel()
        loadingGoogle = true
        searchGoogleJob = viewModelScope.launch {
            delay(500)
            googleNews = getApi().googleStories()
            loadingGoogle = false
            refreshingGoogle = false
        }
    }

    open fun refreshGoogleNews() {
        refreshingGoogle = true
        searchGoogleNews()
    }

    open fun searchFacebookNews() {
        searchFacebookJob?.cancel()
        loadingFacebook = true
        searchFacebookJob = viewModelScope.launch {
            delay(500)
            facebookNews = getApi().facebookStories()
            loadingFacebook = false
            refreshingFacebook = false

        }
    }

    open fun refreshFacebookNews() {
        refreshingFacebook = true
        searchFacebookNews()
    }

    open fun fetchAll() {
        searchAppleNews();
        searchFacebookNews();
        searchGoogleNews();
    }
}



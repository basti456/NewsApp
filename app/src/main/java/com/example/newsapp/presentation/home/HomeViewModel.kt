package com.example.newsapp.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    private val _selectedFilter = mutableStateOf("All")
    val selectedFilter: State<String> get() = _selectedFilter

    fun setFilter(filter: String) {
        _selectedFilter.value = filter
    }


    val news = newsUseCases.getNews(
        sources = listOf(
            "bbc-news",
            "abc-news",
            "al-jazeera-english",
            "abp",
            "hindustan-times",
            "india-today"
        )
    )
        .cachedIn(viewModelScope)
}

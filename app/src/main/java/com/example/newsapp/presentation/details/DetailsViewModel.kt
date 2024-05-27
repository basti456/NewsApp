package com.example.newsapp.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    var sideEffect by mutableStateOf<String?>(null)
        private set

    private val _isBookmarked = MutableStateFlow(BookmarkState())
    val isBookmarked: StateFlow<BookmarkState> = _isBookmarked.asStateFlow()


    init {
        val arg = savedStateHandle.get<String>("url").toString()
        getIsBookmarked(arg)
    }

    private fun getIsBookmarked(arg: String) {
        viewModelScope.launch {
            val isBookmarked = newsUseCases.isBookmarkedArticle(arg)
            if (isBookmarked != 0) {
                _isBookmarked.update {
                    it.copy(isBookmarked = true)
                }
            } else {
                _isBookmarked.update {
                    it.copy(isBookmarked = false)
                }
            }
        }
    }

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.UpsertDeleteArticle -> {
                viewModelScope.launch {
                    val article = newsUseCases.selectArticle(event.article.url)
                    if (article == null) {
                        upsertArticle(event.article)
                        _isBookmarked.update {
                            it.copy(isBookmarked = true)
                        }
                    } else {
                        deleteArticle(event.article)
                        _isBookmarked.update {
                            it.copy(isBookmarked = false)
                        }
                    }
                }
            }

            is DetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }


        }
    }


    private suspend fun deleteArticle(article: Article) {
        newsUseCases.deleteArticle(article)
        sideEffect = "Bookmark Removed"
    }

    private suspend fun upsertArticle(article: Article) {
        newsUseCases.upsertArticle(article)
        sideEffect = "Bookmark Added"
    }

}

data class BookmarkState(
    val isBookmarked: Boolean = false
)
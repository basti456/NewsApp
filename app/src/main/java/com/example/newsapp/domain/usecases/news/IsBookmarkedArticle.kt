package com.example.newsapp.domain.usecases.news

import com.example.newsapp.domain.repository.NewsRepository

class IsBookmarkedArticle(private val newsRepository: NewsRepository) {

    suspend operator fun invoke(url: String): Int {
        if (newsRepository.isBookmarked(url) == null) {
            return 0
        }
        return newsRepository.isBookmarked(url)!!
    }

}
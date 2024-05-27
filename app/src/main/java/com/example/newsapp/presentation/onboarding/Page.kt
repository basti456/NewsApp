package com.example.newsapp.presentation.onboarding

import androidx.annotation.DrawableRes
import com.example.newsapp.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = "Onboarding 1",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry",
        image = R.drawable.onboarding1
    ),
    Page(
        title = "Onboarding 2",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry",
        image = R.drawable.onboarding2
    ),
    Page(
        title = "Onboarding 3",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry",
        image = R.drawable.onboarding3
    ),
)
package com.example.cyberpath.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(

    @SerializedName("status")
    val status: String?,

    @SerializedName("news")
    val news: List<NewsArticle>?
)
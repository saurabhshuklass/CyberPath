package com.example.cyberpath.model

import com.google.gson.annotations.SerializedName

data class NewsArticle(

    @SerializedName("id")
    val id: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("image")
    val image: String?,

    @SerializedName("published")
    val published: String?,

    @SerializedName("author")
    val author: String?,

    @SerializedName("language")
    val language: String?,

    @SerializedName("category")
    val category: List<String>?,

    @SerializedName("source")
    val source: String?
)
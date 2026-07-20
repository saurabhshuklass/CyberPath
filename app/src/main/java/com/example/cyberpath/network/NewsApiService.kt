package com.example.cyberpath.network

import com.example.cyberpath.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v1/search")
    suspend fun getCyberSecurityNews(
        @Query("apiKey") apiKey: String,
        @Query("keywords") keywords: String = "cybersecurity",
        @Query("language") language: String = "en"
    ): Response<NewsResponse>

}
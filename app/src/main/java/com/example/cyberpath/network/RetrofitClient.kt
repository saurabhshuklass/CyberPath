package com.example.cyberpath.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // ===========================
    // Google Safe Browsing API
    // ===========================
    val api: SafeBrowsingApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://safebrowsing.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SafeBrowsingApi::class.java)
    }

    // ===========================
    // Cyber Security News API
    // ===========================
    val newsApi: NewsApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.currentsapi.services/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }
}
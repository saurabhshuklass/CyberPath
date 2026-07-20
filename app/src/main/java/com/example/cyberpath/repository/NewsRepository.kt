package com.example.cyberpath.repository

import android.util.Log
import com.example.cyberpath.model.NewsArticle
import com.example.cyberpath.network.RetrofitClient

class NewsRepository {

    suspend fun getCyberSecurityNews(apiKey: String): List<NewsArticle> {

        return try {

            val response = RetrofitClient.newsApi.getCyberSecurityNews(apiKey)

            Log.d("NEWS_API", "Code: ${response.code()}")

            if (response.isSuccessful) {

                val articles = response.body()?.news ?: emptyList()

                Log.d("NEWS_API", "Articles Received: ${articles.size}")

                val keywords = listOf(
                    "cyber",
                    "cybersecurity",
                    "security",
                    "hack",
                    "hacker",
                    "hacking",
                    "malware",
                    "ransomware",
                    "phishing",
                    "exploit",
                    "vulnerability",
                    "zero-day",
                    "data breach",
                    "virus",
                    "trojan"
                )

                val filteredArticles = articles.filter { article ->

                    val text = buildString {
                        append(article.title ?: "")
                        append(" ")
                        append(article.description ?: "")
                    }.lowercase()

                    keywords.any { keyword ->
                        text.contains(keyword)
                    }
                }

                Log.d("NEWS_API", "Filtered Articles: ${filteredArticles.size}")

                filteredArticles

            } else {

                Log.e("NEWS_API", "Error: ${response.errorBody()?.string()}")

                emptyList()

            }

        } catch (e: Exception) {

            Log.e("NEWS_API", "Exception", e)

            emptyList()

        }
    }
}
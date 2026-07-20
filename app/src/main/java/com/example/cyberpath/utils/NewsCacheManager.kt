package com.example.cyberpath.utils

import android.content.Context
import com.example.cyberpath.model.NewsArticle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NewsCacheManager(context: Context) {

    private val prefs =
        context.getSharedPreferences("news_cache", Context.MODE_PRIVATE)

    private val gson = Gson()

    fun saveNews(news: List<NewsArticle>) {

        val json = gson.toJson(news)

        prefs.edit()
            .putString("cached_news", json)
            .apply()

    }

    fun getNews(): List<NewsArticle> {

        val json =
            prefs.getString("cached_news", null)
                ?: return emptyList()

        val type =
            object : TypeToken<List<NewsArticle>>() {}.type

        return gson.fromJson(json, type)

    }

}
package com.example.cyberpath.utils

object UrlUtils {

    private val urlRegex =
        Regex(
            "(https?|ftp)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]*[-A-Za-z0-9+&@#/%=~_|]"
        )

    fun extractUrls(text: String): List<String> {

        return urlRegex
            .findAll(text)
            .map { it.value }
            .toList()

    }
}
package com.example.cyberpath

data class UrlHistory(

    val url: String = "",

    val verdict: String = "",

    val riskScore: Int = 0,

    val scannedAt: Long = System.currentTimeMillis()

)
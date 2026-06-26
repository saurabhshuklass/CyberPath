package com.example.cyberpath

data class UrlAnalysisResult(

    val riskScore: Int,

    val verdict: String,

    val httpsSafe: Boolean,

    val shortenerFound: Boolean,

    val ipAddressFound: Boolean,

    val suspiciousKeywordFound: Boolean,

    val longUrl: Boolean,

    val recommendation: String

)
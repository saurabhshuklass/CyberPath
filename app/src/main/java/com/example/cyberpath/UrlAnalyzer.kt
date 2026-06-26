package com.example.cyberpath

import java.util.Locale

object UrlAnalyzer {

    private val shorteners = listOf(

        "bit.ly",
        "tinyurl.com",
        "goo.gl",
        "ow.ly",
        "t.co",
        "is.gd",
        "buff.ly",
        "rebrand.ly"

    )

    private val suspiciousWords = listOf(

        "login",
        "verify",
        "secure",
        "account",
        "update",
        "bank",
        "paypal",
        "confirm",
        "password",
        "signin"

    )

    private val suspiciousTlds = listOf(

        ".xyz",
        ".tk",
        ".top",
        ".gq",
        ".cf",
        ".ml"

    )

    fun analyze(url: String): UrlAnalysisResult {

        var score = 0

        val lowerUrl = url.lowercase(Locale.getDefault())

        // HTTPS Check
        val httpsSafe = lowerUrl.startsWith("https://")

        if (!httpsSafe) {
            score += 15
        }

        // URL Length
        val longUrl = lowerUrl.length > 75

        if (longUrl) {
            score += 10
        }

        // URL Shortener
        val shortenerFound =
            shorteners.any { lowerUrl.contains(it) }

        if (shortenerFound) {
            score += 20
        }

        // IP Address
        val ipRegex =
            Regex("""https?://(\d{1,3}\.){3}\d{1,3}""")

        val ipFound =
            ipRegex.containsMatchIn(lowerUrl)

        if (ipFound) {
            score += 20
        }

        // Suspicious Keywords
        val keywordFound =
            suspiciousWords.any {
                lowerUrl.contains(it)
            }

        if (keywordFound) {
            score += 15
        }

        // Multiple Hyphens
        if (lowerUrl.count { it == '-' } >= 3) {
            score += 10
        }

        // '@'
        if (lowerUrl.contains("@")) {
            score += 15
        }

        // Too Many Subdomains

        val dots = lowerUrl.count { it == '.' }

        if (dots >= 4) {
            score += 10
        }

        // Suspicious TLD

        if (suspiciousTlds.any {
                lowerUrl.endsWith(it)
            }) {
            score += 15
        }

        if (score > 100)
            score = 100

        val verdict =
            when {

                score <= 20 ->
                    "SAFE"

                score <= 40 ->
                    "LOW RISK"

                score <= 60 ->
                    "MEDIUM RISK"

                score <= 80 ->
                    "HIGH RISK"

                else ->
                    "DANGEROUS"

            }

        val recommendation =
            when (verdict) {

                "SAFE" ->
                    "This URL appears safe. Still verify before entering sensitive information."

                "LOW RISK" ->
                    "Proceed carefully and double-check the website."

                "MEDIUM RISK" ->
                    "Avoid sharing passwords or personal information."

                "HIGH RISK" ->
                    "This URL contains multiple suspicious indicators."

                else ->
                    "Do NOT visit this website. It may be malicious or phishing."

            }

        return UrlAnalysisResult(

            score,

            verdict,

            httpsSafe,

            shortenerFound,

            ipFound,

            keywordFound,

            longUrl,

            recommendation

        )

    }

}
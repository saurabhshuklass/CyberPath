package com.example.cyberpath.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun formatDate(dateString: String?): String {

        if (dateString.isNullOrEmpty())
            return ""

        return try {

            val parser = SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss Z",
                Locale.getDefault()
            )

            val date = parser.parse(dateString) ?: return ""

            val diff = Date().time - date.time

            val minutes = diff / (60 * 1000)
            val hours = diff / (60 * 60 * 1000)
            val days = diff / (24 * 60 * 60 * 1000)

            when {

                minutes < 60 ->
                    "$minutes min ago"

                hours < 24 ->
                    "$hours h ago"

                days == 1L ->
                    "Yesterday"

                else ->
                    SimpleDateFormat(
                        "dd MMM",
                        Locale.getDefault()
                    ).format(date)

            }

        } catch (e: Exception) {

            ""

        }

    }

}
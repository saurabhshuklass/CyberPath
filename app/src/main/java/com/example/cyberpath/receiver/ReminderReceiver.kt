package com.example.cyberpath.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.cyberpath.utils.NotificationUtils

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {

        when (intent.getStringExtra("TYPE")) {

            "DAILY" -> {

                NotificationUtils.showDailyReminder(context)

            }

            "QUIZ" -> {

                NotificationUtils.showQuizReminder(context)

            }

            "PRACTICAL" -> {

                NotificationUtils.showPracticalReminder(context)

            }

            "NEWS" -> {

                NotificationUtils.showNewsReminder(context)

            }

        }

    }

}
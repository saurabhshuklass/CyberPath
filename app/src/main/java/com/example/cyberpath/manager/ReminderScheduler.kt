package com.example.cyberpath.manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.cyberpath.receiver.ReminderReceiver
import java.util.Calendar

object ReminderScheduler {

    fun scheduleReminder(
        context: Context,
        type: String,
        hour: Int,
        minute: Int,
        requestCode: Int
    ) {

        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE)
                    as AlarmManager

        val intent = Intent(
            context,
            ReminderReceiver::class.java
        )

        intent.putExtra("TYPE", type)

        val pendingIntent = PendingIntent.getBroadcast(

            context,

            requestCode,

            intent,

            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE

        )

        val calendar = Calendar.getInstance().apply {

            set(Calendar.HOUR_OF_DAY, hour)

            set(Calendar.MINUTE, minute)

            set(Calendar.SECOND, 0)

            if (before(Calendar.getInstance())) {

                add(Calendar.DAY_OF_YEAR, 1)

            }

        }

        alarmManager.setRepeating(

            AlarmManager.RTC_WAKEUP,

            calendar.timeInMillis,

            AlarmManager.INTERVAL_DAY,

            pendingIntent

        )

    }

    fun cancelReminder(
        context: Context,
        requestCode: Int
    ) {

        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE)
                    as AlarmManager

        val intent = Intent(
            context,
            ReminderReceiver::class.java
        )

        val pendingIntent = PendingIntent.getBroadcast(

            context,

            requestCode,

            intent,

            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE

        )

        alarmManager.cancel(pendingIntent)

    }

}
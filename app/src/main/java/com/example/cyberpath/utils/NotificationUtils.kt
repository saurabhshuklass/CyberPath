package com.example.cyberpath.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import com.example.cyberpath.DashboardActivity
import com.example.cyberpath.R
import java.io.File

object NotificationUtils {

    // -----------------------------
    // Channels
    // -----------------------------

    private const val CERTIFICATE_CHANNEL = "certificate_channel"
    private const val REMINDER_CHANNEL = "reminder_channel"

    // -----------------------------
    // Certificate Notification
    // -----------------------------

    fun showDownloadNotification(
        context: Context,
        file: File
    ) {

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

        createCertificateChannel(manager)

        val uri = FileProvider.getUriForFile(
            context,
            context.packageName + ".provider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW)

        intent.setDataAndType(
            uri,
            "application/pdf"
        )

        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_GRANT_READ_URI_PERMISSION

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification =
            NotificationCompat.Builder(
                context,
                CERTIFICATE_CHANNEL
            )
                .setSmallIcon(R.drawable.certificate)
                .setContentTitle("Certificate Downloaded")
                .setContentText("Tap to open your certificate.")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

        manager.notify(
            101,
            notification
        )

    }

    // -----------------------------
    // Generic Reminder Notification
    // -----------------------------

    fun showReminderNotification(

        context: Context,

        title: String,

        message: String,

        notificationId: Int

    ) {

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

        createReminderChannel(manager)

        val intent = Intent(
            context,
            DashboardActivity::class.java
        )

        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent.getActivity(

            context,

            notificationId,

            intent,

            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE

        )

        val notification =
            NotificationCompat.Builder(
                context,
                REMINDER_CHANNEL
            )

                .setSmallIcon(R.drawable.ic_notification)

                .setContentTitle(title)

                .setContentText(message)

                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(message)
                )

                .setPriority(NotificationCompat.PRIORITY_HIGH)

                .setAutoCancel(true)

                .setContentIntent(pendingIntent)

                .build()

        manager.notify(
            notificationId,
            notification
        )

    }

    // -----------------------------
    // Daily Learning
    // -----------------------------

    fun showDailyReminder(context: Context) {

        showReminderNotification(

            context,

            "📚 Time to Learn!",

            "Continue your Cyber Security learning today.",

            201

        )

    }

    // -----------------------------
    // Quiz Reminder
    // -----------------------------

    fun showQuizReminder(context: Context) {

        showReminderNotification(

            context,

            "📝 Quiz Reminder",

            "Take today's CyberPath quiz and improve your score.",

            202

        )

    }

    // -----------------------------
    // Practical Reminder
    // -----------------------------

    fun showPracticalReminder(context: Context) {

        showReminderNotification(

            context,

            "💻 Practical Reminder",

            "Complete one practical lab today to improve your skills.",

            203

        )

    }

    // -----------------------------
    // Cyber News Reminder
    // -----------------------------

    fun showNewsReminder(context: Context) {

        showReminderNotification(

            context,

            "📰 Cyber Security News",

            "New cyber security news is waiting for you.",

            204

        )

    }

    // -----------------------------
    // Channels
    // -----------------------------

    private fun createCertificateChannel(
        manager: NotificationManager
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(

                CERTIFICATE_CHANNEL,

                "Certificate Downloads",

                NotificationManager.IMPORTANCE_HIGH

            )

            manager.createNotificationChannel(channel)

        }

    }

    private fun createReminderChannel(
        manager: NotificationManager
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(

                REMINDER_CHANNEL,

                "CyberPath Reminders",

                NotificationManager.IMPORTANCE_HIGH

            )

            channel.description =
                "Daily learning reminders and notifications."

            manager.createNotificationChannel(channel)

        }

    }

}
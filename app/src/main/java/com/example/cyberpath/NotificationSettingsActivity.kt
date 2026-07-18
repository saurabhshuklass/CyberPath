package com.example.cyberpath

import android.content.Context
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cyberpath.manager.ReminderScheduler

class NotificationSettingsActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton

    private lateinit var switchDaily: Switch
    private lateinit var switchNews: Switch
    private lateinit var switchQuiz: Switch
    private lateinit var switchPractical: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_settings)

        initViews()

        loadSettings()

        setupListeners()
    }

    private fun initViews() {

        btnBack = findViewById(R.id.btnBack)

        switchDaily = findViewById(R.id.switchDaily)

        switchNews = findViewById(R.id.switchNews)

        switchQuiz = findViewById(R.id.switchQuiz)

        switchPractical = findViewById(R.id.switchPractical)

    }

    private fun loadSettings() {

        val pref = getSharedPreferences(
            "notification_settings",
            Context.MODE_PRIVATE
        )

        switchDaily.isChecked =
            pref.getBoolean("daily", true)

        switchNews.isChecked =
            pref.getBoolean("news", true)

        switchQuiz.isChecked =
            pref.getBoolean("quiz", true)

        switchPractical.isChecked =
            pref.getBoolean("practical", true)

    }

    private fun saveSettings() {

        val pref = getSharedPreferences(
            "notification_settings",
            Context.MODE_PRIVATE
        )

        pref.edit()

            .putBoolean(
                "daily",
                switchDaily.isChecked
            )

            .putBoolean(
                "news",
                switchNews.isChecked
            )

            .putBoolean(
                "quiz",
                switchQuiz.isChecked
            )

            .putBoolean(
                "practical",
                switchPractical.isChecked
            )

            .apply()

    }

    private fun setupListeners() {

        btnBack.setOnClickListener {
            finish()
        }

        switchDaily.setOnCheckedChangeListener { _, isChecked ->

            saveSettings()

            if (isChecked) {

                ReminderScheduler.scheduleReminder(
                    this,
                    "DAILY",
                    9,
                    0,
                    101
                )

            } else {

                ReminderScheduler.cancelReminder(
                    this,
                    101
                )

            }

            Toast.makeText(
                this,
                "Daily Reminder Updated",
                Toast.LENGTH_SHORT
            ).show()

        }

        switchNews.setOnCheckedChangeListener { _, isChecked ->

            saveSettings()

            if (isChecked) {

                ReminderScheduler.scheduleReminder(
                    this,
                    "NEWS",
                    10,
                    0,
                    102
                )

            } else {

                ReminderScheduler.cancelReminder(
                    this,
                    102
                )

            }

            Toast.makeText(
                this,
                "Cyber News Reminder Updated",
                Toast.LENGTH_SHORT
            ).show()

        }

        switchQuiz.setOnCheckedChangeListener { _, isChecked ->

            saveSettings()

            if (isChecked) {

                ReminderScheduler.scheduleReminder(
                    this,
                    "QUIZ",
                    18,
                    0,
                    103
                )

            } else {

                ReminderScheduler.cancelReminder(
                    this,
                    103
                )

            }

            Toast.makeText(
                this,
                "Quiz Reminder Updated",
                Toast.LENGTH_SHORT
            ).show()

        }

        switchPractical.setOnCheckedChangeListener { _, isChecked ->

            saveSettings()

            if (isChecked) {

                ReminderScheduler.scheduleReminder(
                    this,
                    "PRACTICAL",
                    20,
                    0,
                    104
                )

            } else {

                ReminderScheduler.cancelReminder(
                    this,
                    104
                )

            }

            Toast.makeText(
                this,
                "Practical Reminder Updated",
                Toast.LENGTH_SHORT
            ).show()

        }

    }

}
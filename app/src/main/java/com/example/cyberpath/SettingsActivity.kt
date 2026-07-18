package com.example.cyberpath

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton

    private lateinit var cardAccount: LinearLayout
    private lateinit var cardPassword: LinearLayout
    private lateinit var cardNotification: LinearLayout
    private lateinit var cardPrivacy: LinearLayout
    private lateinit var cardAbout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        btnBack = findViewById(R.id.btnBack)

        cardAccount = findViewById(R.id.cardAccount)
        cardPassword = findViewById(R.id.cardPassword)
        cardNotification = findViewById(R.id.cardNotification)
        cardPrivacy = findViewById(R.id.cardPrivacy)
        cardAbout = findViewById(R.id.cardAbout)

        btnBack.setOnClickListener {
            finish()
        }

        cardAccount.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    AccountActivity::class.java
                )
            )

        }

        cardPassword.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ChangePasswordActivity::class.java
                )
            )

        }

        cardNotification.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    NotificationSettingsActivity::class.java
                )
            )

        }
        cardPrivacy.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    PrivacyPolicyActivity::class.java
                )
            )

        }

        cardAbout.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    AboutActivity::class.java
                )
            )

        }
    }
}
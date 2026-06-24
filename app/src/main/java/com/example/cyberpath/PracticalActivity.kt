package com.example.cyberpath

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PracticalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical)

        val btnPassword =
            findViewById<Button>(R.id.btnPassword)

        val btnPhishing =
            findViewById<Button>(R.id.btnPhishing)

        val btnUrl =
            findViewById<Button>(R.id.btnUrl)

        val btnSocial =
            findViewById<Button>(R.id.btnSocial)

        // Password Strength Checker

        btnPassword.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    PasswordStrengthActivity::class.java
                )
            )
        }

        // Phishing Detector

        btnPhishing.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    PhishingDetectorActivity::class.java
                )
            )
        }

        // URL Safety Checker

        btnUrl.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    UrlSafetyActivity::class.java
                )
            )
        }

        // Social Engineering Simulator

        btnSocial.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    SocialEngineeringActivity::class.java
                )
            )
        }
    }
}
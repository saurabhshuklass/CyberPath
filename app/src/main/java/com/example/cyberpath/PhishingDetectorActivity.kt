package com.example.cyberpath

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class PhishingDetectorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phishing_detector)

        // Views
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnTraining = findViewById<Button>(R.id.btnTraining)
        val btnAnalyzer = findViewById<Button>(R.id.btnAnalyzer)

        // Back Button
        btnBack.setOnClickListener {
            finish()
        }

        // Open Training Mode
        btnTraining.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    TrainingActivity::class.java
                )
            )

        }

        // Open Email Analyzer
        btnAnalyzer.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    EmailAnalyzerActivity::class.java
                )
            )

        }
    }
}
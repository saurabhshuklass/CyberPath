package com.example.cyberpath

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class SocialEngineeringActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnStartSimulation: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social_engineering)

        initViews()

        setupClickListeners()
    }

    private fun initViews() {

        btnBack = findViewById(R.id.btnBack)

        btnStartSimulation =
            findViewById(R.id.btnStartSimulation)

    }

    private fun setupClickListeners() {

        // Back Button
        btnBack.setOnClickListener {
            finish()
        }

        // Start Simulation
        btnStartSimulation.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    SimulationActivity::class.java
                )
            )

        }

    }
}
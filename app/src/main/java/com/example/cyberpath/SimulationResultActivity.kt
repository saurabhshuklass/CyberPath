package com.example.cyberpath

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SimulationResultActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnFinish: Button
    private lateinit var btnRetry: Button

    private lateinit var txtScore: TextView
    private lateinit var txtPerformance: TextView
    private lateinit var txtCorrect: TextView
    private lateinit var txtWrong: TextView
    private lateinit var txtAccuracy: TextView
    private lateinit var txtSecurityLevel: TextView
    private lateinit var txtLevelDescription: TextView

    private lateinit var progressScore: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulation_result)

        initViews()

        loadResult()

        btnBack.setOnClickListener {
            finish()
        }

        btnRetry.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    SimulationActivity::class.java
                )
            )

            finish()

        }

        btnFinish.setOnClickListener {

            val intent =
                Intent(
                    this,
                    PracticalActivity::class.java
                )

            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP

            startActivity(intent)

            finish()

        }

    }

    private fun initViews() {

        btnBack = findViewById(R.id.btnBack)

        btnFinish = findViewById(R.id.btnFinish)

        btnRetry = findViewById(R.id.btnRetry)

        txtScore = findViewById(R.id.txtScore)

        txtPerformance =
            findViewById(R.id.txtPerformance)

        txtCorrect =
            findViewById(R.id.txtCorrect)

        txtWrong =
            findViewById(R.id.txtWrong)

        txtAccuracy =
            findViewById(R.id.txtAccuracy)

        txtSecurityLevel =
            findViewById(R.id.txtSecurityLevel)

        txtLevelDescription =
            findViewById(R.id.txtLevelDescription)

        progressScore =
            findViewById(R.id.progressScore)

    }

    private fun loadResult() {

        val score =
            intent.getIntExtra("score", 0)

        val total =
            intent.getIntExtra("total", 10)

        val wrong =
            total - score

        val percentage =
            (score * 100) / total

        txtScore.text =
            "$score / $total"

        txtCorrect.text =
            score.toString()

        txtWrong.text =
            wrong.toString()

        txtAccuracy.text =
            "$percentage%"

        progressScore.progress =
            percentage

        when {

            percentage >= 90 -> {

                txtPerformance.text =
                    "Outstanding!"

                txtSecurityLevel.text =
                    "Expert"

                txtLevelDescription.text =
                    "You have excellent knowledge of social engineering attacks."

            }

            percentage >= 75 -> {

                txtPerformance.text =
                    "Excellent!"

                txtSecurityLevel.text =
                    "Advanced"

                txtLevelDescription.text =
                    "You can identify most cyber scams successfully."

            }

            percentage >= 50 -> {

                txtPerformance.text =
                    "Good Job!"

                txtSecurityLevel.text =
                    "Intermediate"

                txtLevelDescription.text =
                    "Keep practicing to improve your cyber awareness."

            }

            else -> {

                txtPerformance.text =
                    "Needs Improvement"

                txtSecurityLevel.text =
                    "Beginner"

                txtLevelDescription.text =
                    "Continue learning and practicing to recognize cyber threats."

            }

        }

    }

}
package com.example.cyberpath

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuizRulesActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnStartQuiz: Button

    private lateinit var txtQuizTitle: TextView
    private lateinit var txtQuestions: TextView
    private lateinit var txtTime: TextView

    private lateinit var quizLevel: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_rules)

        initViews()

        quizLevel = intent.getStringExtra("QUIZ_LEVEL") ?: "BEGINNER"

        setupQuiz()

        btnBack.setOnClickListener {
            finish()
        }

        btnStartQuiz.setOnClickListener {

            val intent = Intent(
                this,
                QuizQuestionActivity::class.java
            )

            intent.putExtra(
                "QUIZ_LEVEL",
                quizLevel
            )

            startActivity(intent)

        }

    }

    private fun initViews() {

        btnBack = findViewById(R.id.btnBack)
        btnStartQuiz = findViewById(R.id.btnStartQuiz)

        txtQuizTitle = findViewById(R.id.txtQuizTitle)
        txtQuestions = findViewById(R.id.txtQuestions)
        txtTime = findViewById(R.id.txtTime)

    }

    private fun setupQuiz() {

        txtQuestions.text = "10"

        when (quizLevel) {

            "BEGINNER" -> {

                txtQuizTitle.text = "🟢 Beginner Quiz"
                txtQuizTitle.setTextColor(Color.parseColor("#00E676"))

                txtTime.text = "10 Minutes"

            }

            "INTERMEDIATE" -> {

                txtQuizTitle.text = "🟡 Intermediate Quiz"
                txtQuizTitle.setTextColor(Color.parseColor("#FFD54F"))

                txtTime.text = "12 Minutes"

            }

            "ADVANCED" -> {

                txtQuizTitle.text = "🔴 Advanced Quiz"
                txtQuizTitle.setTextColor(Color.parseColor("#FF6B6B"))

                txtTime.text = "15 Minutes"

            }

        }

    }

}
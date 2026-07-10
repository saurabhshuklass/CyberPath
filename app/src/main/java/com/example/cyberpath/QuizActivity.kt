package com.example.cyberpath

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class QuizActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton

    private lateinit var btnBeginner: Button
    private lateinit var btnIntermediate: Button
    private lateinit var btnAdvanced: Button

    private lateinit var txtBestScore: TextView
    private lateinit var txtQuizCount: TextView
    private lateinit var txtRank: TextView

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        initViews()

        loadQuizStatistics()

        btnBack.setOnClickListener {
            finish()
        }

        btnBeginner.setOnClickListener {

            val intent = Intent(
                this,
                QuizRulesActivity::class.java
            )

            intent.putExtra(
                "QUIZ_LEVEL",
                "BEGINNER"
            )

            startActivity(intent)

        }

        btnIntermediate.setOnClickListener {

            val intent = Intent(
                this,
                QuizRulesActivity::class.java
            )

            intent.putExtra(
                "QUIZ_LEVEL",
                "INTERMEDIATE"
            )

            startActivity(intent)

        }

        btnAdvanced.setOnClickListener {

            val intent = Intent(
                this,
                QuizRulesActivity::class.java
            )

            intent.putExtra(
                "QUIZ_LEVEL",
                "ADVANCED"
            )

            startActivity(intent)

        }
    }

    private fun initViews() {

        btnBack = findViewById(R.id.btnBack)

        btnBeginner = findViewById(R.id.btnBeginner)
        btnIntermediate = findViewById(R.id.btnIntermediate)
        btnAdvanced = findViewById(R.id.btnAdvanced)

        txtBestScore = findViewById(R.id.txtBestScore)
        txtQuizCount = findViewById(R.id.txtQuizCount)
        txtRank = findViewById(R.id.txtRank)

    }

    private fun loadQuizStatistics() {

        val uid = auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->

                if (!document.exists()) return@addOnSuccessListener

                val bestScore =
                    document.getLong("bestQuizScore") ?: 0

                val quizCount =
                    document.getLong("completedQuizzes") ?: 0

                txtBestScore.text = "$bestScore%"
                txtQuizCount.text = quizCount.toString()

                txtRank.text = when {

                    bestScore >= 90 -> "S"

                    bestScore >= 80 -> "A"

                    bestScore >= 70 -> "B"

                    bestScore >= 60 -> "C"

                    else -> "D"

                }

            }

    }

}
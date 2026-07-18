package com.example.cyberpath

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
class QuizResultActivity : AppCompatActivity() {

    private lateinit var txtResultTitle: TextView
    private lateinit var txtPerformance: TextView

    private lateinit var txtScore: TextView
    private lateinit var txtPercentage: TextView
    private lateinit var txtStatus: TextView
    private lateinit var txtLevel: TextView

    private lateinit var btnRetry: Button
    private lateinit var btnBackToQuiz: Button

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private var score = 0
    private var total = 10
    private var percentage = 0
    private var level = "BEGINNER"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        initViews()

        score = intent.getIntExtra("SCORE", 0)
        total = intent.getIntExtra("TOTAL", 10)
        level = intent.getStringExtra("LEVEL") ?: "BEGINNER"

        percentage = (score * 100) / total

        showResult()

        saveQuizResult()

        btnRetry.setOnClickListener {

            val intent = Intent(
                this,
                QuizRulesActivity::class.java
            )

            intent.putExtra(
                "QUIZ_LEVEL",
                level
            )

            startActivity(intent)

            finish()

        }

        btnBackToQuiz.setOnClickListener {

            finish()

        }

    }

    private fun initViews() {

        txtResultTitle = findViewById(R.id.txtResultTitle)
        txtPerformance = findViewById(R.id.txtPerformance)

        txtScore = findViewById(R.id.txtScore)
        txtPercentage = findViewById(R.id.txtPercentage)
        txtStatus = findViewById(R.id.txtStatus)
        txtLevel = findViewById(R.id.txtLevel)

        btnRetry = findViewById(R.id.btnRetry)
        btnBackToQuiz = findViewById(R.id.btnBackToQuiz)

    }

    private fun showResult() {

        txtScore.text = "$score / $total"
        txtPercentage.text = "$percentage%"
        txtLevel.text = level

        if (percentage >= 60) {

            txtStatus.text = "PASS"
            txtStatus.setTextColor(Color.parseColor("#00E676"))

        } else {

            txtStatus.text = "FAIL"
            txtStatus.setTextColor(Color.parseColor("#FF5252"))

        }

        txtPerformance.text = when {

            percentage >= 90 -> "🏆 Excellent"

            percentage >= 75 -> "🥇 Very Good"

            percentage >= 60 -> "👍 Good"

            percentage >= 40 -> "🙂 Average"

            else -> "📚 Keep Practicing"

        }

    }

    private fun saveQuizResult() {

        val uid = auth.currentUser?.uid ?: return

        val userRef =
            firestore.collection("users")
                .document(uid)

        val currentDate =
            SimpleDateFormat(
                "dd MMM yyyy",
                Locale.getDefault()
            ).format(Date())

        val currentTime =
            SimpleDateFormat(
                "hh:mm a",
                Locale.getDefault()
            ).format(Date())

        val history = hashMapOf(

            "level" to level,

            "score" to score,

            "totalQuestions" to total,

            "percentage" to percentage,

            "date" to currentDate,

            "time" to currentTime,

            "completedAt" to FieldValue.serverTimestamp(),

            "timestamp" to System.currentTimeMillis()

        )

        firestore.collection("quiz_history")
            .document(uid)
            .collection("history")
            .add(history)

        userRef.get()

            .addOnSuccessListener { document ->

                val bestScore =
                    document.getLong("bestQuizScore") ?: 0

                val updates = hashMapOf<String, Any>()

                val completedQuizzes =
                    document.getLong("completedQuizzes") ?: 0

                if (completedQuizzes < 1) {

                    updates["completedQuizzes"] = 1

                }

                updates["quizScore"] = percentage

                if (percentage > bestScore) {

                    updates["bestQuizScore"] = percentage

                }

                userRef.update(updates)

            }

    }
}
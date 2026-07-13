package com.example.cyberpath

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var txtCertificateStatus: TextView
    private lateinit var txtName: TextView
    private lateinit var txtProgressPercent: TextView
    private lateinit var txtProgressInfo: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        txtCertificateStatus = findViewById(R.id.txtCertificateStatus)
        txtName = findViewById(R.id.txtName)
        txtProgressPercent = findViewById(R.id.txtProgressPercent)
        txtProgressInfo = findViewById(R.id.txtProgressInfo)
        progressBar = findViewById(R.id.progressBar)

        loadUserData()

        findViewById<LinearLayout>(R.id.cardLearning)
            .setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        LearningActivity::class.java
                    )
                )
            }

        findViewById<LinearLayout>(R.id.cardPractice)
            .setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        PracticalActivity::class.java
                    )
                )
            }

        findViewById<LinearLayout>(R.id.cardQuiz)
            .setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        QuizActivity::class.java
                    )
                )
            }

        findViewById<LinearLayout>(R.id.cardCertificate)
            .setOnClickListener {

                val uid = auth.currentUser?.uid ?: return@setOnClickListener

                firestore.collection("users")
                    .document(uid)
                    .get()
                    .addOnSuccessListener { document ->

                        val topics = document.getLong("completedTopics") ?: 0
                        val practicals = document.getLong("completedPracticals") ?: 0
                        val quizzes = document.getLong("completedQuizzes") ?: 0

                        // Values displayed in the UI (don't exceed the maximum)
                        val displayTopics = minOf(topics.toInt(), 8)
                        val displayPracticals = minOf(practicals.toInt(), 4)
                        val displayQuizzes = minOf(quizzes.toInt(), 1)

                        if (topics >= 8 &&
                            practicals >= 4 &&
                            quizzes >= 1
                        ) {

                            startActivity(
                                Intent(
                                    this,
                                    CertificateActivity::class.java
                                )
                            )

                        } else {

                            androidx.appcompat.app.AlertDialog.Builder(this)
                                .setTitle("Certificate Locked")
                                .setMessage(
                                    """
Complete the following:

Topics : $displayTopics / 8

Practicals : $displayPracticals / 4

Quiz : $displayQuizzes / 1

Finish all requirements to unlock your certificate.
    """.trimIndent()
                                )
                                .setPositiveButton("OK", null)
                                .show()

                        }

                    }

            }
    }

    private fun loadUserData() {

        val uid = auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->

                if (document.exists()) {

                    val name =
                        document.getString("name") ?: "User"

                    val completed =
                        document.getLong("completedTopics") ?: 0

                    val completedPracticals =
                        document.getLong("completedPracticals") ?: 0

                    val completedQuizzes =
                        document.getLong("completedQuizzes") ?: 0

                    txtName.text = "$name 👋"

                    val percent =
                        ((completed.toDouble() / 8.0) * 100).toInt()

                    txtProgressPercent.text =
                        "$percent%"

                    progressBar.progress =
                        percent

                    txtProgressInfo.text =
                        "$completed of 8 topics completed"

                    val certificateUnlocked =
                        completed >= 8 &&
                                completedPracticals >= 4 &&
                                completedQuizzes >= 1

                    if (certificateUnlocked) {

                        txtCertificateStatus.text =
                            "🏆 Available"

                    } else {

                        txtCertificateStatus.text =
                            "🔒 Locked"

                    }
                }
            }
    }

    override fun onResume() {
        super.onResume()

        loadUserData()
    }
}
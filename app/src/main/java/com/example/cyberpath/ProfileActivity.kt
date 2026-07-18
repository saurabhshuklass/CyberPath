package com.example.cyberpath

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {

    // Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    // Header
    private lateinit var btnBack: ImageButton
    private lateinit var imgProfile: ImageView

    private lateinit var txtName: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtCourse: TextView

    // Statistics
    private lateinit var txtTopicsCount: TextView
    private lateinit var txtQuizScore: TextView
    private lateinit var txtCertificates: TextView
    private lateinit var txtStreak: TextView

    // Cards
    private lateinit var cardCertificates: MaterialCardView
    private lateinit var cardSettings: MaterialCardView
    private lateinit var cardShare: MaterialCardView

    // Logout
    private lateinit var btnLogout: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        initViews()

        loadUserData()

        loadStatistics()

        setupClickListeners()
    }

    private fun initViews() {

        btnBack = findViewById(R.id.btnBack)

        imgProfile = findViewById(R.id.imgProfile)

        txtName = findViewById(R.id.txtName)
        txtEmail = findViewById(R.id.txtEmail)
        txtCourse = findViewById(R.id.txtCourse)

        txtTopicsCount = findViewById(R.id.txtTopicsCount)
        txtQuizScore = findViewById(R.id.txtQuizScore)
        txtCertificates = findViewById(R.id.txtCertificates)
        txtStreak = findViewById(R.id.txtStreak)

        cardCertificates = findViewById(R.id.cardCertificates)
        cardSettings = findViewById(R.id.cardSettings)
        cardShare = findViewById(R.id.cardShare)

        btnLogout = findViewById(R.id.btnLogout)


    }
    private fun loadUserData() {

        val uid = auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->

                if (!document.exists()) return@addOnSuccessListener

                val name =
                    document.getString("name")
                        ?: "CyberPath User"

                val email =
                    document.getString("email")
                        ?: auth.currentUser?.email
                        ?: ""

                val course =
                    document.getString("course")
                        ?: "MCA Cyber Security"

                txtName.text = name

                txtEmail.text = email

                txtCourse.text = course

            }
            .addOnFailureListener {

                txtName.text = "CyberPath User"

                txtEmail.text =
                    auth.currentUser?.email ?: ""

                txtCourse.text =
                    "MCA Cyber Security"

            }

    }

    private fun loadStatistics() {

        val uid = auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->

                if (!document.exists()) return@addOnSuccessListener

                // Learning Topics
                val topics =
                    document.getLong("completedTopics")?.toInt() ?: 0

                txtTopicsCount.text = "$topics"

                // Best Quiz Score
                val bestQuiz =
                    document.getLong("bestQuizScore")?.toInt() ?: 0

                txtQuizScore.text = "$bestQuiz%"

                // Streak
                val streak =
                    document.getLong("streak")?.toInt() ?: 1

                txtStreak.text = "$streak"

                // Certificates Count
                loadCertificateCount(uid)

            }
    }

    private fun loadCertificateCount(uid: String) {

        firestore.collection("certificates")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->

                txtCertificates.text =
                    if (document.exists()) "1" else "0"

            }
            .addOnFailureListener {

                txtCertificates.text = "0"

            }

    }

    private fun setupClickListeners() {

        // Back Button
        btnBack.setOnClickListener {
            finish()
        }

        // Open Certificate Page
        cardCertificates.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    CertificateActivity::class.java
                )
            )

        }

        // Open Settings
        cardSettings.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    SettingsActivity::class.java
                )
            )

        }

        // Share CyberPath
        cardShare.setOnClickListener {

            val shareIntent = Intent(Intent.ACTION_SEND)

            shareIntent.type = "text/plain"

            shareIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                "CyberPath"
            )

            shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                """
🛡 CyberPath

Cyber Security Awareness & Practical Training App

Download:
https://github.com/YOUR_GITHUB_USERNAME/CyberPath

Learn Cyber Security in a practical way.
            """.trimIndent()
            )

            startActivity(
                Intent.createChooser(
                    shareIntent,
                    "Share CyberPath"
                )
            )

        }

        // Logout
        btnLogout.setOnClickListener {

            androidx.appcompat.app.AlertDialog.Builder(this)

                .setTitle("Logout")

                .setMessage("Are you sure you want to logout?")

                .setPositiveButton("Logout") { _, _ ->

                    auth.signOut()

                    val intent = Intent(
                        this,
                        LoginActivity::class.java
                    )

                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK

                    startActivity(intent)

                    finish()

                }

                .setNegativeButton("Cancel", null)

                .show()

        }

    }
}
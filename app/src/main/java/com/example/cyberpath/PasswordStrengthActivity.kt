package com.example.cyberpath

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
class PasswordStrengthActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_strength)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        var passwordAnalyzed = false

        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnAnalyze = findViewById<Button>(R.id.btnAnalyze)
        val txtStrength = findViewById<TextView>(R.id.txtStrength)
        val txtChecklist = findViewById<TextView>(R.id.txtChecklist)
        val txtTips = findViewById<TextView>(R.id.txtTips)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val btnCompleteLab = findViewById<Button>(R.id.btnCompleteLab)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()
        }

        btnAnalyze.setOnClickListener {

            val password = etPassword.text.toString().trim()

            if (password.isEmpty()) {

                Toast.makeText(
                    this,
                    "Enter a Password",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }
            passwordAnalyzed = true

            var score = 0

            val checklist = StringBuilder()
            val tips = StringBuilder()

            val commonPasswords = listOf(
                "123456",
                "password",
                "admin",
                "qwerty",
                "welcome",
                "abc123"
            )

            if (commonPasswords.contains(password.lowercase())) {

                txtStrength.text = "Very Weak"

                progressBar.progress = 10

                txtChecklist.text =
                    "❌ Common password detected"

                txtTips.text =
                    "Use a unique password that is difficult to guess."

                return@setOnClickListener
            }

            if (password.length >= 8) {
                score += 25
                checklist.append("✅ 8+ Characters\n")
            } else {
                checklist.append("❌ 8+ Characters\n")
                tips.append("• Use at least 8 characters\n")
            }

            if (password.length >= 12) {
                checklist.append("✅ 12+ Characters\n")
            } else {
                checklist.append("❌ 12+ Characters\n")
                tips.append("• Prefer 12+ characters\n")
            }

            if (password.matches(Regex(".*[A-Z].*"))) {
                score += 25
                checklist.append("✅ Uppercase Letters\n")
            } else {
                checklist.append("❌ Uppercase Letters\n")
                tips.append("• Add uppercase letters\n")
            }

            if (password.matches(Regex(".*[0-9].*"))) {
                score += 25
                checklist.append("✅ Numbers\n")
            } else {
                checklist.append("❌ Numbers\n")
                tips.append("• Add numbers\n")
            }

            if (password.matches(Regex(".*[@#\$%^&+=!].*"))) {
                score += 25
                checklist.append("✅ Special Characters\n")
            } else {
                checklist.append("❌ Special Characters\n")
                tips.append("• Add special characters\n")
            }

            progressBar.progress = score

            when {
                score <= 25 -> txtStrength.text = "Very Weak"
                score <= 50 -> txtStrength.text = "Weak"
                score <= 75 -> txtStrength.text = "Medium"
                else -> txtStrength.text = "Strong"
            }

            txtChecklist.text = checklist.toString()

            txtTips.text =
                if (tips.isEmpty())
                    "Excellent Password Security!"
                else
                    tips.toString()
        }

        btnCompleteLab.setOnClickListener {

            if (!passwordAnalyzed) {

                Toast.makeText(
                    this,
                    "Please analyze a password first",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val uid =
                auth.currentUser?.uid
                    ?: return@setOnClickListener

            val practicalRef =
                firestore.collection("practical_progress")
                    .document(uid)

            practicalRef.get()

                .addOnSuccessListener { document ->

                    if (
                        document.getBoolean(
                            "Password Strength Checker"
                        ) == true
                    ) {

                        Toast.makeText(
                            this,
                            "Lab Already Completed",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        practicalRef.set(
                            mapOf(
                                "Password Strength Checker" to true
                            ),
                            SetOptions.merge()
                        )

                            .addOnSuccessListener {

                                firestore.collection("users")
                                    .document(uid)
                                    .update(
                                        "completedPracticals",
                                        FieldValue.increment(1)
                                    )

                                    .addOnSuccessListener {

                                        Toast.makeText(
                                            this,
                                            "Lab Completed Successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        finish()
                                    }

                                    .addOnFailureListener {

                                        Toast.makeText(
                                            this,
                                            "Failed to Update Progress",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                    }
                }

                .addOnFailureListener {

                    Toast.makeText(
                        this,
                        "Failed to Check Progress",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}
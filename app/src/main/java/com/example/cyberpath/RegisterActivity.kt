package com.example.cyberpath

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.cyberpath.models.User
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val etName = findViewById<TextInputEditText>(R.id.etName)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val etConfirmPassword =
            findViewById<TextInputEditText>(R.id.etConfirmPassword)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val txtLogin = findViewById<TextView>(R.id.txtLogin)

        btnRegister.setOnClickListener {

            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword =
                etConfirmPassword.text.toString().trim()

            // Validation

            if (name.isEmpty()) {
                etName.error = "Enter Full Name"
                etName.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                etEmail.error = "Enter Email Address"
                etEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = "Enter Password"
                etPassword.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6) {

                Toast.makeText(
                    this,
                    "Password must be at least 6 characters",
                    Toast.LENGTH_LONG
                ).show()

                return@setOnClickListener
            }

            if (confirmPassword.isEmpty()) {
                etConfirmPassword.error =
                    "Confirm Password"
                etConfirmPassword.requestFocus()
                return@setOnClickListener
            }

            if (password != confirmPassword) {

                Toast.makeText(
                    this,
                    "Passwords do not match",
                    Toast.LENGTH_LONG
                ).show()

                return@setOnClickListener
            }

            // Firebase Registration

            auth.createUserWithEmailAndPassword(
                email,
                password
            )

                .addOnSuccessListener {

                    val uid =
                        auth.currentUser?.uid ?: ""

                    val user = User(
                        uid = uid,
                        name = name,
                        email = email,
                        completedTopics = 0,
                        completedPracticals = 0,
                        quizScore = 0
                    )

                    firestore.collection("users")
                        .document(uid)
                        .set(user)

                        .addOnSuccessListener {

                            AlertDialog.Builder(this)
                                .setTitle("Registration Successful")
                                .setMessage(
                                    "Your CyberPath account has been created successfully.\n\nPlease login to continue."
                                )
                                .setCancelable(false)
                                .setPositiveButton("Login") { _, _ ->

                                    startActivity(
                                        Intent(
                                            this,
                                            LoginActivity::class.java
                                        )
                                    )

                                    finish()
                                }
                                .show()
                        }

                        .addOnFailureListener {

                            Toast.makeText(
                                this,
                                "Firestore Error:\n${it.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                }

                .addOnFailureListener {

                    Toast.makeText(
                        this,
                        "Registration Failed:\n${it.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }

        txtLogin.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

            finish()
        }
    }
}
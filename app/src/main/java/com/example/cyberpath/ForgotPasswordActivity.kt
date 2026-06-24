package com.example.cyberpath

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        auth = FirebaseAuth.getInstance()

        val email =
            findViewById<TextInputEditText>(R.id.etEmail)

        val btnReset =
            findViewById<Button>(R.id.btnReset)

        btnReset.setOnClickListener {

            val userEmail =
                email.text.toString().trim()

            if (userEmail.isEmpty()) {

                Toast.makeText(
                    this,
                    "Enter Email Address",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(userEmail)

                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        "Reset Link Sent Successfully",
                        Toast.LENGTH_LONG
                    ).show()

                    finish()
                }

                .addOnFailureListener {

                    Toast.makeText(
                        this,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }
}
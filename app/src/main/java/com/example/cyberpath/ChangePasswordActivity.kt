package com.example.cyberpath

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var btnBack: ImageButton
    private lateinit var edtEmail: EditText
    private lateinit var btnSendReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        auth = FirebaseAuth.getInstance()

        initViews()

        loadEmail()

        setupClickListeners()
    }

    private fun initViews() {

        btnBack = findViewById(R.id.btnBack)

        edtEmail = findViewById(R.id.edtEmail)

        btnSendReset = findViewById(R.id.btnSendReset)

    }

    private fun loadEmail() {

        val email = auth.currentUser?.email ?: ""

        edtEmail.setText(email)

    }

    private fun setupClickListeners() {

        btnBack.setOnClickListener {

            finish()

        }

        btnSendReset.setOnClickListener {

            sendResetEmail()

        }

    }

    private fun sendResetEmail() {

        val email = edtEmail.text.toString().trim()

        if (email.isEmpty()) {

            Toast.makeText(
                this,
                "Email not found.",
                Toast.LENGTH_SHORT
            ).show()

            return

        }

        btnSendReset.isEnabled = false

        auth.sendPasswordResetEmail(email)

            .addOnSuccessListener {

                btnSendReset.isEnabled = true

                AlertDialog.Builder(this)

                    .setTitle("Email Sent")

                    .setMessage(
                        "A password reset email has been sent to:\n\n$email\n\nPlease check your Inbox or Spam folder."
                    )

                    .setPositiveButton("OK") { _, _ ->

                        finish()

                    }

                    .show()

            }

            .addOnFailureListener {

                btnSendReset.isEnabled = true

                Toast.makeText(
                    this,
                    it.message,
                    Toast.LENGTH_LONG
                ).show()

            }

    }

}
package com.example.cyberpath

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cyberpath.data.EmailRepository
import com.example.cyberpath.models.EmailModel
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class TrainingActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnSafe: Button
    private lateinit var btnPhishing: Button
    private lateinit var btnNext: Button
    private lateinit var btnFinish: Button

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var emailCard: LinearLayout
    private lateinit var txtEmailNumber: TextView
    private lateinit var txtScore: TextView
    private lateinit var txtSender: TextView
    private lateinit var txtSubject: TextView
    private lateinit var txtBody: TextView

    private lateinit var txtResult: TextView
    private lateinit var txtExplanation: TextView
    private lateinit var txtFinalScore: TextView
    private lateinit var txtRemark: TextView

    private lateinit var resultCard: LinearLayout
    private lateinit var finalCard: LinearLayout

    private var currentIndex = 0
    private var score = 0

    private val emails = EmailRepository.emails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        initViews()

        loadEmail()

        btnBack.setOnClickListener {
            finish()
        }

        btnSafe.setOnClickListener {

            checkAnswer(false)

        }

        btnPhishing.setOnClickListener {

            checkAnswer(true)

        }

        btnNext.setOnClickListener {

            currentIndex++

            if (currentIndex < emails.size) {

                loadEmail()

            } else {

                showFinalResult()

            }

        }

        btnFinish.setOnClickListener {

            Toast.makeText(
                this,
                "Training Completed Successfully",
                Toast.LENGTH_SHORT
            ).show()

            finish()

        }

        emailCard =
            findViewById(R.id.emailCard)

    }

    private fun initViews() {

        btnBack = findViewById(R.id.btnBack)
        btnSafe = findViewById(R.id.btnSafe)
        btnPhishing = findViewById(R.id.btnPhishing)
        btnNext = findViewById(R.id.btnNext)
        btnFinish = findViewById(R.id.btnFinish)

        txtEmailNumber = findViewById(R.id.txtEmailNumber)
        txtScore = findViewById(R.id.txtScore)

        txtSender = findViewById(R.id.txtSender)
        txtSubject = findViewById(R.id.txtSubject)
        txtBody = findViewById(R.id.txtBody)

        txtResult = findViewById(R.id.txtResult)
        txtExplanation = findViewById(R.id.txtExplanation)

        txtFinalScore = findViewById(R.id.txtFinalScore)
        txtRemark = findViewById(R.id.txtRemark)

        resultCard = findViewById(R.id.resultCard)
        finalCard = findViewById(R.id.finalCard)

    }

    private fun loadEmail() {

        resultCard.visibility = View.GONE
        btnNext.visibility = View.GONE

        btnSafe.isEnabled = true
        btnPhishing.isEnabled = true

        val email = emails[currentIndex]

        txtEmailNumber.text =
            "Email ${currentIndex + 1} of ${emails.size}"

        txtScore.text =
            "Score : $score"

        txtSender.text =
            email.sender

        txtSubject.text =
            email.subject

        txtBody.text =
            email.body

    }

    private fun checkAnswer(userAnswer: Boolean) {

        val email = emails[currentIndex]

        // Disable buttons after answering
        btnSafe.isEnabled = false
        btnPhishing.isEnabled = false

        resultCard.visibility = View.VISIBLE
        btnNext.visibility = View.VISIBLE

        if (userAnswer == email.isPhishing) {

            score++

            txtScore.text = "Score : $score"

            txtResult.text = "✅ Correct!"

            txtResult.setTextColor(
                resources.getColor(android.R.color.holo_green_light)
            )

        } else {

            txtResult.text = "❌ Wrong!"

            txtResult.setTextColor(
                resources.getColor(android.R.color.holo_red_light)
            )

        }

        // Show explanation
        val explanation = StringBuilder()

        explanation.append("Indicators:\n\n")

        for (item in email.explanation) {

            explanation.append("• ")
            explanation.append(item)
            explanation.append("\n")

        }

        txtExplanation.text = explanation.toString()

    }

    private fun showFinalResult() {

        emailCard.visibility = View.GONE

        btnSafe.visibility = View.GONE
        btnPhishing.visibility = View.GONE

        resultCard.visibility = View.GONE
        btnNext.visibility = View.GONE

        finalCard.visibility = View.VISIBLE

        txtFinalScore.text =
            "Score : $score / ${emails.size}"

        when {

            score == emails.size ->

                txtRemark.text =
                    "🏆 Excellent! Perfect Score."

            score >= emails.size * 0.7 ->

                txtRemark.text =
                    "👏 Great Job! You can identify most phishing emails."

            score >= emails.size * 0.5 ->

                txtRemark.text =
                    "🙂 Good! Keep practicing."

            else ->

                txtRemark.text =
                    "⚠ You should practice more."

        }

        saveTrainingProgress()
    }

    private fun saveTrainingProgress() {

        val uid =
            auth.currentUser?.uid
                ?: return

        val progressRef =
            firestore.collection("practical_progress")
                .document(uid)

        progressRef.get()

            .addOnSuccessListener { document ->

                if (document.getBoolean("Phishing Training") == true) {

                    Toast.makeText(
                        this,
                        "Training Already Completed",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    progressRef.set(

                        mapOf(
                            "Phishing Training" to true
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

                        }

                }

            }

    }
}

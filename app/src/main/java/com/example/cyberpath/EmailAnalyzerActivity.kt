package com.example.cyberpath

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class EmailAnalyzerActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var etEmail: EditText
    private lateinit var txtIndicatorCount: TextView
    private lateinit var txtUrlFound: TextView

    private lateinit var btnCopy: Button
    private lateinit var btnClear: Button
    private lateinit var btnAnalyze: Button
    private lateinit var btnComplete: Button
    private lateinit var btnBack: ImageButton

    private lateinit var resultCard: LinearLayout

    private lateinit var txtRisk: TextView
    private lateinit var txtScore: TextView
    private lateinit var txtIndicators: TextView
    private lateinit var txtRecommendation: TextView

    private var lastRiskScore = 0

    // NEW
    private var analyzed = false

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_analyzer)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        etEmail = findViewById(R.id.etEmail)

        btnAnalyze = findViewById(R.id.btnAnalyze)
        btnComplete = findViewById(R.id.btnComplete)
        btnBack = findViewById(R.id.btnBack)

        resultCard = findViewById(R.id.resultCard)

        txtRisk = findViewById(R.id.txtRisk)
        txtScore = findViewById(R.id.txtScore)
        txtIndicators = findViewById(R.id.txtIndicators)
        txtRecommendation = findViewById(R.id.txtRecommendation)
        txtIndicatorCount = findViewById(R.id.txtIndicatorCount)
        txtUrlFound = findViewById(R.id.txtUrlFound)
        btnCopy = findViewById(R.id.btnCopy)
        btnClear = findViewById(R.id.btnClear)

        btnBack.setOnClickListener {

            finish()

        }

        btnAnalyze.setOnClickListener {

            analyzeEmail()

        }

        btnComplete.setOnClickListener {

            saveProgress()

        }
        btnCopy.setOnClickListener {

            val clipboard =
                getSystemService(CLIPBOARD_SERVICE)
                        as android.content.ClipboardManager

            val clip =
                android.content.ClipData.newPlainText(

                    "Analysis",

                    """
                    Risk : ${txtRisk.text}

                    ${txtScore.text}

                    ${txtIndicators.text}

                    Recommendation

                        ${txtRecommendation.text}
                            """.trimIndent()

                )

            btnClear.setOnClickListener {

                etEmail.setText("")

                resultCard.visibility = View.GONE

                analyzed = false

                lastRiskScore = 0

            }

            clipboard.setPrimaryClip(clip)

            Toast.makeText(
                this,
                "Analysis Copied",
                Toast.LENGTH_SHORT
            ).show()

        }

    }


    private fun analyzeEmail() {

        var indicatorCount = 0
        val email = etEmail.text.toString().trim().lowercase()

        if (email.isEmpty()) {

            Toast.makeText(
                this,
                "Please paste an email first.",
                Toast.LENGTH_SHORT
            ).show()

            val regex =
                "(https?://\\S+)".toRegex()

            val match =
                regex.find(email)

            if (match != null) {

                txtUrlFound.text =
                    "URL : ${match.value}"

            } else {

                txtUrlFound.text =
                    "URL : None"

            }

            return
        }

        analyzed = true

        lastRiskScore = 0

        resultCard.visibility = View.VISIBLE

        txtIndicators.text = ""

        var indicators = StringBuilder()

        // --------------------
        // Generic Greeting
        // --------------------

        if (
            email.contains("dear customer") ||
            email.contains("dear user") ||
            email.contains("valued customer")
        ) {

            lastRiskScore += 15

            indicators.append("• Generic Greeting\n")

        }

        // --------------------
        // Verification Request
        // --------------------

        if (
            email.contains("verify") ||
            email.contains("verification") ||
            email.contains("confirm")
        ) {

            lastRiskScore += 20

            indicators.append("• Verification Request\n")

        }

        // --------------------
        // Urgent Language
        // --------------------

        if (
            email.contains("urgent") ||
            email.contains("immediately") ||
            email.contains("within 24 hours") ||
            email.contains("expire")
        ) {

            lastRiskScore += 15

            indicators.append("• Urgent Language\n")

        }

        // --------------------
        // Login Request
        // --------------------

        if (
            email.contains("login") ||
            email.contains("sign in")
        ) {

            lastRiskScore += 15

            indicators.append("• Login Request\n")

        }

        // --------------------
        // Password
        // --------------------

        if (
            email.contains("password")
        ) {

            lastRiskScore += 20

            indicators.append("• Password Requested\n")

        }

        // --------------------
        // OTP
        // --------------------

        if (
            email.contains("otp")
        ) {

            lastRiskScore += 20

            indicators.append("• OTP Requested\n")

        }

        // --------------------
        // Sensitive Account
        // --------------------

        if (
            email.contains("bank") ||
            email.contains("account")
        ) {

            lastRiskScore += 15

            indicators.append("• Sensitive Account Mentioned\n")

        }

        // --------------------
        // HTTP / HTTPS Link
        // --------------------

        if (
            email.contains("http://") ||
            email.contains("https://")
        ) {

            lastRiskScore += 20

            indicators.append("• URL Found in Email\n")

        }

        // --------------------
        // Click Here
        // --------------------

        if (
            email.contains("click here") ||
            email.contains("click below")
        ) {

            lastRiskScore += 15

            indicators.append("• Suspicious Link Request\n")

        }

        // --------------------
        // Prize Scam
        // --------------------

        if (
            email.contains("winner") ||
            email.contains("lottery") ||
            email.contains("free gift") ||
            email.contains("reward")
        ) {

            lastRiskScore += 20

            indicators.append("• Prize Scam Keywords\n")

        }

        txtScore.text =
            "Risk Score : $lastRiskScore%"

        if (indicators.isEmpty()) {

            txtIndicators.text =
                "No major phishing indicators found."

        } else {

            txtIndicators.text =
                indicators.toString()

        }

        when {

            lastRiskScore <= 25 -> {

                txtRisk.text = "🟢 LOW RISK"
                txtRisk.setTextColor(getColor(android.R.color.holo_green_light))

                txtRecommendation.text =
                    "This email appears safe. Always verify the sender before clicking links."

            }

            lastRiskScore <= 50 -> {

                txtRisk.text = "🟢 LOW RISK"
                txtRisk.setTextColor(getColor(android.R.color.holo_green_light))

                txtRecommendation.text =
                    "Some phishing indicators were detected. Be cautious."

            }

            lastRiskScore <= 75 -> {

                txtRisk.text = "🟠 HIGH RISK"
                txtRisk.setTextColor(getColor(android.R.color.holo_orange_dark))

                txtRecommendation.text =
                    "This email is likely phishing. Avoid clicking links or sharing personal information."

            }

            else -> {

                txtRisk.text = "🔴 CRITICAL RISK"
                txtRisk.setTextColor(getColor(android.R.color.holo_red_light))

                txtRecommendation.text =
                    "This email appears extremely dangerous. Delete it immediately and do not interact with it."

            }

        }

    }

    private fun saveProgress() {

        if (!analyzed) {

            Toast.makeText(
                this,
                "Please analyze an email first.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val uid = auth.currentUser?.uid

        if (uid == null) {

            Toast.makeText(
                this,
                "User not logged in.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val practicalRef =
            firestore.collection("practical_progress")
                .document(uid)

        practicalRef.get()

            .addOnSuccessListener { document ->

                if (
                    document.getBoolean("Email Analyzer") == true
                ) {

                    Toast.makeText(
                        this,
                        "Lab Already Completed",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    practicalRef.set(

                        mapOf(
                            "Email Analyzer" to true
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
                                        "Email Analyzer Completed Successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    finish()

                                }

                                .addOnFailureListener {

                                    Toast.makeText(
                                        this,
                                        "Progress saved, but failed to update user data.",
                                        Toast.LENGTH_LONG
                                    ).show()

                                }

                        }

                        .addOnFailureListener {

                            Toast.makeText(
                                this,
                                "Failed to save practical progress.",
                                Toast.LENGTH_LONG
                            ).show()

                        }

                }

            }

            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Failed to connect to Firebase.",
                    Toast.LENGTH_LONG
                ).show()

            }

    }
}
package com.example.cyberpath

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.cyberpath.network.SafeBrowsingRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.cyberpath.utils.ProgressManager


class UrlSafetyActivity : AppCompatActivity() {
    private lateinit var btnBack: ImageButton
    private lateinit var btnAnalyze: Button

    private lateinit var etUrl: TextInputEditText

    private lateinit var progressBar: ProgressBar
    private lateinit var resultCard: View
    private val repository = SafeBrowsingRepository()

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private lateinit var txtVerdict: TextView
    private lateinit var txtRiskScore: TextView

    private lateinit var txtHttps: TextView
    private lateinit var txtShortener: TextView
    private lateinit var txtIp: TextView
    private lateinit var txtKeyword: TextView
    private lateinit var txtLength: TextView
    private lateinit var txtRecommendation: TextView
    private lateinit var riskProgress: ProgressBar
    private var isScanning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_url_safety)

        initViews()

        btnBack.setOnClickListener {
            finish()
        }

        btnAnalyze.setOnClickListener {

            if (isScanning)
                return@setOnClickListener

            validateAndAnalyze()

        }
    }

    private fun initViews() {

        btnBack = findViewById(R.id.btnBack)
        btnAnalyze = findViewById(R.id.btnAnalyze)

        etUrl = findViewById(R.id.etUrl)

        progressBar = findViewById(R.id.progressBar)

        resultCard = findViewById(R.id.resultCard)

        txtVerdict = findViewById(R.id.txtVerdict)
        txtRiskScore = findViewById(R.id.txtRiskScore)

        txtHttps = findViewById(R.id.txtHttps)
        txtShortener = findViewById(R.id.txtShortener)
        txtIp = findViewById(R.id.txtIp)
        txtKeyword = findViewById(R.id.txtKeyword)
        txtLength = findViewById(R.id.txtLength)

        txtRecommendation = findViewById(R.id.txtRecommendation)

        riskProgress = findViewById(R.id.riskProgress)

    }

    private fun validateAndAnalyze() {

        val url = etUrl.text.toString().trim()

        if (url.isEmpty()) {

            etUrl.error = "Please enter a URL"

            return

        }

        if (!Patterns.WEB_URL.matcher(url).matches()) {

            etUrl.error = "Invalid URL"

            return

        }

        startFakeAnalysis(url)

    }

    private fun startFakeAnalysis(url: String) {

        isScanning = true

        progressBar.visibility = View.VISIBLE

        btnAnalyze.isEnabled = false

        btnAnalyze.text = "Scanning..."

        progressBar.postDelayed({

            progressBar.visibility = View.GONE

            btnAnalyze.isEnabled = true

            checkUrl(url)

        }, 1500)

    }

    private fun checkUrl(url: String) {

        lifecycleScope.launch {

            try {

                val ruleResult = UrlAnalyzer.analyze(url)

                val apiResult = repository.checkUrl(

                    BuildConfig.SAFE_BROWSING_API_KEY,
                    url

                )

                showFinalResult(ruleResult, apiResult)

            } catch (e: Exception) {

                progressBar.visibility = View.GONE

                btnAnalyze.isEnabled = true

                isScanning = false

                btnAnalyze.isEnabled = true

                btnAnalyze.text = "Scan URL"

                Toast.makeText(

                    this@UrlSafetyActivity,

                    "API Error: ${e.message}",

                    Toast.LENGTH_LONG

                ).show()

            }

        }

    }

    private fun showFinalResult(

        result: UrlAnalysisResult,

        googleDetected: Boolean

    ) {
        isScanning = false

        btnAnalyze.isEnabled = true

        btnAnalyze.text = "Scan URL"

        progressBar.visibility = View.GONE

        btnAnalyze.isEnabled = true

        resultCard.visibility = View.VISIBLE

        var score = result.riskScore

        if (googleDetected) {

            score += 40

            if (score > 100)
                score = 100

        }

        val verdict = when {

            score <= 20 -> "SAFE"

            score <= 40 -> "LOW RISK"

            score <= 60 -> "MEDIUM RISK"

            score <= 80 -> "HIGH RISK"

            else -> "DANGEROUS"

        }

        txtVerdict.text = verdict

        txtRiskScore.text = "Risk Score : $score%"

        riskProgress.progress = score

        txtHttps.text =
            if (result.httpsSafe)
                "✅ HTTPS Secure"
            else
                "❌ Uses HTTP"

        txtShortener.text =
            if (result.shortenerFound)
                "⚠ URL Shortener Found"
            else
                "✅ No URL Shortener"

        txtIp.text =
            if (result.ipAddressFound)
                "⚠ IP Address Used"
            else
                "✅ Domain Name"

        txtKeyword.text =
            if (result.suspiciousKeywordFound)
                "⚠ Suspicious Keywords"
            else
                "✅ No Suspicious Keywords"

        txtLength.text =
            if (result.longUrl)
                "⚠ Long URL"
            else
                "✅ Normal Length"

        txtRecommendation.text =
            if (googleDetected)
                "⚠ Google Safe Browsing reports this website as potentially dangerous. Avoid opening it."
            else
                result.recommendation

        ProgressManager.markPracticalCompleted(

            "URL Safety Checker",

            onSuccess = {

                Toast.makeText(
                    this,
                    "Practical Completed",
                    Toast.LENGTH_SHORT
                ).show()

            },

            onFailure = { message ->

                Toast.makeText(
                    this,
                    message,
                    Toast.LENGTH_SHORT
                ).show()

            }

        )
        saveScanHistory(

            etUrl.text.toString(),

            verdict,

            score

        )

    }

    private fun saveScanHistory(

        url: String,

        verdict: String,

        riskScore: Int

    ) {

        val uid =
            FirebaseAuth.getInstance()
                .currentUser?.uid
                ?: return

        val history = UrlHistory(

            url,

            verdict,

            riskScore,

            System.currentTimeMillis()

        )

        FirebaseFirestore.getInstance()

            .collection("url_history")

            .document(uid)

            .collection("scans")

            .add(history)

    }


}
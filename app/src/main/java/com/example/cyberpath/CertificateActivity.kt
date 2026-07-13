package com.example.cyberpath

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberpath.adapter.CertificateAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import com.example.cyberpath.model.Certificate
import com.example.cyberpath.model.CertificateHistory
import com.example.cyberpath.repository.CertificateRepository
import com.example.cyberpath.utils.PdfGenerator
import com.example.cyberpath.utils.ShareUtils
import androidx.recyclerview.widget.LinearLayoutManager

class CertificateActivity : AppCompatActivity() {

    // Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private val repository = CertificateRepository()
    private var currentCertificate: Certificate? = null
    // Toolbar
    private lateinit var btnBack: ImageButton

    // Certificate Views
    private lateinit var tvUserName: TextView
    private lateinit var tvCourseName: TextView
    private lateinit var tvCertificateId: TextView
    private lateinit var tvBestScore: TextView
    private lateinit var tvCompletionDate: TextView

    // Buttons
    private lateinit var btnDownload: Button
    private lateinit var btnShare: Button

    // History
    private lateinit var layoutEmpty: LinearLayout
    private lateinit var rvCertificates: RecyclerView
    private lateinit var adapter: CertificateAdapter

    private val historyList = ArrayList<CertificateHistory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certificate)

        initFirebase()
        initViews()
        adapter = CertificateAdapter(historyList)

        rvCertificates.layoutManager =
            LinearLayoutManager(this)

        rvCertificates.adapter = adapter

        setupClickListeners()

        checkCertificateEligibility()
        loadCertificateHistory()
    }

    private fun loadCertificateHistory() {

        val uid = auth.currentUser?.uid ?: return

        repository.getCertificateHistory(

            uid,

            onSuccess = { list ->

                historyList.clear()

                historyList.addAll(list)

                adapter.notifyDataSetChanged()

                if (historyList.isEmpty()) {

                    layoutEmpty.visibility = View.VISIBLE
                    rvCertificates.visibility = View.GONE

                } else {

                    layoutEmpty.visibility = View.GONE
                    rvCertificates.visibility = View.VISIBLE

                }

            },

            onFailure = {

                Toast.makeText(
                    this,
                    it.message,
                    Toast.LENGTH_SHORT
                ).show()

            }

        )

    }
    private fun initFirebase() {
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
    }

    private fun initViews() {

        btnBack = findViewById(R.id.btnBack)

        tvUserName = findViewById(R.id.tvUserName)
        tvCourseName = findViewById(R.id.tvCourseName)
        tvCertificateId = findViewById(R.id.tvCertificateId)
        tvBestScore = findViewById(R.id.tvBestScore)
        tvCompletionDate = findViewById(R.id.tvCompletionDate)

        btnDownload = findViewById(R.id.btnDownload)
        btnShare = findViewById(R.id.btnShare)

        layoutEmpty = findViewById(R.id.layoutEmpty)
        rvCertificates = findViewById(R.id.rvCertificates)
    }

    private fun setupClickListeners() {

        btnBack.setOnClickListener {
            finish()
        }

        btnDownload.setOnClickListener {

            val certificate = currentCertificate

            if (certificate == null) {
                Toast.makeText(this, "Certificate not available", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {

                val file = PdfGenerator.generateCertificate(
                    this,
                    certificate
                )

                Toast.makeText(
                    this,
                    "PDF Saved Successfully\n${file.absolutePath}",
                    Toast.LENGTH_LONG
                ).show()

            } catch (e: Exception) {

                Toast.makeText(
                    this,
                    e.message,
                    Toast.LENGTH_LONG
                ).show()

            }

        }

        btnShare.setOnClickListener {

            val certificate = currentCertificate

            if (certificate == null) {

                Toast.makeText(
                    this,
                    "Certificate not available.",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            try {

                val file = PdfGenerator.generateCertificate(
                    this,
                    certificate
                )

                ShareUtils.sharePdf(
                    this,
                    file
                )

            } catch (e: Exception) {

                Toast.makeText(
                    this,
                    e.message ?: "Unable to share certificate.",
                    Toast.LENGTH_LONG
                ).show()

            }

        }
    }

    private fun checkCertificateEligibility() {

        val uid = auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->

                if (!document.exists()) {
                    Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                val name = document.getString("name") ?: "CyberPath User"

                val completedTopics =
                    document.getLong("completedTopics")?.toInt() ?: 0

                val completedPracticals =
                    document.getLong("completedPracticals")?.toInt() ?: 0

                val completedQuizzes =
                    document.getLong("completedQuizzes")?.toInt() ?: 0

                val bestScore =
                    document.getLong("bestQuizScore")?.toInt() ?: 0

                if (completedTopics >= 8 &&
                    completedPracticals >= 4 &&
                    completedQuizzes >= 1
                ) {

                    displayCertificate(
                        userName = name,
                        bestScore = bestScore
                    )

                } else {

                    val message = """
                        Certificate Locked

                        Complete:
                        • All 8 Learning Topics
                        • All 4 Practicals
                        • At least 1 Quiz
                    """.trimIndent()

                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

                }

            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Failed to load certificate",
                    Toast.LENGTH_SHORT
                ).show()

            }

    }

    private fun displayCertificate(
        userName: String,
        bestScore: Int
    ) {

        val uid = auth.currentUser?.uid ?: return

        repository.getCertificate(

            uid,

            onSuccess = { certificate ->

                currentCertificate = certificate
                if (certificate != null) {

                    tvUserName.text = certificate.userName

                    tvCourseName.text =
                        "Cyber Security Awareness & Practical Training"

                    tvBestScore.text =
                        "${certificate.bestQuizScore}%"

                    tvCompletionDate.text =
                        certificate.issueDate

                    tvCertificateId.text =
                        certificate.certificateId

                } else {

                    createNewCertificate(
                        uid,
                        userName,
                        bestScore
                    )

                }

            },


            onFailure = {

                Toast.makeText(
                    this,
                    it.message,
                    Toast.LENGTH_SHORT
                ).show()

            }

        )

    }

    private fun createNewCertificate(

        uid: String,

        userName: String,

        bestScore: Int

    ) {

        val certificate = Certificate(

            certificateId = generateCertificateId(),

            userName = userName,

            email = auth.currentUser?.email ?: "",

            courseName = "Cyber Security Awareness & Practical Training",

            issueDate = getCurrentDate(),

            bestQuizScore = bestScore,

            completedTopics = 8,

            completedPracticals = 4,

            completedQuizzes = 1,

            verified = true

        )

        repository.saveCertificate(

            uid,

            certificate,

            onSuccess = {

                currentCertificate = certificate
                tvUserName.text = certificate.userName

                tvCourseName.text =
                    "Cyber Security Awareness & Practical Training"

                tvBestScore.text =
                    "${certificate.bestQuizScore}%"

                tvCompletionDate.text =
                    certificate.issueDate

                tvCertificateId.text =
                    certificate.certificateId

                Toast.makeText(

                    this,

                    "Certificate Generated Successfully",

                    Toast.LENGTH_SHORT

                ).show()

            },

            onFailure = {

                Toast.makeText(

                    this,

                    it.message,

                    Toast.LENGTH_SHORT

                ).show()

            }

        )

    }

    private fun generateCertificateId(): String {

        val year = SimpleDateFormat(
            "yyyy",
            Locale.getDefault()
        ).format(Date())

        val random =
            UUID.randomUUID().toString()
                .substring(0, 6)
                .uppercase()

        return "CYP-$year-$random"
    }

    private fun getCurrentDate(): String {

        return SimpleDateFormat(
            "dd MMM yyyy",
            Locale.getDefault()
        ).format(Date())

    }

}
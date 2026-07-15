package com.example.cyberpath

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberpath.adapter.CertificateAdapter
import com.example.cyberpath.model.Certificate
import com.example.cyberpath.model.CertificateHistory
import com.example.cyberpath.repository.CertificateRepository
import com.example.cyberpath.utils.NotificationUtils
import com.example.cyberpath.utils.PdfGenerator
import com.example.cyberpath.utils.ShareUtils
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class CertificateActivity : AppCompatActivity() {

    // Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private val repository = CertificateRepository()

    private var currentCertificate: Certificate? = null

    // Toolbar
    private lateinit var btnBack: ImageButton

    // Certificate Views
    private lateinit var certificateCard: LinearLayout
    private lateinit var layoutLocked: LinearLayout

    private lateinit var tvUserName: TextView
    private lateinit var tvCourseName: TextView
    private lateinit var tvCertificateId: TextView
    private lateinit var tvBestScore: TextView
    private lateinit var tvCompletionDate: TextView

    // Buttons
    private lateinit var btnDownload: Button
    private lateinit var btnShare: Button
    private lateinit var btnContinueLearning: MaterialButton

    private lateinit var txtVerifyStatus: TextView
    private lateinit var txtVerifyId: TextView
    private lateinit var txtVerifyDate: TextView

    // Locked Layout
    private lateinit var progressTopics: ProgressBar
    private lateinit var progressPracticals: ProgressBar
    private lateinit var progressQuiz: ProgressBar
    private lateinit var progressOverall: ProgressBar

    private lateinit var txtTopicsProgress: TextView
    private lateinit var txtPracticalsProgress: TextView
    private lateinit var txtQuizProgress: TextView
    private lateinit var txtOverallProgress: TextView
    private lateinit var txtRemaining: TextView

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

        setupRecyclerView()

        setupClickListeners()

        loadUserProgress()

        loadCertificateHistory()
    }

    private fun initFirebase() {

        auth = FirebaseAuth.getInstance()

        firestore = FirebaseFirestore.getInstance()

    }

    private fun initViews() {

        btnBack = findViewById(R.id.btnBack)

        certificateCard = findViewById(R.id.certificateCard)
        layoutLocked = findViewById(R.id.layoutLocked)

        tvUserName = findViewById(R.id.tvUserName)
        tvCourseName = findViewById(R.id.tvCourseName)
        tvCertificateId = findViewById(R.id.tvCertificateId)
        tvBestScore = findViewById(R.id.tvBestScore)
        tvCompletionDate = findViewById(R.id.tvCompletionDate)

        btnDownload = findViewById(R.id.btnDownload)
        btnShare = findViewById(R.id.btnShare)
        btnContinueLearning =
            findViewById(R.id.btnContinueLearning)

        progressTopics =
            findViewById(R.id.progressTopics)

        progressPracticals =
            findViewById(R.id.progressPracticals)

        progressQuiz =
            findViewById(R.id.progressQuiz)

        progressOverall =
            findViewById(R.id.progressOverall)

        txtTopicsProgress =
            findViewById(R.id.txtTopicsProgress)

        txtPracticalsProgress =
            findViewById(R.id.txtPracticalsProgress)

        txtQuizProgress =
            findViewById(R.id.txtQuizProgress)

        txtOverallProgress =
            findViewById(R.id.txtOverallProgress)

        txtRemaining =
            findViewById(R.id.txtRemaining)

        layoutEmpty =
            findViewById(R.id.layoutEmpty)

        rvCertificates =
            findViewById(R.id.rvCertificates)

        txtVerifyStatus =
            findViewById(R.id.txtVerifyStatus)

        txtVerifyId =
            findViewById(R.id.txtVerifyId)

        txtVerifyDate =
            findViewById(R.id.txtVerifyDate)

    }

    private fun setupRecyclerView() {

        adapter =
            CertificateAdapter(historyList)

        rvCertificates.layoutManager =
            LinearLayoutManager(this)

        rvCertificates.adapter =
            adapter

    }
    private fun setupClickListeners() {

        btnBack.setOnClickListener {
            finish()
        }

        btnDownload.setOnClickListener {

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

                NotificationUtils.showDownloadNotification(
                    this,
                    file
                )

                Toast.makeText(
                    this,
                    "Saved in Downloads/CyberPath",
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: Exception) {

                Toast.makeText(
                    this,
                    e.message ?: "Download failed",
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
                    Toast.LENGTH_SHORT
                ).show()

            }

        }

        btnContinueLearning.setOnClickListener {

            navigateToIncompleteModule()

        }

    }

    private fun loadUserProgress() {

        val uid = auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->

                if (!document.exists()) return@addOnSuccessListener

                val name =
                    document.getString("name") ?: "CyberPath User"

                val bestScore =
                    (document.getLong("bestQuizScore") ?: 0).toInt()

                val completedTopics =
                    (document.getLong("completedTopics") ?: 0).toInt()

                val completedPracticals =
                    (document.getLong("completedPracticals") ?: 0).toInt()

                val completedQuizzes =
                    (document.getLong("completedQuizzes") ?: 0).toInt()

                updateCertificateUI(
                    name,
                    bestScore,
                    completedTopics,
                    completedPracticals,
                    completedQuizzes
                )

            }

    }

    private fun updateCertificateUI(
        userName: String,
        bestScore: Int,
        topics: Int,
        practicals: Int,
        quizzes: Int
    ) {

        val unlocked =
            topics >= 8 &&
                    practicals >= 4 &&
                    quizzes >= 1

        if (unlocked) {

            showCertificateLayout()

            displayCertificate(
                userName,
                bestScore
            )

        } else {

            showLockedLayout(
                topics,
                practicals,
                quizzes
            )

        }

    }

    private fun showCertificateLayout() {

        layoutLocked.visibility = View.GONE

        certificateCard.visibility = View.VISIBLE

        btnDownload.visibility = View.VISIBLE

        btnShare.visibility = View.VISIBLE

        layoutEmpty.visibility = View.VISIBLE

        rvCertificates.visibility = View.VISIBLE

    }

    private fun showLockedLayout(
        topics: Int,
        practicals: Int,
        quizzes: Int
    ) {

        val topicCount = minOf(topics, 8)
        val practicalCount = minOf(practicals, 4)
        val quizCount = minOf(quizzes, 1)

        layoutLocked.visibility = View.VISIBLE

        certificateCard.visibility = View.GONE

        btnDownload.visibility = View.GONE

        btnShare.visibility = View.GONE

        layoutEmpty.visibility = View.GONE

        rvCertificates.visibility = View.GONE

        progressTopics.progress = topicCount
        progressPracticals.progress = practicalCount
        progressQuiz.progress = quizCount

        txtTopicsProgress.text =
            "$topicCount / 8 Completed"

        txtPracticalsProgress.text =
            "$practicalCount / 4 Completed"

        txtQuizProgress.text =
            "$quizCount / 1 Completed"

        val overall =
            ((topicCount + practicalCount + quizCount) * 100) / 13

        progressOverall.progress = overall

        txtOverallProgress.text =
            "$overall% Completed"

        val remainingTopics = 8 - topicCount
        val remainingPracticals = 4 - practicalCount
        val remainingQuiz = 1 - quizCount

        txtRemaining.text = when {
            remainingTopics > 0 ->
                "Complete $remainingTopics more learning topic(s)."

            remainingPracticals > 0 ->
                "Complete $remainingPracticals practical lab(s)."

            remainingQuiz > 0 ->
                "Complete one quiz to unlock the certificate."

            else ->
                "Certificate Ready!"
        }

    }

    private fun navigateToIncompleteModule() {

        val uid = auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->

                val topics =
                    (document.getLong("completedTopics") ?: 0).toInt()

                val practicals =
                    (document.getLong("completedPracticals") ?: 0).toInt()

                val quizzes =
                    (document.getLong("completedQuizzes") ?: 0).toInt()

                when {

                    topics < 8 -> {

                        startActivity(
                            Intent(
                                this,
                                LearningActivity::class.java
                            )
                        )

                    }

                    practicals < 4 -> {

                        startActivity(
                            Intent(
                                this,
                                PracticalActivity::class.java
                            )
                        )

                    }

                    quizzes < 1 -> {

                        startActivity(
                            Intent(
                                this,
                                QuizActivity::class.java
                            )
                        )

                    }

                }

            }

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
                    it.message ?: "Unable to load history",
                    Toast.LENGTH_SHORT
                ).show()

            }

        )

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
                        certificate.courseName

                    tvCertificateId.text =
                        certificate.certificateId

                    txtVerifyStatus.text =
                        "Verified ✔"

                    txtVerifyId.text =
                        "Certificate ID : ${certificate.certificateId}"

                    txtVerifyDate.text =
                        "Issued : ${certificate.issueDate}"

                    tvBestScore.text =
                        "${certificate.bestQuizScore}%"

                    tvCompletionDate.text =
                        certificate.issueDate

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
                    it.message ?: "Unable to load certificate",
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

                tvCourseName.text = certificate.courseName

                tvCertificateId.text = certificate.certificateId

                tvBestScore.text = "${certificate.bestQuizScore}%"

                tvCompletionDate.text = certificate.issueDate

                Toast.makeText(
                    this,
                    "Certificate Generated Successfully",
                    Toast.LENGTH_SHORT
                ).show()

            },

            onFailure = {

                Toast.makeText(
                    this,
                    it.message ?: "Unable to generate certificate",
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

        val random = UUID.randomUUID()
            .toString()
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
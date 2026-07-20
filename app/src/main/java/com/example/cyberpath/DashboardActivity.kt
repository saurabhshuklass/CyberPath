package com.example.cyberpath

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.ImageButton
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cyberpath.adapter.NewsAdapter
import com.example.cyberpath.repository.NewsRepository
import kotlinx.coroutines.launch
import com.airbnb.lottie.LottieAnimationView
import com.example.cyberpath.utils.NewsCacheManager
import com.example.cyberpath.utils.NetworkUtils

class DashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var txtCertificateStatus: TextView
    private lateinit var txtName: TextView
    private lateinit var txtProgressPercent: TextView
    private lateinit var txtProgressInfo: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnProfile: ImageButton
    private lateinit var rvNews: RecyclerView
    private lateinit var swipeRefreshNews: SwipeRefreshLayout
    private lateinit var lottieLoading: LottieAnimationView
    private val newsRepository = NewsRepository()
    private lateinit var newsCacheManager: NewsCacheManager
    private lateinit var layoutEmptyState: LinearLayout
    private lateinit var txtOfflineMode: TextView
    private val NEWS_API_KEY = BuildConfig.NEWS_API_KEY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        newsCacheManager = NewsCacheManager(this)
        txtCertificateStatus = findViewById(R.id.txtCertificateStatus)
        txtName = findViewById(R.id.txtName)
        txtProgressPercent = findViewById(R.id.txtProgressPercent)
        txtProgressInfo = findViewById(R.id.txtProgressInfo)
        progressBar = findViewById(R.id.progressBar)
        btnProfile = findViewById(R.id.btnProfile)
        rvNews = findViewById(R.id.rvNews)
        swipeRefreshNews = findViewById(R.id.swipeRefreshNews)
        lottieLoading = findViewById(R.id.lottieLoading)
        layoutEmptyState = findViewById(R.id.layoutEmptyState)
        txtOfflineMode = findViewById(R.id.txtOfflineMode)
        rvNews.layoutManager = LinearLayoutManager(this)
        rvNews.setHasFixedSize(true)


        loadUserData()
        loadNews()

        swipeRefreshNews.setOnRefreshListener {
            loadNews()
        }

        requestNotificationPermission()

        findViewById<LinearLayout>(R.id.cardLearning)
            .setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        LearningActivity::class.java
                    )
                )
            }

        findViewById<LinearLayout>(R.id.cardPractice)
            .setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        PracticalActivity::class.java
                    )
                )
            }

        findViewById<LinearLayout>(R.id.cardQuiz)
            .setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        QuizActivity::class.java
                    )
                )
            }

        findViewById<LinearLayout>(R.id.cardCertificate)
            .setOnClickListener {

                startActivity(
                    Intent(
                        this,
                        CertificateActivity::class.java
                    )
                )

            }

        btnProfile.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ProfileActivity::class.java
                )
            )

        }
    }

    private fun loadUserData() {

        val uid = auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->

                if (document.exists()) {

                    val name =
                        document.getString("name") ?: "User"

                    val completed =
                        document.getLong("completedTopics") ?: 0

                    val completedPracticals =
                        document.getLong("completedPracticals") ?: 0

                    val completedQuizzes =
                        document.getLong("completedQuizzes") ?: 0

                    txtName.text = "$name 👋"

                    val percent =
                        ((completed.toDouble() / 8.0) * 100).toInt()

                    txtProgressPercent.text =
                        "$percent%"

                    progressBar.progress =
                        percent

                    txtProgressInfo.text =
                        "$completed of 8 topics completed"

                    val certificateUnlocked =
                        completed >= 8 &&
                                completedPracticals >= 4 &&
                                completedQuizzes >= 1

                    if (certificateUnlocked) {

                        txtCertificateStatus.text =
                            "🏆 Available"

                    } else {

                        txtCertificateStatus.text =
                            "🔒 Locked"

                    }
                }
            }
    }

    private fun loadNews() {

        lottieLoading.visibility = View.VISIBLE
        layoutEmptyState.visibility = View.GONE
        txtOfflineMode.visibility = View.GONE
        rvNews.visibility = View.GONE

        if (!NetworkUtils.isInternetAvailable(this)) {

            val cachedNews = newsCacheManager.getNews()

            lottieLoading.visibility = View.GONE

            if (cachedNews.isNotEmpty()) {

                txtOfflineMode.visibility = View.VISIBLE

                rvNews.visibility = View.VISIBLE

                layoutEmptyState.visibility = View.GONE

                rvNews.adapter = NewsAdapter(cachedNews)

            } else {

                layoutEmptyState.visibility = View.VISIBLE

                rvNews.visibility = View.GONE

            }

            return

        }

        lifecycleScope.launch {

            try {

                val newsList =
                    newsRepository.getCyberSecurityNews(NEWS_API_KEY)

                if (newsList.isNotEmpty()) {

                    // Save latest news for offline mode
                    newsCacheManager.saveNews(newsList)

                    rvNews.adapter = NewsAdapter(newsList)

                    layoutEmptyState.visibility = View.GONE
                    rvNews.visibility = View.VISIBLE

                } else {

                    // API returned no news
                    layoutEmptyState.visibility = View.VISIBLE
                    rvNews.visibility = View.GONE

                }

            } catch (e: Exception) {

                e.printStackTrace()

                // Try loading cached news
                val cachedNews =
                    newsCacheManager.getNews()

                if (cachedNews.isNotEmpty()) {

                    rvNews.adapter = NewsAdapter(cachedNews)

                    layoutEmptyState.visibility = View.GONE
                    rvNews.visibility = View.VISIBLE

                    txtOfflineMode.visibility = View.VISIBLE

                } else {

                    layoutEmptyState.visibility = View.VISIBLE
                    rvNews.visibility = View.GONE

                }

            } finally {

                lottieLoading.visibility = View.GONE

                if (::swipeRefreshNews.isInitialized) {
                    swipeRefreshNews.isRefreshing = false
                }

            }

        }

    }

    override fun onResume() {
        super.onResume()

        loadUserData()
    }

    private fun requestNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )

            }

        }

    }
}
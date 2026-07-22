package com.example.cyberpath

import android.Manifest
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.example.cyberpath.adapter.NewsAdapter
import com.example.cyberpath.repository.NewsRepository
import com.example.cyberpath.utils.NetworkUtils
import com.example.cyberpath.utils.NewsCacheManager
import com.example.cyberpath.utils.RecyclerAnimation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.cyberpath.utils.ClickAnimation
import com.example.cyberpath.notifications.NotificationHelper



class DashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private lateinit var txtCertificateStatus: TextView
    private lateinit var txtName: TextView
    private lateinit var txtProgressPercent: TextView
    private lateinit var txtProgressInfo: TextView
    private lateinit var txtMotivation: TextView
    private lateinit var txtLastUpdated: TextView
    private lateinit var txtOfflineMode: TextView

    private lateinit var progressBar: ProgressBar

    private lateinit var btnProfile: ImageButton

    private lateinit var rvNews: RecyclerView
    private lateinit var swipeRefreshNews: SwipeRefreshLayout

    private lateinit var lottieLoading: LottieAnimationView
    private lateinit var layoutEmptyState: LinearLayout

    private lateinit var newsCacheManager: NewsCacheManager

    private val newsRepository = NewsRepository()

    private val NEWS_API_KEY = BuildConfig.NEWS_API_KEY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        NotificationHelper.createChannel(this)
        newsCacheManager = NewsCacheManager(this)

        txtCertificateStatus = findViewById(R.id.txtCertificateStatus)
        txtName = findViewById(R.id.txtName)
        txtProgressPercent = findViewById(R.id.txtProgressPercent)
        txtProgressInfo = findViewById(R.id.txtProgressInfo)
        txtMotivation = findViewById(R.id.txtMotivation)
        txtLastUpdated = findViewById(R.id.txtLastUpdated)
        txtOfflineMode = findViewById(R.id.txtOfflineMode)

        progressBar = findViewById(R.id.progressBar)

        btnProfile = findViewById(R.id.btnProfile)

        rvNews = findViewById(R.id.rvNews)
        swipeRefreshNews = findViewById(R.id.swipeRefreshNews)

        lottieLoading = findViewById(R.id.lottieLoading)
        layoutEmptyState = findViewById(R.id.layoutEmptyState)

        rvNews.layoutManager = LinearLayoutManager(this)
        rvNews.setHasFixedSize(true)

        loadUserData()
        loadNews()

        swipeRefreshNews.setOnRefreshListener {
            loadNews()
        }

        requestNotificationPermission()

        findViewById<LinearLayout>(R.id.cardLearning).setOnClickListener {

            ClickAnimation.animate(it) {

                startActivity(
                    Intent(
                        this,
                        LearningActivity::class.java
                    )
                )

                overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )

            }

        }

        findViewById<LinearLayout>(R.id.cardPractice).setOnClickListener {

            ClickAnimation.animate(it) {

                startActivity(
                    Intent(
                        this,
                        PracticalActivity::class.java
                    )
                )

                overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )

            }

        }

        findViewById<LinearLayout>(R.id.cardQuiz).setOnClickListener {

            ClickAnimation.animate(it) {

                startActivity(
                    Intent(
                        this,
                        QuizActivity::class.java
                    )
                )

                overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )

            }

        }

        findViewById<LinearLayout>(R.id.cardCertificate).setOnClickListener {

            ClickAnimation.animate(it) {

                startActivity(
                    Intent(
                        this,
                        CertificateActivity::class.java
                    )
                )

                overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )

            }

        }

        btnProfile.setOnClickListener {

            ClickAnimation.animate(it) {

                startActivity(
                    Intent(
                        this,
                        ProfileActivity::class.java
                    )
                )

                overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )

            }

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
                        "$percent% Completed"

                    ObjectAnimator.ofInt(
                        progressBar,
                        "progress",
                        0,
                        percent
                    ).apply {

                        duration = 1000
                        interpolator = DecelerateInterpolator()
                        start()

                    }

                    txtProgressInfo.text =
                        "$completed of 8 topics completed"

                    txtMotivation.text = when {

                        completed == 0L ->
                            "Let's begin your cyber security journey! 🚀"

                        completed in 1..3 ->
                            "Great start! Keep learning. 💪"

                        completed in 4..6 ->
                            "You're making excellent progress! 🔥"

                        completed == 7L ->
                            "One more topic to unlock your certificate! 🏆"

                        else ->
                            "Congratulations! Course completed! 🎉"

                    }

                    val certificateUnlocked =
                        completed >= 8 &&
                                completedPracticals >= 4 &&
                                completedQuizzes >= 1

                    txtCertificateStatus.text =
                        if (certificateUnlocked)
                            "🏆 Available"
                        else
                            "🔒 Locked"

                }

            }

    }

    private fun loadNews() {

        lottieLoading.visibility = View.VISIBLE
        layoutEmptyState.visibility = View.GONE
        txtOfflineMode.visibility = View.GONE
        rvNews.visibility = View.GONE

        // Check internet first
        if (!NetworkUtils.isInternetAvailable(this)) {

            lottieLoading.visibility = View.GONE

            val cachedNews =
                newsCacheManager.getNews()

            if (cachedNews.isNotEmpty()) {

                rvNews.adapter = NewsAdapter(cachedNews)

                RecyclerAnimation.animate(rvNews)

                rvNews.visibility = View.VISIBLE

                txtOfflineMode.visibility = View.VISIBLE

                layoutEmptyState.visibility = View.GONE

                txtLastUpdated.text =
                    "Showing Cached News"

            } else {

                layoutEmptyState.visibility = View.VISIBLE
                rvNews.visibility = View.GONE

            }

            if (::swipeRefreshNews.isInitialized) {
                swipeRefreshNews.isRefreshing = false
            }

            return
        }

        lifecycleScope.launch {

            try {

                val newsList =
                    newsRepository.getCyberSecurityNews(
                        NEWS_API_KEY
                    )

                if (newsList.isNotEmpty()) {

                    newsCacheManager.saveNews(newsList)

                    rvNews.adapter =
                        NewsAdapter(newsList)
                    NotificationHelper.showNotification(
                        this@DashboardActivity,
                        "🛡 CyberPath",
                        "New Cyber Security News Available!",
                        101
                    )

                    RecyclerAnimation.animate(rvNews)

                    rvNews.visibility = View.VISIBLE

                    layoutEmptyState.visibility = View.GONE

                    txtOfflineMode.visibility = View.GONE

                    txtLastUpdated.text =
                        "Updated: ${
                            SimpleDateFormat(
                                "hh:mm a",
                                Locale.getDefault()
                            ).format(Date())
                        }"

                } else {

                    layoutEmptyState.visibility = View.VISIBLE
                    rvNews.visibility = View.GONE

                }

            } catch (e: Exception) {

                e.printStackTrace()

                val cachedNews =
                    newsCacheManager.getNews()

                if (cachedNews.isNotEmpty()) {

                    rvNews.adapter =
                        NewsAdapter(cachedNews)

                    RecyclerAnimation.animate(rvNews)

                    rvNews.visibility = View.VISIBLE

                    txtOfflineMode.visibility = View.VISIBLE

                    layoutEmptyState.visibility = View.GONE

                    txtLastUpdated.text =
                        "Showing Cached News"

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

    override fun finish() {
        super.finish()

        overridePendingTransition(
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
    }
}
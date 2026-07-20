package com.example.cyberpath

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.example.cyberpath.intro.IntroActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        progressBar = findViewById(R.id.progressBar)

        val animator = ValueAnimator.ofInt(0, 100)

        animator.duration = 2000

        animator.interpolator =
            AccelerateDecelerateInterpolator()

        animator.addUpdateListener {

            progressBar.progress =
                it.animatedValue as Int

        }

        animator.start()

        animator.doOnEnd {

            val pref = getSharedPreferences(
                "CyberPathPref",
                Context.MODE_PRIVATE
            )

            val keepLogin =
                pref.getBoolean("keepLogin", false)

            val introSeen =
                pref.getBoolean("introSeen", false)

            val currentUser =
                FirebaseAuth.getInstance().currentUser

            when {

                keepLogin && currentUser != null -> {

                    startActivity(
                        Intent(
                            this,
                            DashboardActivity::class.java
                        )
                    )

                }

                introSeen -> {

                    FirebaseAuth.getInstance().signOut()

                    startActivity(
                        Intent(
                            this,
                            LoginActivity::class.java
                        )
                    )

                }

                else -> {

                    startActivity(
                        Intent(
                            this,
                            IntroActivity::class.java
                        )
                    )

                }

            }

            finish()

        }

    }

}
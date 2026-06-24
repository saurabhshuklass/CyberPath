package com.example.cyberpath

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd

class SplashActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        progressBar = findViewById(R.id.progressBar)

        val animator = ValueAnimator.ofInt(0, 100)
        animator.duration = 3000
        animator.interpolator = AccelerateDecelerateInterpolator()

        animator.addUpdateListener {
            progressBar.progress = it.animatedValue as Int
        }

        animator.start()

        animator.doOnEnd {
            startActivity(
                Intent( this, com.example.cyberpath.intro.IntroActivity::class.java)
            )
            finish()
        }
    }
}
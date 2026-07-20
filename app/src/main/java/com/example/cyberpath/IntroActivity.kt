package com.example.cyberpath.intro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.cyberpath.LoginActivity
import com.example.cyberpath.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import android.content.Context
class IntroActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var btnNext: Button
    private lateinit var txtSkip: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_intro)

        viewPager = findViewById(R.id.viewPager)
        btnNext = findViewById(R.id.btnNext)
        txtSkip = findViewById(R.id.txtSkip)

        val introList = listOf(

            IntroModel(
                R.drawable.shield,
                "Welcome to CyberPath",
                "Learn cybersecurity concepts through interactive lessons and practical training."
            ),

            IntroModel(
                R.drawable.book,
                "Learn Security Concepts",
                "Study Password Security, Phishing, Malware, Network Security and more."
            ),

            IntroModel(
                R.drawable.practical,
                "Practice & Get Certified",
                "Complete practical exercises, quizzes and earn your certificate."
            )

        )

        val adapter = IntroAdapter(introList)

        viewPager.adapter = adapter

        val tabLayout = findViewById<TabLayout>(R.id.tabIndicator)

        TabLayoutMediator(
            tabLayout,
            viewPager
        ) { _, _ -> }.attach()

        btnNext.setOnClickListener {

            if (viewPager.currentItem < introList.size - 1) {

                viewPager.currentItem =
                    viewPager.currentItem + 1

            } else {

                val pref = getSharedPreferences(
                    "CyberPathPref",
                    Context.MODE_PRIVATE
                )

                pref.edit()
                    .putBoolean("introSeen", true)
                    .apply()

                startActivity(
                    Intent(
                        this,
                        LoginActivity::class.java
                    )
                )

                finish()
            }
        }

        txtSkip.setOnClickListener {

            val pref = getSharedPreferences(
                "CyberPathPref",
                Context.MODE_PRIVATE
            )

            pref.edit()
                .putBoolean("introSeen", true)
                .apply()

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

            finish()

        }

        viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    if (position == introList.size - 1) {
                        btnNext.text = "Get Started"
                    } else {
                        btnNext.text = "Next"
                    }
                }
            }
        )
    }
}
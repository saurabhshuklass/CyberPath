package com.example.cyberpath

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.io.File
import java.io.FileOutputStream

class TopicDetailActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_detail)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val imgTopic =
            findViewById<ImageView>(R.id.imgTopic)

        val txtTitle =
            findViewById<TextView>(R.id.txtTitle)

        val txtContent =
            findViewById<TextView>(R.id.txtContent)

        val btnOpenPdf =
            findViewById<Button>(R.id.btnOpenPdf)

        val btnComplete =
            findViewById<Button>(R.id.btnComplete)

        val topicTitle =
            intent.getStringExtra("topicTitle") ?: ""

        txtTitle.text = topicTitle

        txtContent.text =
            getTopicContent(topicTitle)

        // Topic Images

        when (topicTitle) {

            "Introduction to Cyber Security" ->
                imgTopic.setImageResource(R.drawable.topic_intro)

            "Cyber Threats & Attacks" ->
                imgTopic.setImageResource(R.drawable.topic_threats)

            "Password Security" ->
                imgTopic.setImageResource(R.drawable.topic_password)

            "Phishing Attacks" ->
                imgTopic.setImageResource(R.drawable.topic_phishing)

            "Malware & Ransomware" ->
                imgTopic.setImageResource(R.drawable.topic_malware)

            "Social Engineering" ->
                imgTopic.setImageResource(R.drawable.topic_social)

            "Network Security" ->
                imgTopic.setImageResource(R.drawable.topic_network)

            "Safe Browsing Practices" ->
                imgTopic.setImageResource(R.drawable.topic_browsing)
        }

        // Open PDF

        btnOpenPdf.setOnClickListener {

            val pdfName = when (topicTitle) {

                "Introduction to Cyber Security" ->
                    "Saurabh's Resume.pdf"

                "Cyber Threats & Attacks" ->
                    "Saurabh's Resume.pdf"

                "Password Security" ->
                    "Saurabh's Resume.pdf"

                "Phishing Attacks" ->
                    "Saurabh's Resume.pdf"

                "Malware & Ransomware" ->
                    "Saurabh's Resume.pdf"

                "Social Engineering" ->
                    "Saurabh's Resume.pdf"

                "Network Security" ->
                    "Saurabh's Resume.pdf"

                "Safe Browsing Practices" ->
                    "Saurabh's Resume.pdf"

                else -> ""
            }

            openPdfFromAssets(pdfName)
        }

        // Mark Completed

        btnComplete.setOnClickListener {

            val uid =
                auth.currentUser?.uid
                    ?: return@setOnClickListener

            val progressRef =
                firestore.collection("user_progress")
                    .document(uid)

            progressRef.get()
                .addOnSuccessListener { document ->

                    if (document.getBoolean(topicTitle) == true) {

                        Toast.makeText(
                            this,
                            "Topic Already Completed",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        progressRef.set(
                            mapOf(topicTitle to true),
                            SetOptions.merge()
                        )
                            .addOnSuccessListener {

                                firestore.collection("users")
                                    .document(uid)
                                    .update(
                                        "completedTopics",
                                        FieldValue.increment(1)
                                    )
                                    .addOnSuccessListener {

                                        Toast.makeText(
                                            this,
                                            "Topic Completed Successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        finish()
                                    }
                            }
                    }
                }
        }
    }

    private fun openPdfFromAssets(
        pdfName: String
    ) {

        try {

            val file =
                File(cacheDir, pdfName)

            if (!file.exists()) {

                assets.open(pdfName).use { input ->

                    FileOutputStream(file).use { output ->

                        input.copyTo(output)
                    }
                }
            }

            val uri =
                FileProvider.getUriForFile(
                    this,
                    "$packageName.provider",
                    file
                )

            val intent =
                Intent(Intent.ACTION_VIEW)

            intent.setDataAndType(
                uri,
                "application/pdf"
            )

            intent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            startActivity(intent)

        } catch (e: Exception) {

            Toast.makeText(
                this,
                "PDF not found",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getTopicContent(
        topic: String
    ): String {

        return when (topic) {

            "Introduction to Cyber Security" ->
                "Learn about Cyber Security, CIA Triad, Security Goals and Real World Applications."

            "Cyber Threats & Attacks" ->
                "Learn about Malware, Ransomware, DDoS Attacks and Modern Cyber Threats."

            "Password Security" ->
                "Understand Strong Passwords, MFA and Password Protection Techniques."

            "Phishing Attacks" ->
                "Learn how attackers use fake emails and websites to steal information."

            "Malware & Ransomware" ->
                "Explore Viruses, Worms, Trojans, Spyware and Ransomware attacks."

            "Social Engineering" ->
                "Understand how attackers manipulate human psychology to gain access."

            "Network Security" ->
                "Learn OSI Model, TCP/IP Model, Firewalls, VPNs and Network Protection."

            "Safe Browsing Practices" ->
                "Learn safe internet usage, HTTPS, browser security and download protection."

            else ->
                "No content available."
        }
    }
}
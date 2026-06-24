package com.example.cyberpath

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberpath.adapters.TopicAdapter
import com.example.cyberpath.models.TopicModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LearningActivity : AppCompatActivity() {

    private lateinit var recyclerTopics: RecyclerView
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var topicAdapter: TopicAdapter
    private val topics = ArrayList<TopicModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learning)

        recyclerTopics = findViewById(R.id.recyclerTopics)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        topics.add(
            TopicModel(
                R.drawable.shield,
                "Introduction to Cyber Security"
            )
        )

        topics.add(
            TopicModel(
                R.drawable.warning,
                "Cyber Threats & Attacks"
            )
        )

        topics.add(
            TopicModel(
                R.drawable.lock,
                "Password Security"
            )
        )

        topics.add(
            TopicModel(
                R.drawable.email,
                "Phishing Attacks"
            )
        )

        topics.add(
            TopicModel(
                R.drawable.bug,
                "Malware & Ransomware"
            )
        )

        topics.add(
            TopicModel(
                R.drawable.people,
                "Social Engineering"
            )
        )

        topics.add(
            TopicModel(
                R.drawable.wifi,
                "Network Security"
            )
        )

        topics.add(
            TopicModel(
                R.drawable.browser,
                "Safe Browsing Practices"
            )
        )

        topicAdapter = TopicAdapter(topics)

        recyclerTopics.layoutManager =
            LinearLayoutManager(this)

        recyclerTopics.adapter =
            topicAdapter

        loadCompletedTopics()
    }

    override fun onResume() {
        super.onResume()
        loadCompletedTopics()
    }

    private fun loadCompletedTopics() {

        val uid =
            auth.currentUser?.uid ?: return

        firestore.collection("user_progress")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->

                for (topic in topics) {

                    topic.isCompleted =
                        document.getBoolean(topic.title) == true
                }

                topicAdapter.notifyDataSetChanged()
            }
    }
}
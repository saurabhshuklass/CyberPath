package com.example.cyberpath.model

import com.google.firebase.Timestamp

data class Certificate(

    val certificateId: String = "",

    val userName: String = "",

    val email: String = "",

    val courseName: String = "Cyber Security Awareness & Practical Training",

    val issueDate: String = "",

    val bestQuizScore: Int = 0,

    val completedTopics: Int = 0,

    val completedPracticals: Int = 0,

    val completedQuizzes: Int = 0,

    val verified: Boolean = true,

    val generatedAt: Timestamp = Timestamp.now()

)
package com.example.cyberpath.models

data class User(

    val uid: String = "",

    val name: String = "",

    val email: String = "",

    val completedTopics: Int = 0,

    val completedPracticals: Int = 0,

    val quizScore: Int = 0

)
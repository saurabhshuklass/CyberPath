package com.example.cyberpath.model

data class Scenario(

    val id: Int,

    val title: String,

    val type: String,

    val message: String,

    val warningSigns: List<String>,

    val options: List<String>,

    val correctAnswer: Int,

    val explanation: String,

    val cyberTip: String

)
package com.example.cyberpath.models

data class EmailModel(

    val sender: String,

    val subject: String,

    val body: String,

    val isPhishing: Boolean,

    val explanation: List<String>

)
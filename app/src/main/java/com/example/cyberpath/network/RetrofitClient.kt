package com.example.cyberpath.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val api: SafeBrowsingApi by lazy {

        Retrofit.Builder()

            .baseUrl("https://safebrowsing.googleapis.com/")

            .addConverterFactory(
                GsonConverterFactory.create()
            )

            .build()

            .create(SafeBrowsingApi::class.java)

    }

}
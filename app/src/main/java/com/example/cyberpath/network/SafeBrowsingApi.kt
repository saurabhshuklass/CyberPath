package com.example.cyberpath.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface SafeBrowsingApi {

    @POST("v4/threatMatches:find")
    suspend fun checkUrl(

        @Query("key")
        apiKey: String,

        @Body
        request: SafeBrowsingRequest

    ): Response<SafeBrowsingResponse>

}
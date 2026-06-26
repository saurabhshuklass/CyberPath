package com.example.cyberpath.network

class SafeBrowsingRepository {

    suspend fun checkUrl(
        apiKey: String,
        url: String
    ): Boolean {

        val request = SafeBrowsingRequest(

            client = Client(
                clientId = "CyberPath",
                clientVersion = "1.0"
            ),

            threatInfo = ThreatInfo(

                threatTypes = listOf(
                    "MALWARE",
                    "SOCIAL_ENGINEERING",
                    "UNWANTED_SOFTWARE"
                ),

                platformTypes = listOf(
                    "ANY_PLATFORM"
                ),

                threatEntryTypes = listOf(
                    "URL"
                ),

                threatEntries = listOf(
                    ThreatEntry(url)
                )

            )

        )

        val response =
            RetrofitClient.api.checkUrl(
                apiKey,
                request
            )

        return response.body()?.matches != null

    }

}
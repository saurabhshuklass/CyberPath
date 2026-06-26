package com.example.cyberpath.network

data class SafeBrowsingResponse(

    val matches: List<ThreatMatch>?

)

data class ThreatMatch(

    val threatType: String,

    val platformType: String,

    val threatEntryType: String,

    val threat: ThreatEntry

)
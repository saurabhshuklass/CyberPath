package com.example.cyberpath.network

data class VirusTotalResponse(

    val data: VirusData

)

data class VirusData(

    val attributes: VirusAttributes

)

data class VirusAttributes(

    val last_analysis_stats: AnalysisStats,

    val reputation: Int

)

data class AnalysisStats(

    val harmless: Int,

    val malicious: Int,

    val suspicious: Int,

    val undetected: Int

)
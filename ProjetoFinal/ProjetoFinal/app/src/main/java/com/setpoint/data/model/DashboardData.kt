package com.setpoint.data.model

data class DashboardData(
    val teamName: String = "",
    val matchesPlayed: Int = 0,
    val wins: Int = 0,
    val losses: Int = 0,
    val winRate: Int = 0,
    val teamPlayers: Int = 0,
    val nextMatch: NextMatchData? = null,
    val recentMatches: List<Match> = emptyList()
)
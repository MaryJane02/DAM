package com.setpoint.data.model

data class Match(
    val id: String = "",
    val opponent: String = "",
    val date: String = "",
    val time: String = "",
    val location: String = "",
    val status: String = "",
    val lineup: MatchLineup = MatchLineup(),
    val currentSet: Int = 1,
    val teamScore: Int = 0,
    val opponentScore: Int = 0,
    val teamSetsWon: Int = 0,
    val opponentSetsWon: Int = 0,
    val setResults: List<SetResults> = emptyList()
)
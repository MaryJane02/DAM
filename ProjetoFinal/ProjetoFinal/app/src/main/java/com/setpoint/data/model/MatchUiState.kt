package com.setpoint.data.model

data class MatchUiState(
    val isLoading: Boolean = false,
    val matches: List<Match> = emptyList(),
    val selectedMatch: Match? = null,
    val playerStats: List<MatchPlayerStats> = emptyList(),
    val seasonPlayerStats: List<SeasonPlayerStats> = emptyList(),
    val errorMessage: String? = null
)
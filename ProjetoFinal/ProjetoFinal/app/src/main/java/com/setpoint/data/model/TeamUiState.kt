package com.setpoint.data.model

data class TeamUiState(
    val isLoading: Boolean = false,
    val team: Team? = null,
    val errorMessage: String? = null
)
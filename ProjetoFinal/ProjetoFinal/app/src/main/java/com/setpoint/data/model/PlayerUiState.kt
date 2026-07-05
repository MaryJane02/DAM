package com.setpoint.data.model

data class PlayerUiState(
    val isLoading: Boolean = false,
    val players: List<Player> = emptyList(),
    val selectedPlayer: Player? = null,
    val errorMessage: String? = null
)
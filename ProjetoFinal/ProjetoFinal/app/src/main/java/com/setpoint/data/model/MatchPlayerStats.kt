package com.setpoint.data.model

data class MatchPlayerStats(
    val playerId: String = "",
    val playerName: String = "",
    val playerNumber: Int = 0,
    val points: Int = 0,
    val errors: Int = 0,
    val kills: Int = 0,
    val aces: Int = 0,
    val blocks: Int = 0
)
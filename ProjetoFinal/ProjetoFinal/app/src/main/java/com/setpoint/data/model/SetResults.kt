package com.setpoint.data.model

data class SetResults(
    val setNumber: Int = 1,
    val teamScore: Int = 0,
    val opponentScore: Int = 0,
    val winner: String = "" // "team" ou "opponent"
)
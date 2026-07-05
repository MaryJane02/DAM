package com.setpoint.data.model

data class NextMatchData(
    val match: Match,
    val isLive: Boolean = false,
    val showTrackButton: Boolean = false
)
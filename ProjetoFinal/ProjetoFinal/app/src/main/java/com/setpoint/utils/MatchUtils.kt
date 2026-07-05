package com.setpoint.utils

import com.setpoint.data.model.Match
import java.time.LocalDateTime

fun getMatchDisplayStatus(match: Match): String {
    if (match.status == "finished") {
        return "finished"
    }

    val dateParts = match.date.split("/")
    val timeParts = match.time.split(":")

    val day = dateParts.getOrNull(0)?.toIntOrNull() ?: return "scheduled"
    val month = dateParts.getOrNull(1)?.toIntOrNull() ?: return "scheduled"
    val year = dateParts.getOrNull(2)?.toIntOrNull() ?: return "scheduled"

    val hour = timeParts.getOrNull(0)?.toIntOrNull() ?: 0
    val minute = timeParts.getOrNull(1)?.toIntOrNull() ?: 0

    return try {
        val matchDateTime = LocalDateTime.of(
            year,
            month,
            day,
            hour,
            minute
        )

        if (LocalDateTime.now().isBefore(matchDateTime)) {
            "scheduled"
        } else {
            "live"
        }
    } catch (e: Exception) {
        "scheduled"
    }
}
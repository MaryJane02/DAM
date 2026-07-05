package com.setpoint.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun getCurrentDateText(): String {
    val formatter = DateTimeFormatter.ofPattern(
        "EEEE, MMMM d",
        Locale.ENGLISH
    )

    return LocalDate.now()
        .format(formatter)
        .uppercase()
}

fun getCurrentSeasonText(): String {
    val today = LocalDate.now()
    val startYear = if (today.monthValue >= 9) {
        today.year
    } else {
        today.year - 1
    }

    val endYear = startYear + 1

    return "SEASON $startYear/$endYear"
}
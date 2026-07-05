package com.setpoint.utils

fun getPlayerInitials(name: String): String {
    return name
        .trim()
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { word ->
            word.first().uppercase()
        }
}
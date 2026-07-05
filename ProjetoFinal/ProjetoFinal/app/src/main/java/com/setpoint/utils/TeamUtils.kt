package com.setpoint.utils

fun getInitials(value: String): String {
    return value
        .trim()
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercase() }
        .ifBlank { "TM" }
}
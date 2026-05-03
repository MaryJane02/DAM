package com.example.cooljetpackweatherapp.ui

data class WeatherUIState(
    val latitude: Float = 38.7369f,
    val longitude: Float = -9.1427f,
    val temperature: Float = 0f,
    val windspeed: Float = 0f,
    val winddirection: Int = 0,
    val weathercode: Int = 0,
    val seaLevelPressure: Number = 0f,
    val time: String = ""
)

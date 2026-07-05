package com.setpoint.data.model

data class DashboardUiState(
    val isLoading: Boolean = false,
    val dashboard: DashboardData? = null,
    val errorMessage: String? = null
)
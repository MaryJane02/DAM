package com.setpoint.data.repository

import com.setpoint.data.model.DashboardData
import com.setpoint.utils.DashboardCalculator

class DashboardRepository(
    private val matchRepository: MatchRepository = MatchRepository(),
    private val playerRepository: PlayerRepository = PlayerRepository(),
    private val teamRepository: TeamRepository = TeamRepository()
) {
    suspend fun getDashboardData(): DashboardData {
        val matches = matchRepository.getMatches()
        val players = playerRepository.getPlayers()
        val team = teamRepository.getTeam()

        return DashboardCalculator.calculateDashboard(
            matches = matches,
            players = players,
            teamName = team?.teamName ?: ""
        )
    }
}
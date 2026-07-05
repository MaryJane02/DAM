package com.setpoint.utils

import com.setpoint.data.model.DashboardData
import com.setpoint.data.model.Match
import com.setpoint.data.model.NextMatchData
import com.setpoint.data.model.Player
import java.time.LocalDateTime
import java.time.ZoneId

object DashboardCalculator {

    fun calculateDashboard(
        matches: List<Match>,
        players: List<Player>,
        teamName: String
    ): DashboardData {
        val finishedMatches = matches.filter { it.status == "finished" }

        val wins = finishedMatches.count {
            it.teamSetsWon > it.opponentSetsWon
        }

        val losses = finishedMatches.size - wins

        val winRate =
            if (finishedMatches.isEmpty()) 0
            else (wins * 100) / finishedMatches.size

        return DashboardData(
            teamName = teamName,
            matchesPlayed = finishedMatches.size,
            wins = wins,
            losses = losses,
            winRate = winRate,
            teamPlayers = players.size,
            nextMatch = getNextMatch(matches),
            recentMatches = getRecentMatches(finishedMatches)
        )
    }

    private fun getNextMatch(matches: List<Match>): NextMatchData? {
        val liveMatches = matches
            .filter { getMatchDisplayStatus(it) == "live" }
            .sortedBy { getMatchSortValue(it) }

        if (liveMatches.isNotEmpty()) {
            return NextMatchData(
                match = liveMatches.first(),
                isLive = true,
                showTrackButton = true
            )
        }

        val scheduledMatches = matches
            .filter { getMatchDisplayStatus(it) == "scheduled" }
            .sortedBy { getMatchSortValue(it) }

        return scheduledMatches.firstOrNull()?.let {
            NextMatchData(
                match = it,
                isLive = false,
                showTrackButton = false
            )
        }
    }

    private fun getRecentMatches(
        finishedMatches: List<Match>
    ): List<Match> {
        return finishedMatches
            .sortedByDescending { getMatchSortValue(it) }
            .take(3)
    }

    private fun getMatchSortValue(match: Match): Long {
        val dateParts = match.date.split("/")
        val timeParts = match.time.split(":")

        val day = dateParts.getOrNull(0)?.toIntOrNull() ?: return Long.MAX_VALUE
        val month = dateParts.getOrNull(1)?.toIntOrNull() ?: return Long.MAX_VALUE
        val year = dateParts.getOrNull(2)?.toIntOrNull() ?: return Long.MAX_VALUE

        val hour = timeParts.getOrNull(0)?.toIntOrNull() ?: 0
        val minute = timeParts.getOrNull(1)?.toIntOrNull() ?: 0

        return try {
            LocalDateTime
                .of(year, month, day, hour, minute)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        } catch (e: Exception) {
            Long.MAX_VALUE
        }
    }
}
package com.setpoint.utils

import com.setpoint.data.model.MatchPlayerStats

object MatchStatsCalculator {

    fun calculateMvpScore(stats: MatchPlayerStats): Int {
        return stats.points * 2 +
                stats.kills * 4 +
                stats.aces * 5 +
                stats.blocks * 4 -
                stats.errors * 3
    }

    fun getMvp(playerStats: List<MatchPlayerStats>): MatchPlayerStats? {
        return playerStats.maxByOrNull { stats ->
            calculateMvpScore(stats)
        }
    }

    fun calculateTotalPoints(playerStats: List<MatchPlayerStats>): Int {
        return playerStats.sumOf { it.points }
    }

    fun calculateTotalKills(playerStats: List<MatchPlayerStats>): Int {
        return playerStats.sumOf { it.kills }
    }

    fun calculateTotalAces(playerStats: List<MatchPlayerStats>): Int {
        return playerStats.sumOf { it.aces }
    }

    fun calculateTotalBlocks(playerStats: List<MatchPlayerStats>): Int {
        return playerStats.sumOf { it.blocks }
    }

    fun calculateTotalErrors(playerStats: List<MatchPlayerStats>): Int {
        return playerStats.sumOf { it.errors }
    }


}
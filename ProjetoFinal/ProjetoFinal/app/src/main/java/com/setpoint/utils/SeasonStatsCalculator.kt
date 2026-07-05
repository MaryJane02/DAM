package com.setpoint.utils

import com.setpoint.data.model.SeasonPlayerStats

object SeasonStatsCalculator {

    fun calculateSeasonMvpScore(stats: SeasonPlayerStats): Int {
        return stats.points * 2 +
                stats.kills * 4 +
                stats.aces * 5 +
                stats.blocks * 4 -
                stats.errors * 3
    }

    fun getSeasonMvp(players: List<SeasonPlayerStats>): SeasonPlayerStats? {
        return players.maxByOrNull { player ->
            calculateSeasonMvpScore(player)
        }
    }

    fun sortBySeasonMvpScore(players: List<SeasonPlayerStats>): List<SeasonPlayerStats> {
        return players.sortedByDescending { player ->
            calculateSeasonMvpScore(player)
        }
    }
}
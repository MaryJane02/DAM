package com.setpoint.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.setpoint.data.model.Match
import kotlinx.coroutines.tasks.await
import com.setpoint.data.model.MatchLineup
import com.setpoint.data.model.MatchPlayerStats
import com.setpoint.data.model.Player
import com.setpoint.data.model.SetResults
import com.setpoint.data.model.SeasonPlayerStats

class MatchRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private fun getTeamId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("User not authenticated.")
    }

    private fun matchesCollection() =
        db.collection("teams")
            .document(getTeamId())
            .collection("matches")

    suspend fun addMatch(match: Match) {

        val document = matchesCollection().document()

        val matchWithId = match.copy(
            id = document.id
        )

        document.set(matchWithId).await()
    }

    suspend fun getMatches(): List<Match> {

        val snapshot = matchesCollection()
            .get()
            .await()

        return snapshot.documents.mapNotNull {
            it.toObject(Match::class.java)
        }
    }

    suspend fun getMatchById(matchId: String): Match? {

        return matchesCollection()
            .document(matchId)
            .get()
            .await()
            .toObject(Match::class.java)
    }

    suspend fun updateMatch(match: Match) {

        matchesCollection()
            .document(match.id)
            .set(match)
            .await()
    }

    suspend fun deleteMatch(matchId: String) {

        matchesCollection()
            .document(matchId)
            .delete()
            .await()
    }

    suspend fun saveLineup(
        matchId: String,
        lineup: MatchLineup
    ) {
        matchesCollection()
            .document(matchId)
            .update("lineup", lineup)
            .await()
    }

    suspend fun getLineup(
        matchId: String
    ): MatchLineup? {
        val document = matchesCollection()
            .document(matchId)
            .get()
            .await()

        return document.get("lineup")?.let {
            document.toObject(Match::class.java)
        }?.lineup
    }

    suspend fun updateLiveScore(match: Match) {
        matchesCollection()
            .document(match.id)
            .set(match)
            .await()
    }

    private fun playerStatsCollection(matchId: String) =
        matchesCollection()
            .document(matchId)
            .collection("playerStats")

    suspend fun updatePlayerStats(
        matchId: String,
        stats: MatchPlayerStats
    ) {
        playerStatsCollection(matchId)
            .document(stats.playerId)
            .set(stats)
            .await()
    }

    suspend fun getPlayerStats(
        matchId: String
    ): List<MatchPlayerStats> {
        val snapshot = playerStatsCollection(matchId)
            .get()
            .await()

        return snapshot.documents.mapNotNull {
            it.toObject(MatchPlayerStats::class.java)
        }
    }

    suspend fun incrementPlayerStat(
        matchId: String,
        playerId: String,
        playerName: String,
        playerNumber: Int,
        field: String
    ) {
        val docRef = playerStatsCollection(matchId)
            .document(playerId)

        val snapshot = docRef.get().await()

        if (!snapshot.exists()) {
            val initialStats = MatchPlayerStats(
                playerId = playerId,
                playerName = playerName,
                playerNumber = playerNumber
            )

            docRef.set(initialStats).await()
        }

        docRef.update(field, FieldValue.increment(1)).await()
    }

    suspend fun registerMatchAction(
        match: Match,
        player: Player,
        action: String
    ): Match {
        var updatedMatch = when (action) {
            "ERROR" -> match.copy(opponentScore = match.opponentScore + 1)

            "POINT", "KILL", "ACE", "BLOCK" ->
                match.copy(teamScore = match.teamScore + 1)

            else -> match
        }

        when (action) {
            "POINT" -> {
                incrementPlayerStat(match.id, player.id, player.name, player.number, "points")
            }

            "ERROR" -> {
                incrementPlayerStat(match.id, player.id, player.name, player.number, "errors")
            }

            "KILL" -> {
                incrementPlayerStat(match.id, player.id, player.name, player.number, "kills")
                incrementPlayerStat(match.id, player.id, player.name, player.number, "points")
            }

            "ACE" -> {
                incrementPlayerStat(match.id, player.id, player.name, player.number, "aces")
                incrementPlayerStat(match.id, player.id, player.name, player.number, "points")
            }

            "BLOCK" -> {
                incrementPlayerStat(match.id, player.id, player.name, player.number, "blocks")
                incrementPlayerStat(match.id, player.id, player.name, player.number, "points")
            }
        }

        val target = if (updatedMatch.currentSet == 5) 15 else 25
        val maxScore = maxOf(updatedMatch.teamScore, updatedMatch.opponentScore)
        val difference = kotlin.math.abs(
            updatedMatch.teamScore - updatedMatch.opponentScore
        )

        if (maxScore >= target && difference >= 2) {
            val winner = if (updatedMatch.teamScore > updatedMatch.opponentScore) {
                "team"
            } else {
                "opponent"
            }

            val setResult = SetResults(
                setNumber = updatedMatch.currentSet,
                teamScore = updatedMatch.teamScore,
                opponentScore = updatedMatch.opponentScore,
                winner = winner
            )

            updatedMatch = if (winner == "team") {
                updatedMatch.copy(
                    teamSetsWon = updatedMatch.teamSetsWon + 1,
                    setResults = updatedMatch.setResults + setResult
                )
            } else {
                updatedMatch.copy(
                    opponentSetsWon = updatedMatch.opponentSetsWon + 1,
                    setResults = updatedMatch.setResults + setResult
                )
            }

            if (updatedMatch.teamSetsWon == 3 || updatedMatch.opponentSetsWon == 3) {
                val finishedMatch = updatedMatch.copy(status = "finished")
                updateLiveScore(finishedMatch)
                return finishedMatch
            }

            updatedMatch = updatedMatch.copy(
                currentSet = updatedMatch.currentSet + 1,
                teamScore = 0,
                opponentScore = 0
            )
        }

        updateLiveScore(updatedMatch)

        return updatedMatch
    }

    suspend fun getSeasonPlayerStats(): List<SeasonPlayerStats> {
        val finishedMatches = getMatches().filter { match ->
            match.status == "finished"
        }

        val statsMap = mutableMapOf<String, SeasonPlayerStats>()

        finishedMatches.forEach { match ->
            val matchStats = getPlayerStats(match.id)

            matchStats.forEach { stats ->
                val current = statsMap[stats.playerId]

                if (current == null) {
                    statsMap[stats.playerId] = SeasonPlayerStats(
                        playerId = stats.playerId,
                        playerName = stats.playerName,
                        playerNumber = stats.playerNumber,
                        points = stats.points,
                        kills = stats.kills,
                        aces = stats.aces,
                        blocks = stats.blocks,
                        errors = stats.errors
                    )
                } else {
                    statsMap[stats.playerId] = current.copy(
                        points = current.points + stats.points,
                        kills = current.kills + stats.kills,
                        aces = current.aces + stats.aces,
                        blocks = current.blocks + stats.blocks,
                        errors = current.errors + stats.errors
                    )
                }
            }
        }

        return statsMap.values.toList()
    }
}
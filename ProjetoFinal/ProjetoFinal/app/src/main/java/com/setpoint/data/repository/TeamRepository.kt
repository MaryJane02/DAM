package com.setpoint.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.setpoint.data.model.Team
import kotlinx.coroutines.tasks.await

class TeamRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private fun getTeamId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("User not authenticated.")
    }

    private fun teamDocument() =
        db.collection("teams")
            .document(getTeamId())

    suspend fun createTeam(
        teamName: String,
        email: String
    ) {
        val teamId = getTeamId()

        val team = Team(
            id = teamId,
            teamName = teamName,
            email = email
        )

        db.collection("teams")
            .document(teamId)
            .set(team)
            .await()
    }

    suspend fun getTeam(): Team? {
        return teamDocument()
            .get()
            .await()
            .toObject(Team::class.java)
    }

    suspend fun updateTeam(team: Team) {
        teamDocument()
            .set(team)
            .await()
    }
}
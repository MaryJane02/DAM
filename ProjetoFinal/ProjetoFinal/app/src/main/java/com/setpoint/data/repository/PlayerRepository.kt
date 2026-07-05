package com.setpoint.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.setpoint.data.model.Player
import kotlinx.coroutines.tasks.await

class PlayerRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    private fun getTeamId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("User not authenticated.")
    }

    private fun playersCollection() =
        db.collection("teams")
            .document(getTeamId())
            .collection("players")

    suspend fun addPlayer(player: Player) {
        val document = playersCollection().document()

        val playerWithId = player.copy(
            id = document.id
        )

        document.set(playerWithId).await()
    }

    suspend fun getPlayers(): List<Player> {
        val snapshot = playersCollection()
            .get()
            .await()

        return snapshot.documents.mapNotNull { document ->
            document.toObject(Player::class.java)
        }
    }

    suspend fun getPlayerById(playerId: String): Player? {
        val document = playersCollection()
            .document(playerId)
            .get()
            .await()

        return document.toObject(Player::class.java)
    }

    suspend fun deletePlayer(playerId: String) {
        playersCollection()
            .document(playerId)
            .delete()
            .await()
    }

    suspend fun updatePlayerPhoto(
        playerId: String,
        imageUri: Uri
    ): String {
        val teamId = getTeamId()

        val ref = storage.reference
            .child("teams/$teamId/players/$playerId/profile.jpg")

        ref.putFile(imageUri).await()

        val downloadUrl = ref.downloadUrl.await().toString()

        playersCollection()
            .document(playerId)
            .update("photoUrl", downloadUrl)
            .await()

        return downloadUrl
    }
}
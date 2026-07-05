package com.setpoint.data.repository

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import com.setpoint.data.model.Team
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    suspend fun login(
        email: String,
        password: String
    ) {
        val result = auth
            .signInWithEmailAndPassword(email, password)
            .await()

        val user = result.user

        user?.reload()?.await()

        if (user?.isEmailVerified != true) {
            auth.signOut()
            throw IllegalStateException(
                "Please verify your email before logging in."
            )
        }
    }

    suspend fun register(
        teamName: String,
        email: String,
        password: String
    ) {
        val result = auth
            .createUserWithEmailAndPassword(email, password)
            .await()

        val user = result.user
            ?: throw IllegalStateException("User was not created.")

        val team = Team(
            id = user.uid,
            teamName = teamName,
            email = email
        )

        db.collection("teams")
            .document(user.uid)
            .set(team)
            .await()

        user.sendEmailVerification().await()

        auth.signOut()
    }

    suspend fun loginWithGoogle(
        context: Context
    ) {
        val credentialManager = CredentialManager.create(context)

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(
                "605229567656-9m3d91i6c1l27ooi5ls5gmsnng7e98va.apps.googleusercontent.com"
            )
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val result = credentialManager.getCredential(
            context = context,
            request = request
        )

        val googleCredential = GoogleIdTokenCredential
            .createFrom(result.credential.data)

        val firebaseCredential = GoogleAuthProvider.getCredential(
            googleCredential.idToken,
            null
        )

        val authResult = auth
            .signInWithCredential(firebaseCredential)
            .await()

        val user = authResult.user
            ?: throw IllegalStateException("Google authentication failed.")

        val teamRef = db.collection("teams")
            .document(user.uid)

        val teamSnapshot = teamRef
            .get()
            .await()

        if (!teamSnapshot.exists()) {
            val team = Team(
                id = user.uid,
                teamName = user.displayName ?: "Set Point Team",
                email = user.email ?: ""
            )

            teamRef.set(team).await()
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    suspend fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }
}
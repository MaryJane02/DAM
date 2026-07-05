package com.setpoint.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.setpoint.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.content.Context
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.setpoint.data.model.AuthUiState

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()


    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        _uiState.value = _uiState.value.copy(
            isAuthenticated = repository.isUserLoggedIn(),
            isCheckingAuth = false
        )
    }

    fun logout() {
        repository.logout()

        _uiState.value = AuthUiState(
            isAuthenticated = false,
            isCheckingAuth = false
        )
    }

    fun login(
        email: String,
        password: String
    ) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState(
                errorMessage = "Please fill in all fields."
            )
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.value = AuthUiState(
                errorMessage = "Please enter a valid email address."
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)

            try {
                repository.login(email, password)

                _uiState.value = AuthUiState(
                    isLoading = false,
                    isAuthenticated = true
                )
            } catch (e: Exception) {
                _uiState.value = AuthUiState(
                    errorMessage = getAuthError(e)
                )
            }
        }
    }

    fun loginWithGoogle(context: Context) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)

            try {
                repository.loginWithGoogle(context)

                _uiState.value = AuthUiState(
                    isAuthenticated = true
                )
            } catch (e: Exception) {
                _uiState.value = AuthUiState(
                    errorMessage = getAuthError(e)
                )
            }
        }
    }

    fun register(
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        if (
            fullName.isBlank() ||
            email.isBlank() ||
            password.isBlank() ||
            confirmPassword.isBlank()
        ) {
            _uiState.value = AuthUiState(
                errorMessage = "Please fill in all fields."
            )
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.value = AuthUiState(
                errorMessage = "Please enter a valid email address."
            )
            return
        }

        if (password != confirmPassword) {
            _uiState.value = AuthUiState(
                errorMessage = "Passwords do not match."
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)

            try {
                repository.register(
                    teamName = fullName,
                    email = email,
                    password = password
                )

                _uiState.value = AuthUiState(
                    isLoading = false,
                    registrationSuccess = true
                )
            } catch (e: Exception) {
                _uiState.value = AuthUiState(
                    errorMessage = getAuthError(e)
                )
            }
        }
    }

    fun resetPassword(email: String) {
        if (email.isBlank()) {
            _uiState.value = AuthUiState(
                errorMessage = "Please enter your email address."
            )
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.value = AuthUiState(
                errorMessage = "Please enter a valid email address."
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)

            try {
                repository.resetPassword(email)

                _uiState.value = AuthUiState(
                    errorMessage = "Password reset email sent. Please check your inbox."
                )
            } catch (e: Exception) {
                _uiState.value = AuthUiState(
                    errorMessage = getAuthError(e)
                )
            }
        }
    }

    fun clearRegistrationSuccess() {
        _uiState.value = _uiState.value.copy(
            registrationSuccess = false
        )
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null
        )
    }

    private fun getAuthError(e: Exception): String {
        return when (e) {
            is FirebaseAuthUserCollisionException ->
                "This email is already registered."

            is FirebaseAuthWeakPasswordException ->
                "Password is too weak. Use at least 6 characters."

            is FirebaseAuthInvalidCredentialsException ->
                "Invalid email or password."

            is FirebaseAuthInvalidUserException ->
                "No account was found with this email."

            is FirebaseNetworkException ->
                "Network error. Please check your internet connection."

            is IllegalStateException ->
                e.message ?: "Authentication failed."

            else ->
                "Authentication failed. Please try again."
        }
    }
}
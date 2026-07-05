package com.setpoint.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.setpoint.data.model.Match
import com.setpoint.data.model.MatchLineup
import com.setpoint.data.model.MatchUiState
import com.setpoint.data.model.Player
import com.setpoint.data.repository.MatchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MatchViewModel(
    private val repository: MatchRepository = MatchRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(MatchUiState())
    val uiState: StateFlow<MatchUiState> = _uiState.asStateFlow()


    fun loadMatches() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val matches = repository.getMatches()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    matches = matches
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to load matches."
                )
            }
        }
    }

    fun addMatch(match: Match) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                repository.addMatch(match)
                loadMatches()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to add match."
                )
            }
        }
    }

    fun loadMatchById(matchId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val match = repository.getMatchById(matchId)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    selectedMatch = match
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to load match."
                )
            }
        }
    }

    fun updateMatch(match: Match) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                repository.updateMatch(match)
                loadMatches()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to update match."
                )
            }
        }
    }

    fun saveLineup(
        matchId: String,
        lineup: MatchLineup
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                repository.saveLineup(matchId, lineup)

                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to save lineup."
                )
            }
        }
    }

    fun updateLiveScore(match: Match) {
        viewModelScope.launch {
            try {
                repository.updateLiveScore(match)

                _uiState.value = _uiState.value.copy(
                    selectedMatch = match
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message ?: "Failed to update live score."
                )
            }
        }
    }

    fun incrementPlayerStat(
        matchId: String,
        playerId: String,
        playerName: String,
        playerNumber: Int,
        field: String
    ) {
        viewModelScope.launch {
            try {
                repository.incrementPlayerStat(
                    matchId = matchId,
                    playerId = playerId,
                    playerName = playerName,
                    playerNumber = playerNumber,
                    field = field
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message ?: "Failed to update player stats."
                )
            }
        }
    }

    fun loadPlayerStats(matchId: String) {
        viewModelScope.launch {
            try {
                val stats = repository.getPlayerStats(matchId)

                _uiState.value = _uiState.value.copy(
                    playerStats = stats
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message ?: "Failed to load player stats."
                )
            }
        }
    }

    fun registerMatchAction(
        match: Match,
        player: Player,
        action: String
    ) {
        viewModelScope.launch {
            try {
                val updatedMatch = repository.registerMatchAction(
                    match = match,
                    player = player,
                    action = action
                )

                _uiState.value = _uiState.value.copy(
                    selectedMatch = updatedMatch
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message ?: "Failed to register action."
                )
            }
        }
    }

    fun loadSeasonPlayerStats() {
        viewModelScope.launch {
            try {
                val stats = repository.getSeasonPlayerStats()

                _uiState.value = _uiState.value.copy(
                    seasonPlayerStats = stats
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message ?: "Failed to load season stats."
                )
            }
        }
    }

    fun deleteMatch(matchId: String) {
        viewModelScope.launch {
            try {
                repository.deleteMatch(matchId)
                loadMatches()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message ?: "Failed to delete match."
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
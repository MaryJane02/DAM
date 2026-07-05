package com.setpoint.ui.navigation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.setpoint.ui.screens.FirstScreen
import com.setpoint.ui.screens.LoginScreen
import com.setpoint.ui.screens.RegisterScreen
import com.setpoint.viewmodel.AuthViewModel
import androidx.compose.ui.platform.LocalContext
import com.setpoint.ui.screens.AddPlayerScreen
import com.setpoint.ui.screens.DashboardScreen
import com.setpoint.ui.screens.PlayerProfileScreen
import com.setpoint.ui.screens.TeamScreen
import com.setpoint.data.model.Match
import com.setpoint.data.model.Player
import com.setpoint.ui.screens.AccountCreatedScreen
import com.setpoint.ui.screens.AddMatchScreen
import com.setpoint.ui.screens.LiveMatchScreen
import com.setpoint.ui.screens.MatchStatsScreen
import com.setpoint.ui.screens.MatchesScreen
import com.setpoint.ui.screens.SeasonStatsScreen
import com.setpoint.ui.screens.StartingLineupScreen
import com.setpoint.ui.screens.SubstitutionScreen
import com.setpoint.ui.screens.TeamProfileScreen
import com.setpoint.utils.getMatchDisplayStatus
import com.setpoint.viewmodel.MatchViewModel
import com.setpoint.viewmodel.PlayerViewModel
import com.setpoint.viewmodel.TeamViewModel
import com.setpoint.viewmodel.DashboardViewModel
import kotlinx.coroutines.delay

sealed class Screen(val route: String) {
    data object Intro : Screen("intro")
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object AccountCreated: Screen("account_created")
    data object Dashboard : Screen("dashboard")
    data object Team: Screen("team")
    data object AddPlayer: Screen("add_player")
    data object PlayerProfile : Screen("player_profile/{playerId}") {
        fun createRoute(playerId: String) = "player_profile/$playerId"
    }
    data object Matches : Screen("matches?status={status}") {
        fun createRoute(status: String = "scheduled") = "matches?status=$status"
    }
    data object AddMatch : Screen("add_match")
    data object TeamProfile: Screen("team_profile")
    data object StartingLineup : Screen("starting_lineup/{matchId}"){
        fun createRoute(matchId: String) = "starting_lineup/$matchId"
    }
    data object LiveMatch : Screen("live_match/{matchId}"){
        fun createRoute(matchId: String) = "live_match/$matchId"
    }
    data object Substitution : Screen("substitution/{matchId}"){
        fun createRoute(matchId: String) = "substitution/$matchId"
    }
    data object MatchStats : Screen("match_stats/{matchId}") {
        fun createRoute(matchId: String) =
            "match_stats/$matchId"
    }
    data object SeasonStats : Screen("season_stats")
}

@Composable
fun AppNavGraph(
    authViewModel: AuthViewModel = viewModel(),
    playerViewModel: PlayerViewModel = viewModel(),
    matchViewModel: MatchViewModel = viewModel(),
    teamViewModel: TeamViewModel = viewModel(),
    dashboardViewModel: DashboardViewModel = viewModel()
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val authState by authViewModel.uiState.collectAsState()
    val playerState by playerViewModel.uiState.collectAsState()
    val teamState by teamViewModel.uiState.collectAsState()

    if (authState.errorMessage != null) {
        AlertDialog(
            onDismissRequest = {
                authViewModel.clearError()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        authViewModel.clearError()
                    }
                ) {
                    Text("OK")
                }
            },
            title = {
                Text("Authentication Error")
            },
            text = {
                Text(authState.errorMessage ?: "")
            }
        )
    }

    if (playerState.errorMessage != null) {
        AlertDialog(
            onDismissRequest = {
                playerViewModel.clearError()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        playerViewModel.clearError()
                    }
                ) {
                    Text("OK")
                }
            },
            title = {
                Text("Player Error")
            },
            text = {
                Text(playerState.errorMessage ?: "")
            }
        )
    }

    // Navegação após LOGIN
    LaunchedEffect(authState.isAuthenticated) {
        if (authState.isAuthenticated) {
            navController.navigate(Screen.Dashboard.route) {
                popUpTo(Screen.Intro.route) {
                    inclusive = true
                }
            }
        }
    }

    // Navegação após REGISTO
    LaunchedEffect(authState.registrationSuccess) {
        if (authState.registrationSuccess) {
            navController.navigate(Screen.AccountCreated.route) {
                popUpTo(Screen.Register.route) {
                    inclusive = true
                }
            }

            authViewModel.clearRegistrationSuccess()
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (authState.isAuthenticated) {
            Screen.Dashboard.route
        } else {
            Screen.Intro.route
        }
    ) {
        composable(Screen.Intro.route) {
            FirstScreen(
                onGetStartedClick = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginClick = { email, password ->
                    authViewModel.login(email, password)
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                },
                onForgotPasswordClick = { email ->
                    authViewModel.resetPassword(email)
                },
                onGoogleLoginClick = {
                    authViewModel.loginWithGoogle(context)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onSignUpClick = { fullName, email, password, confirmPassword ->
                    authViewModel.register(
                        fullName = fullName,
                        email = email,
                        password = password,
                        confirmPassword = confirmPassword
                    )
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                },
                onGoogleLoginClick = {
                    authViewModel.loginWithGoogle(context)
                }
            )
        }

        composable(Screen.AccountCreated.route) {
            AccountCreatedScreen(
                onGoToLoginClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.AccountCreated.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.Dashboard.route) {
            val dashboardState by dashboardViewModel.uiState.collectAsState()

            LaunchedEffect(Unit) {
                dashboardViewModel.loadDashboard()
            }

            DashboardScreen(
                dashboard = dashboardState.dashboard,
                isLoading = dashboardState.isLoading,
                onTeamClick = {
                    navController.navigate(Screen.Team.route)
                },
                onStatsClick = {
                    navController.navigate(Screen.SeasonStats.route)
                },
                onMatchesClick = {
                    navController.navigate(Screen.Matches.route)
                },
                onProfileClick = {
                    navController.navigate(Screen.TeamProfile.route)
                },
                onTrackMatchClick = { match ->
                    val hasLineup =
                        match.lineup.position1PlayerId.isNotBlank() &&
                                match.lineup.position2PlayerId.isNotBlank() &&
                                match.lineup.position3PlayerId.isNotBlank() &&
                                match.lineup.position4PlayerId.isNotBlank() &&
                                match.lineup.position5PlayerId.isNotBlank() &&
                                match.lineup.position6PlayerId.isNotBlank() &&
                                match.lineup.liberoPlayerId.isNotBlank()

                    if (hasLineup) {
                        navController.navigate(Screen.LiveMatch.createRoute(match.id))
                    } else {
                        navController.navigate(Screen.StartingLineup.createRoute(match.id))
                    }
                },
                onMatchStatsClick = { match ->
                    navController.navigate(Screen.MatchStats.createRoute(match.id))
                }
            )
        }

        composable(Screen.Team.route) {
            val playerState by playerViewModel.uiState.collectAsState()

            LaunchedEffect(Unit) {
                playerViewModel.loadPlayers()
            }
            TeamScreen(
                players = playerState.players,
                isLoading = playerState.isLoading,
                onPlayerClick = { player ->
                    navController.navigate(Screen.PlayerProfile.createRoute(player.id))
                },
                onDashboardClick = {
                    navController.navigate(Screen.Dashboard.route)
                },
                onStatsClick = {
                    navController.navigate(Screen.SeasonStats.route)
                },
                onMatchesClick = {
                    navController.navigate(Screen.Matches.route)
                },
                onAddPlayerClick = {
                    navController.navigate(Screen.AddPlayer.route)
                },
                onProfileClick = {
                    navController.navigate(Screen.TeamProfile.route)
                },
                onDeletePlayerClick = { player ->
                    playerViewModel.deletePlayer(player.id)
                }
            )
        }

        composable(Screen.AddPlayer.route) {
            val playerState by playerViewModel.uiState.collectAsState()

            LaunchedEffect(Unit) {
                playerViewModel.loadPlayers()
            }

            AddPlayerScreen(
                onSavePlayerClick = { form ->

                    val newNumber = form.number.toIntOrNull() ?: 0

                    val numberAlreadyExists = playerState.players.any { player ->
                        player.number == newNumber
                    }

                    if (numberAlreadyExists) {
                        playerViewModel.setError(
                            "There is already a player with number #$newNumber."
                        )
                        return@AddPlayerScreen
                    }

                    val player = Player(
                        name = form.name,
                        age = form.age.toIntOrNull() ?: 0,
                        height = form.height.toIntOrNull() ?: 0,
                        weight = form.weight.toDoubleOrNull() ?: 0.0,
                        position = form.position,
                        number = newNumber
                    )

                    playerViewModel.addPlayer(player)
                    navController.popBackStack()
                },
                onCancelClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.PlayerProfile.route
        ) { backStackEntry ->

            val playerId = backStackEntry.arguments?.getString("playerId") ?: ""

            val playerState by playerViewModel.uiState.collectAsState()
            val matchState by matchViewModel.uiState.collectAsState()

            LaunchedEffect(playerId) {
                playerViewModel.loadPlayerById(playerId)
                matchViewModel.loadSeasonPlayerStats()
            }

            val seasonStats = matchState.seasonPlayerStats.find {
                it.playerId == playerId
            }

            PlayerProfileScreen(
                player = playerState.selectedPlayer,
                seasonStats = seasonStats,
                isLoading = playerState.isLoading,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Matches.route) { backStackEntry ->

            val matchState by matchViewModel.uiState.collectAsState()
            val teamState by teamViewModel.uiState.collectAsState()

            val initialStatus =
                backStackEntry.arguments?.getString("status") ?: "scheduled"

            LaunchedEffect(Unit) {
                matchViewModel.loadMatches()
                teamViewModel.loadTeam()

                while (true) {
                    delay(60_000)
                    matchViewModel.loadMatches()
                }
            }

            MatchesScreen(
                initialStatus = initialStatus,
                teamName = teamState.team?.teamName ?: "Your Team",
                matches = matchState.matches,
                isLoading = matchState.isLoading,
                onAddMatchClick = {
                    navController.navigate(Screen.AddMatch.route)
                },
                onMatchClick = { match ->
                    when (getMatchDisplayStatus(match)) {
                        "finished" -> {
                            navController.navigate(
                                Screen.MatchStats.createRoute(match.id)
                            )
                        }
                    }
                },
                onDashboardClick = {
                    navController.navigate(Screen.Dashboard.route)
                },
                onStatsClick = {
                    navController.navigate(Screen.SeasonStats.route)
                },
                onTeamClick = {
                    navController.navigate(Screen.Team.route)
                },
                onProfileClick = {
                    navController.navigate(Screen.TeamProfile.route)
                },
                onStartMatchClick = { match ->
                    val hasLineup =
                        match.lineup.position1PlayerId.isNotBlank() &&
                                match.lineup.position2PlayerId.isNotBlank() &&
                                match.lineup.position3PlayerId.isNotBlank() &&
                                match.lineup.position4PlayerId.isNotBlank() &&
                                match.lineup.position5PlayerId.isNotBlank() &&
                                match.lineup.position6PlayerId.isNotBlank() &&
                                match.lineup.liberoPlayerId.isNotBlank()

                    if (hasLineup) {
                        navController.navigate(Screen.LiveMatch.createRoute(match.id))
                    } else {
                        navController.navigate(Screen.StartingLineup.createRoute(match.id))
                    }
                },
                onDeleteMatchClick = { match ->
                    matchViewModel.deleteMatch(match.id)
                }
            )
        }

        composable(Screen.AddMatch.route) {
            AddMatchScreen(
                onSaveMatchClick = { form ->
                    val match = Match(
                        opponent = form.opponent,
                        date = form.date,
                        time = form.time,
                        location = form.location,
                        status = ""
                    )

                    matchViewModel.addMatch(match)

                    navController.popBackStack()
                },
                onCancelClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.TeamProfile.route) {
            val teamState by teamViewModel.uiState.collectAsState()

            LaunchedEffect(Unit) {
                teamViewModel.loadTeam()
                playerViewModel.loadPlayers()
                matchViewModel.loadMatches()
            }

            TeamProfileScreen(
                team = teamState.team,
                isLoading = teamState.isLoading,
                onSaveClick = { updatedTeam ->
                    teamViewModel.updateTeam(updatedTeam)
                },
                onBackClick = {
                    navController.popBackStack()
                },
                onLogoutClick = {
                    authViewModel.logout()

                    navController.navigate(Screen.Intro.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(Screen.StartingLineup.route) { backStackEntry ->

            val matchId = backStackEntry.arguments?.getString("matchId") ?: ""
            val playerState by playerViewModel.uiState.collectAsState()

            LaunchedEffect(Unit) {
                playerViewModel.loadPlayers()
            }

            StartingLineupScreen(
                players = playerState.players,
                isLoading = playerState.isLoading,
                onBackClick = {
                    navController.popBackStack()
                },
                onContinueClick = { lineup ->
                    matchViewModel.saveLineup(matchId, lineup)

                    navController.navigate(Screen.LiveMatch.createRoute(matchId))
                }
            )
        }

        composable(Screen.LiveMatch.route) { backStackEntry ->

            val matchId = backStackEntry.arguments?.getString("matchId") ?: ""
            val matchState by matchViewModel.uiState.collectAsState()
            val playerState by playerViewModel.uiState.collectAsState()

            LaunchedEffect(matchId) {
                matchViewModel.loadMatchById(matchId)
                playerViewModel.loadPlayers()
                teamViewModel.loadTeam()
            }

            val match = matchState.selectedMatch
            val players = playerState.players

            val lineup = match?.lineup

            val courtPlayers = if (lineup != null) {
                listOfNotNull(
                    players.find { it.id == lineup.position1PlayerId },
                    players.find { it.id == lineup.position2PlayerId },
                    players.find { it.id == lineup.position3PlayerId },
                    players.find { it.id == lineup.position4PlayerId },
                    players.find { it.id == lineup.position5PlayerId },
                    players.find { it.id == lineup.position6PlayerId }
                )
            } else {
                emptyList()
            }

            val libero = lineup?.let {
                players.find { player -> player.id == it.liberoPlayerId }
            }

            LiveMatchScreen(
                match = match,
                courtPlayers = courtPlayers,
                libero = libero,
                teamName = teamState.team?.teamName ?: "Your Team",
                onActionClick = { currentMatch, player, action ->
                    matchViewModel.registerMatchAction(
                        match = currentMatch,
                        player = player,
                        action = action
                    )
                },
                onBackClick = {
                    navController.navigate(Screen.Matches.createRoute("live")) {
                        popUpTo(Screen.Matches.route) {
                            inclusive = true
                        }
                    }
                },
                onEndMatchClick = { finishedMatch ->
                    matchViewModel.updateLiveScore(finishedMatch)

                    navController.navigate(Screen.Matches.route) {
                        popUpTo(Screen.Matches.route) {
                            inclusive = true
                        }
                    }
                },
                onSubstitutionClick = {
                    navController.navigate(Screen.Substitution.createRoute(matchId))
                },
            )
        }
        composable(Screen.Substitution.route) { backStackEntry ->

            val matchId = backStackEntry.arguments?.getString("matchId") ?: ""
            val matchState by matchViewModel.uiState.collectAsState()
            val playerState by playerViewModel.uiState.collectAsState()

            LaunchedEffect(matchId) {
                matchViewModel.loadMatchById(matchId)
                playerViewModel.loadPlayers()
            }

            SubstitutionScreen(
                match = matchState.selectedMatch,
                players = playerState.players,
                onBackClick = {
                    navController.popBackStack()
                },
                onConfirmSubstitution = { updatedMatch ->
                    matchViewModel.updateLiveScore(updatedMatch)

                    navController.popBackStack()
                }
            )
        }

        composable(Screen.MatchStats.route) { backStackEntry ->

            val matchId =
                backStackEntry.arguments?.getString("matchId") ?: ""

            val matchState by matchViewModel.uiState.collectAsState()

            LaunchedEffect(matchId) {
                matchViewModel.loadMatchById(matchId)
                matchViewModel.loadPlayerStats(matchId)
            }

            MatchStatsScreen(
                match = matchState.selectedMatch,
                playerStats = matchState.playerStats,
                onBackClick = {
                    navController.navigate(Screen.Matches.createRoute("finished")) {
                        popUpTo(Screen.Matches.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.SeasonStats.route) {
            val matchState by matchViewModel.uiState.collectAsState()

            LaunchedEffect(Unit) {
                matchViewModel.loadMatches()
                matchViewModel.loadSeasonPlayerStats()
            }

            SeasonStatsScreen(
                finishedMatches = matchState.matches.filter { it.status == "finished" },
                seasonPlayerStats = matchState.seasonPlayerStats,
                isLoading = matchState.isLoading,
                onDashboardClick = {
                    navController.navigate(Screen.Dashboard.route)
                },
                onMatchesClick = {
                    navController.navigate(Screen.Matches.route)
                },
                onTeamClick = {
                    navController.navigate(Screen.Team.route)
                },
                onProfileClick = {
                    navController.navigate(Screen.TeamProfile.route)
                }
            )
        }
    }
}
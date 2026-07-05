package com.setpoint.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.setpoint.data.model.DashboardData
import com.setpoint.data.model.Match
import com.setpoint.ui.components.BottomBar
import com.setpoint.ui.components.TopBar
import com.setpoint.ui.theme.Blue1
import com.setpoint.ui.theme.Green1
import com.setpoint.ui.theme.Red3
import com.setpoint.ui.theme.White
import com.setpoint.utils.getCurrentSeasonText
import com.setpoint.utils.getInitials

@Composable
fun DashboardScreen(
    dashboard: DashboardData?,
    isLoading: Boolean = false,
    onTeamClick: () -> Unit = {},
    onStatsClick: () -> Unit = {},
    onMatchesClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onTrackMatchClick: (Match) -> Unit = {},
    onMatchStatsClick: (Match) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101418))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 18.dp)
                .padding(bottom = 90.dp)
        ) {
            TopBar(
                onProfileClick = onProfileClick
            )

            Spacer(modifier = Modifier.height(34.dp))

            Text(
                text = "Welcome back",
                color = White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = getCurrentSeasonText(),
                color = Green1,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (isLoading || dashboard == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Blue1)
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    DashboardStatCard(
                        title = "MATCHES\nPLAYED",
                        value = dashboard.matchesPlayed.toString(),
                        valueColor = Blue1,
                        modifier = Modifier.weight(1f)
                    )

                    DashboardStatCard(
                        title = "WIN\nRATE",
                        value = "${dashboard.winRate}%",
                        valueColor = Green1,
                        modifier = Modifier.weight(1f)
                    )

                    DashboardStatCard(
                        title = "TEAM\nPLAYERS",
                        value = dashboard.teamPlayers.toString(),
                        valueColor = Red3,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(42.dp))

                Text(
                    text = "Next Match",
                    color = Green1,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(18.dp))

                if (dashboard.nextMatch == null) {
                    EmptyDashboardCard("No upcoming matches.")
                } else {
                    NextMatchCard(
                        match = dashboard.nextMatch.match,
                        showTrackButton = dashboard.nextMatch.showTrackButton,
                        onTrackMatchClick = {
                            onTrackMatchClick(dashboard.nextMatch.match)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(42.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Past Matches",
                        color = Green1,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.weight(1f)
                    )

                    TextButton(onClick = onMatchesClick) {
                        Text(
                            text = "ARCHIVE",
                            color = Blue1,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                if (dashboard.recentMatches.isEmpty()) {
                    EmptyDashboardCard("No finished matches yet.")
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        dashboard.recentMatches.take(3).forEach { match ->
                            PastMatchCard(
                                teamInitials = getInitials(dashboard.teamName),
                                teamName = dashboard.teamName,
                                match = match,
                                onStatsClick = {
                                    onMatchStatsClick(match)
                                }
                            )
                        }
                    }
                }
            }
        }

        BottomBar(
            selectedItem = "DASHBOARD",
            onDashboardClick = {},
            onStatsClick = onStatsClick,
            onMatchesClick = onMatchesClick,
            onTeamClick = onTeamClick,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun DashboardStatCard(
    title: String,
    value: String,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(122.dp)
            .background(Color(0xFF181D20), RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFF2A3036), RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            color = Color(0xFFB6BCC6),
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = value,
            color = valueColor,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
        )
    }
}

@Composable
fun NextMatchCard(
    match: Match,
    showTrackButton: Boolean,
    onTrackMatchClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF181D20), RoundedCornerShape(10.dp))
            .border(1.dp, Color(0xFF2A3036), RoundedCornerShape(10.dp))
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DashboardMatchDateBox(match.date)

        Spacer(modifier = Modifier.width(18.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "VS. ${match.opponent.uppercase()}",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${match.time} • ${match.location}",
                color = Color(0xFFB6BCC6),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (showTrackButton) {
            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = onTrackMatchClick,
                modifier = Modifier
                    .width(108.dp)
                    .height(58.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue1,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "TRACK\nMATCH",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 13.sp
                )
            }
        }
    }
}

@Composable
fun PastMatchCard(
    teamInitials: String,
    teamName: String,
    match: Match,
    onStatsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF181D20), RoundedCornerShape(10.dp))
            .border(1.dp, Color(0xFF2A3036), RoundedCornerShape(10.dp))
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            TeamMatchLine(
                initials = getInitials(teamInitials),
                name = teamName,
                initialsColor = Blue1
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "${match.teamSetsWon} - ${match.opponentSetsWon}",
                color = White,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(start = 54.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            TeamMatchLine(
                initials = getInitials(match.opponent),
                name = match.opponent,
                initialsColor = Color(0xFFFFA3A3)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = formatShortMatchDate(match.date),
                color = Green1,
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            IconButton(
                onClick = onStatsClick,
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF20252B), RoundedCornerShape(8.dp))
            ) {
                Icon(
                    imageVector = Icons.Outlined.BarChart,
                    contentDescription = "Match stats",
                    tint = Blue1
                )
            }
        }
    }
}

@Composable
fun TeamMatchLine(
    initials: String,
    name: String,
    initialsColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TeamSmallBox(
            text = initials,
            textColor = initialsColor
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = name.uppercase(),
            color = White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
    }
}

@Composable
fun TeamSmallBox(
    text: String,
    textColor: Color
) {
    Box(
        modifier = Modifier
            .size(42.dp)
            .background(Color(0xFF20252B), RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFF333A42), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text.uppercase(),
            color = textColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun DashboardMatchDateBox(date: String) {
    val parts = date.split("/")
    val day = parts.getOrNull(0) ?: "--"
    val monthNumber = parts.getOrNull(1)?.toIntOrNull()

    val monthText = when (monthNumber) {
        1 -> "JAN"
        2 -> "FEB"
        3 -> "MAR"
        4 -> "APR"
        5 -> "MAY"
        6 -> "JUN"
        7 -> "JUL"
        8 -> "AUG"
        9 -> "SEP"
        10 -> "OCT"
        11 -> "NOV"
        12 -> "DEC"
        else -> "--"
    }

    Column(
        modifier = Modifier
            .width(74.dp)
            .height(90.dp)
            .background(Color(0xFF20252B), RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFF333A42), RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = monthText,
            color = Color(0xFFB6BCC6),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = day,
            color = Blue1,
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun EmptyDashboardCard(
    text: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(94.dp)
            .background(Color(0xFF181D20), RoundedCornerShape(10.dp))
            .border(1.dp, Color(0xFF2A3036), RoundedCornerShape(10.dp))
            .padding(18.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            color = Color(0xFF9CA3AF),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

fun formatShortMatchDate(date: String): String {
    val parts = date.split("/")
    val day = parts.getOrNull(0) ?: "--"
    val monthNumber = parts.getOrNull(1)?.toIntOrNull()

    val month = when (monthNumber) {
        1 -> "JAN"
        2 -> "FEB"
        3 -> "MAR"
        4 -> "APR"
        5 -> "MAY"
        6 -> "JUN"
        7 -> "JUL"
        8 -> "AUG"
        9 -> "SEP"
        10 -> "OCT"
        11 -> "NOV"
        12 -> "DEC"
        else -> "--"
    }

    return "$month $day"
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    MaterialTheme {
        DashboardScreen(
            dashboard = DashboardData(
                teamName = "NewxGen Feminina",
                matchesPlayed = 8,
                wins = 6,
                losses = 2,
                winRate = 75,
                teamPlayers = 12,
                nextMatch = null,
                recentMatches = listOf(
                    Match(
                        opponent = "Portugal Telecom",
                        date = "02/07/2026",
                        time = "20:30",
                        location = "Pavilhão Inatel",
                        status = "finished",
                        teamSetsWon = 3,
                        opponentSetsWon = 0
                    ),
                    Match(
                        opponent = "Famoes",
                        date = "28/06/2026",
                        time = "19:00",
                        location = "Pavilhão Municipal",
                        status = "finished",
                        teamSetsWon = 2,
                        opponentSetsWon = 3
                    )
                )
            )
        )
    }
}




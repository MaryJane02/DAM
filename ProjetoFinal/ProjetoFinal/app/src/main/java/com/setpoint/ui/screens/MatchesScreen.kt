package com.setpoint.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.setpoint.data.model.Match
import com.setpoint.ui.components.BottomBar
import com.setpoint.ui.components.TopBar
import com.setpoint.ui.theme.Blue1
import com.setpoint.ui.theme.Green1
import com.setpoint.ui.theme.White
import com.setpoint.utils.getInitials
import com.setpoint.utils.getMatchDisplayStatus

@Composable
fun MatchesScreen(
    matches: List<Match> = emptyList(),
    teamName: String = "",
    isLoading: Boolean = false,
    initialStatus: String = "scheduled",
    onAddMatchClick: () -> Unit = {},
    onMatchClick: (Match) -> Unit = {},
    onStartMatchClick: (Match) -> Unit = {},
    onDashboardClick: () -> Unit = {},
    onStatsClick: () -> Unit = {},
    onTeamClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onDeleteMatchClick: (Match) -> Unit = {}
) {
    var selectedStatus by rememberSaveable { mutableStateOf(initialStatus) }

    val filteredMatches = matches
        .filter { match ->
            getMatchDisplayStatus(match) == selectedStatus
        }
        .sortedBy { match ->
            getMatchSortValue(match)
        }

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
                showProfileButton = true,
                onProfileClick = onProfileClick
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Matches",
                color = White,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "${filteredMatches.size} ${selectedStatus.uppercase()} MATCHES",
                color = Green1,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = onAddMatchClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue1,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Schedule New Match",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatusFilterChip(
                    text = "Scheduled",
                    selected = selectedStatus == "scheduled",
                    onClick = { selectedStatus = "scheduled" },
                    modifier = Modifier.weight(1f)
                )

                StatusFilterChip(
                    text = "Live",
                    selected = selectedStatus == "live",
                    onClick = { selectedStatus = "live" },
                    modifier = Modifier.weight(1f)
                )

                StatusFilterChip(
                    text = "Finished",
                    selected = selectedStatus == "finished",
                    onClick = { selectedStatus = "finished" },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF181D20), RoundedCornerShape(8.dp))
                    .border(1.dp, Color(0xFF2A3036), RoundedCornerShape(8.dp))
            ) {
                when {
                    isLoading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(28.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Blue1)
                        }
                    }

                    filteredMatches.isEmpty() -> {
                        Text(
                            text = "No ${selectedStatus.lowercase()} matches found.",
                            color = Color(0xFF9CA3AF),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(18.dp)
                        )
                    }

                    else -> {
                        filteredMatches.forEachIndexed { index, match ->
                            MatchRow(
                                match = match,
                                teamName = teamName,
                                onClick = { onMatchClick(match) },
                                onStartMatchClick = { onStartMatchClick(match) },
                                onDeleteMatchClick = { onDeleteMatchClick(match) }
                            )

                            if (index < filteredMatches.lastIndex) {
                                HorizontalDivider(color = Color(0xFF2A3036))
                            }
                        }
                    }
                }
            }
        }

        BottomBar(
            selectedItem = "MATCHES",
            onDashboardClick = onDashboardClick,
            onStatsClick = onStatsClick,
            onMatchesClick = {},
            onTeamClick = onTeamClick,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun StatusFilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(42.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Blue1 else Color(0xFF181D20),
            contentColor = if (selected) Color.White else Color(0xFF9CA3AF)
        )
    ) {
        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MatchRow(
    teamName: String,
    match: Match,
    onClick: () -> Unit,
    onStartMatchClick: () -> Unit,
    onDeleteMatchClick: () -> Unit
) {
    val displayStatus = getMatchDisplayStatus(match)

    if (displayStatus == "finished") {
        FinishedMatchRow(
            teamName = teamName,
            match = match,
            onClick = onClick
        )
        return
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MatchDateBox(date = match.date)

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "VS. ${match.opponent.uppercase()}",
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = null,
                    tint = Color(0xFF9CA3AF),
                    modifier = Modifier.size(15.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = match.time,
                    color = Color(0xFFB6BCC6),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null,
                    tint = Color(0xFF9CA3AF),
                    modifier = Modifier.size(15.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = match.location,
                    color = Color(0xFFB6BCC6),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        when (displayStatus) {
            "live" -> {
                Button(
                    onClick = onStartMatchClick,
                    modifier = Modifier
                        .width(96.dp)
                        .height(44.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Blue1,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "TRACK\nMATCH",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 13.sp
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))
            }

            "scheduled" -> {
                IconButton(
                    onClick = onDeleteMatchClick,
                    modifier = Modifier
                        .size(42.dp)
                        .background(
                            Color(0xFF3A1F1F),
                            RoundedCornerShape(8.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Delete Match",
                        tint = Color(0xFFFFA3A3),
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun FinishedMatchRow(
    teamName: String,
    match: Match,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            MatchTeamLine(
                initials = getInitials(teamName),
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

            MatchTeamLine(
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
                color = Blue1,
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            IconButton(
                onClick = onClick,
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
fun MatchDateBox(date: String) {
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
            .width(58.dp)
            .height(68.dp)
            .background(Color(0xFF20252B), RoundedCornerShape(6.dp))
            .border(1.dp, Color(0xFF333A42), RoundedCornerShape(6.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = monthText,
            color = Color(0xFFB6BCC6),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = day,
            color = Blue1,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

fun getMatchSortValue(match: Match): Long {
    val dateParts = match.date.split("/")
    val timeParts = match.time.split(":")

    val day = dateParts.getOrNull(0)?.toIntOrNull() ?: return Long.MAX_VALUE
    val month = dateParts.getOrNull(1)?.toIntOrNull() ?: return Long.MAX_VALUE
    val year = dateParts.getOrNull(2)?.toIntOrNull() ?: return Long.MAX_VALUE

    val hour = timeParts.getOrNull(0)?.toIntOrNull() ?: 0
    val minute = timeParts.getOrNull(1)?.toIntOrNull() ?: 0

    return try {
        java.time.LocalDateTime
            .of(year, month, day, hour, minute)
            .atZone(java.time.ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    } catch (e: Exception) {
        Long.MAX_VALUE
    }
}

@Composable
fun MatchTeamLine(
    initials: String,
    name: String,
    initialsColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        MatchTeamSmallBox(
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
fun MatchTeamSmallBox(
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

@Preview(showBackground = true)
@Composable
fun MatchesScreenPreview() {
    MaterialTheme {
        MatchesScreen(
            matches = listOf(
                Match(
                    id = "1",
                    opponent = "Portugal Telecom",
                    date = "12/07/2026",
                    time = "20:30",
                    location = "Pavilhão Inatel",
                    status = "scheduled"
                )
            )
        )
    }
}


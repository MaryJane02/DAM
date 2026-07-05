package com.setpoint.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.setpoint.data.model.Match
import com.setpoint.data.model.MatchPlayerStats
import com.setpoint.ui.components.TopBar
import com.setpoint.ui.theme.Blue1
import com.setpoint.ui.theme.Green1
import com.setpoint.ui.theme.White
import com.setpoint.utils.MatchStatsCalculator

@Composable
fun MatchStatsScreen(
    match: Match?,
    playerStats: List<MatchPlayerStats> = emptyList(),
    onBackClick: () -> Unit = {}
) {
    if (match == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF101418)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Blue1)
        }
        return
    }

    val mvp = MatchStatsCalculator.getMvp(playerStats)
    val totalPoints = MatchStatsCalculator.calculateTotalPoints(playerStats)
    val totalKills = MatchStatsCalculator.calculateTotalKills(playerStats)
    val totalAces = MatchStatsCalculator.calculateTotalAces(playerStats)
    val totalBlocks = MatchStatsCalculator.calculateTotalBlocks(playerStats)
    val totalErrors = MatchStatsCalculator.calculateTotalErrors(playerStats)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101418))
            .padding(horizontal = 24.dp, vertical = 18.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            TopBar(showProfileButton = false)

            Spacer(modifier = Modifier.height(20.dp))

            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Match Statistics",
                color = White,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "VS. ${match.opponent.uppercase()}",
                color = Green1,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "📅 ${match.date}",
                color = Color(0xFFB6BCC6),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "🕗 ${match.time}",
                color = Color(0xFFB6BCC6),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "📍 ${match.location}",
                color = Color(0xFFB6BCC6),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(28.dp))

            FinalResultCard(match)

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Set Results",
                color = White,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(14.dp))

            SetResultsCard(match)

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "MVP",
                color = White,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(14.dp))

            TopPerformerCard(mvp)

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Team Actions",
                color = White,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MatchStatCard("POINTS", totalPoints.toString(), Blue1, Modifier.weight(1f))
                MatchStatCard("KILLS", totalKills.toString(), Green1, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MatchStatCard("ACES", totalAces.toString(), Blue1, Modifier.weight(1f))
                MatchStatCard("BLOCKS", totalBlocks.toString(), Green1, Modifier.weight(1f))
                MatchStatCard("ERRORS", totalErrors.toString(), Color(0xFFFFA3A3), Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Player Stats",
                color = White,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(14.dp))

            PlayerStatsList(playerStats)

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun FinalResultCard(match: Match) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF181D20), RoundedCornerShape(10.dp))
            .border(1.dp, Color(0xFF2A3036), RoundedCornerShape(10.dp))
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "FINAL RESULT",
            color = Green1,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "${match.teamSetsWon} - ${match.opponentSetsWon}",
            color = White,
            fontSize = 54.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "SETS WON",
            color = Color(0xFF9CA3AF),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SetResultsCard(match: Match) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF181D20), RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFF2A3036), RoundedCornerShape(8.dp))
            .padding(18.dp)
    ) {
        if (match.setResults.isEmpty()) {
            Text(
                text = "No set results available.",
                color = Color(0xFF9CA3AF),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        } else {
            match.setResults.forEachIndexed { index, set ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SET ${set.setNumber}",
                        color = Color(0xFF9CA3AF),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "${set.teamScore} - ${set.opponentScore}",
                        color = White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = if (set.winner == "team") "W" else "L",
                        color = if (set.winner == "team") Green1 else Color(0xFFFFA3A3),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                if (index < match.setResults.lastIndex) {
                    HorizontalDivider(color = Color(0xFF2A3036))
                }
            }
        }
    }
}

@Composable
fun TopPerformerCard(
    player: MatchPlayerStats?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF181D20), RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFF2A3036), RoundedCornerShape(8.dp))
            .padding(18.dp)
    ) {
        if (player == null) {
            Text(
                text = "No player stats available.",
                color = Color(0xFF9CA3AF),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        } else {
            Text(
                text = "#${player.playerNumber} ${player.playerName.uppercase()}",
                color = White,
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${player.points} POINTS • ${player.kills} KILLS • ${player.aces} ACES • ${player.blocks} BLOCKS",
                color = Green1,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun MatchStatCard(
    title: String,
    value: String,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(92.dp)
            .background(Color(0xFF181D20), RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFF2A3036), RoundedCornerShape(8.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            color = Color(0xFF9CA3AF),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = value,
            color = valueColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun PlayerStatsList(
    playerStats: List<MatchPlayerStats>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF181D20), RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFF2A3036), RoundedCornerShape(8.dp))
    ) {
        if (playerStats.isEmpty()) {
            Text(
                text = "No player stats available.",
                color = Color(0xFF9CA3AF),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(18.dp)
            )
        } else {
            playerStats
                .sortedByDescending { it.points }
                .forEachIndexed { index, player ->
                    PlayerMatchStatsRow(player)

                    if (index < playerStats.lastIndex) {
                        HorizontalDivider(color = Color(0xFF2A3036))
                    }
                }
        }
    }
}

@Composable
fun PlayerMatchStatsRow(
    player: MatchPlayerStats
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "#${player.playerNumber} ${player.playerName.uppercase()}",
                color = White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "${player.points} pts • ${player.kills} kills • ${player.aces} aces • ${player.blocks} blocks • ${player.errors} errors",
                color = Color(0xFFB6BCC6),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
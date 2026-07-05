package com.setpoint.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.setpoint.data.model.SeasonPlayerStats
import com.setpoint.ui.components.BottomBar
import com.setpoint.ui.components.TopBar
import com.setpoint.ui.theme.Blue1
import com.setpoint.ui.theme.Green1
import com.setpoint.ui.theme.White
import com.setpoint.utils.MatchStatsCalculator
import com.setpoint.utils.SeasonStatsCalculator

@Composable
fun SeasonStatsScreen(
    finishedMatches: List<Match> = emptyList(),
    seasonPlayerStats: List<SeasonPlayerStats> = emptyList(),
    isLoading: Boolean = false,
    onDashboardClick: () -> Unit = {},
    onMatchesClick: () -> Unit = {},
    onTeamClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val matchesPlayed = finishedMatches.size
    val wins = finishedMatches.count { it.teamSetsWon > it.opponentSetsWon }
    val losses = matchesPlayed - wins
    val winRate = if (matchesPlayed == 0) 0 else (wins * 100) / matchesPlayed

    val totalPoints = seasonPlayerStats.sumOf { it.points }
    val totalKills = seasonPlayerStats.sumOf { it.kills }
    val totalAces = seasonPlayerStats.sumOf { it.aces }
    val totalBlocks = seasonPlayerStats.sumOf { it.blocks }
    val totalErrors = seasonPlayerStats.sumOf { it.errors }

    val topScorer = seasonPlayerStats.maxByOrNull { it.points }
    val topKiller = seasonPlayerStats.maxByOrNull { it.kills }
    val topServer = seasonPlayerStats.maxByOrNull { it.aces }
    val topBlocker = seasonPlayerStats.maxByOrNull { it.blocks }

    val seasonMvp = SeasonStatsCalculator.getSeasonMvp(seasonPlayerStats)

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

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Season Stats",
                color = White,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "TEAM PERFORMANCE OVERVIEW",
                color = Green1,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            if (isLoading) {
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
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SeasonStatCard("MATCHES", matchesPlayed.toString(), Blue1, Modifier.weight(1f))
                    SeasonStatCard("WINS", wins.toString(), Green1, Modifier.weight(1f))
                    SeasonStatCard("LOSSES", losses.toString(), Color(0xFFFFA3A3), Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(12.dp))

                SeasonWideCard(
                    title = "WIN RATE",
                    value = "$winRate%",
                    subtitle = "$wins wins in $matchesPlayed finished matches"
                )

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "Season Leaders",
                    color = White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(14.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF181D20), RoundedCornerShape(8.dp))
                        .border(1.dp, Color(0xFF2A3036), RoundedCornerShape(8.dp))
                ) {
                    SeasonLeaderRow(
                        "MVP",
                        seasonMvp,
                        seasonMvp?.let {
                            val mvpScore =
                                it.points * 2 +
                                        it.kills * 4 +
                                        it.aces * 5 +
                                        it.blocks * 4 -
                                        it.errors * 3

                            "$mvpScore MVP Score"
                        } ?: "0 MVP Score"
                    )

                    HorizontalDivider(color = Color(0xFF2A3036))

                    SeasonLeaderRow("TOP SCORER", topScorer, "${topScorer?.points ?: 0} pts")

                    HorizontalDivider(color = Color(0xFF2A3036))

                    SeasonLeaderRow("TOP ATTACKER", topKiller, "${topKiller?.kills ?: 0} kills")

                    HorizontalDivider(color = Color(0xFF2A3036))

                    SeasonLeaderRow("TOP SERVER", topServer, "${topServer?.aces ?: 0} aces")

                    HorizontalDivider(color = Color(0xFF2A3036))

                    SeasonLeaderRow("TOP BLOCKER", topBlocker, "${topBlocker?.blocks ?: 0} blocks")
                }

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "Team Totals",
                    color = White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SeasonStatCard("POINTS", totalPoints.toString(), Blue1, Modifier.weight(1f))
                    SeasonStatCard("KILLS", totalKills.toString(), Green1, Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SeasonStatCard("ACES", totalAces.toString(), Blue1, Modifier.weight(1f))
                    SeasonStatCard("BLOCKS", totalBlocks.toString(), Green1, Modifier.weight(1f))
                    SeasonStatCard("ERRORS", totalErrors.toString(), Color(0xFFFFA3A3), Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "Player Ranking",
                    color = White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(14.dp))

                SeasonPlayerRanking(seasonPlayerStats)
            }
        }

        BottomBar(
            selectedItem = "STATS",
            onDashboardClick = onDashboardClick,
            onStatsClick = {},
            onMatchesClick = onMatchesClick,
            onTeamClick = onTeamClick,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun SeasonStatCard(
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
fun SeasonWideCard(
    title: String,
    value: String,
    subtitle: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF181D20), RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFF2A3036), RoundedCornerShape(8.dp))
            .padding(18.dp)
    ) {
        Text(
            text = title,
            color = Green1,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = value,
            color = White,
            fontSize = 38.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = subtitle,
            color = Color(0xFF9CA3AF),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SeasonLeaderRow(
    title: String,
    player: SeasonPlayerStats?,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = Green1,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = if (player == null) "No data available"
                else "#${player.playerNumber} ${player.playerName.uppercase()}",
                color = White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = value,
            color = Blue1,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun SeasonPlayerRanking(
    stats: List<SeasonPlayerStats>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF181D20), RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFF2A3036), RoundedCornerShape(8.dp))
    ) {
        if (stats.isEmpty()) {
            Text(
                text = "No season stats available.",
                color = Color(0xFF9CA3AF),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(18.dp)
            )
        } else {
            SeasonStatsCalculator
                .sortBySeasonMvpScore(stats)
                .forEachIndexed { index, player ->

                    val mvpScore =
                        SeasonStatsCalculator.calculateSeasonMvpScore(player)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${index + 1}",
                            color = Blue1,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.width(34.dp)
                        )

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "#${player.playerNumber} ${player.playerName.uppercase()}",
                                color = White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                text = "MVP Score $mvpScore • ${player.points} pts • ${player.kills} kills • ${player.aces} aces • ${player.blocks} blocks • ${player.errors} errors",
                                color = Color(0xFFB6BCC6),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    if (index < stats.lastIndex) {
                        HorizontalDivider(color = Color(0xFF2A3036))
                    }
                }
        }
    }
}

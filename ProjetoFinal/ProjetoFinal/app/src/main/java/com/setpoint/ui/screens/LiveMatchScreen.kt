package com.setpoint.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.setpoint.data.model.Match
import com.setpoint.data.model.Player
import com.setpoint.ui.components.TopBar
import com.setpoint.ui.theme.Blue1
import com.setpoint.ui.theme.Green1
import com.setpoint.ui.theme.White
import com.setpoint.utils.getPlayerInitials
import kotlin.math.abs

@Composable
fun LiveMatchScreen(
    match: Match?,
    courtPlayers: List<Player> = emptyList(),
    libero: Player? = null,
    teamName: String,
    onActionClick: (Match, Player, String) -> Unit = { _, _, _ -> },
    onBackClick: () -> Unit = {},
    onEndMatchClick: (Match) -> Unit = {},
    onSubstitutionClick: () -> Unit = {}
) {
    var selectedPlayer by remember { mutableStateOf<Player?>(null) }

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

    fun processAction(action: String) {
        val player = selectedPlayer ?: return

        onActionClick(
            match,
            player,
            action
        )
    }

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
                text = "Live Match",
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

            Spacer(modifier = Modifier.height(26.dp))

            ScoreBoard(
                currentSet = match.currentSet,
                teamName = teamName,
                opponentName = match.opponent,
                teamScore = match.teamScore,
                opponentScore = match.opponentScore,
                teamSetsWon = match.teamSetsWon,
                opponentSetsWon = match.opponentSetsWon
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Court Formation",
                color = White,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(14.dp))

            LiveCourtSection(
                courtPlayers = courtPlayers,
                libero = libero,
                selectedPlayer = selectedPlayer,
                onPlayerClick = { selectedPlayer = it }
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = onSubstitutionClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF20252B),
                    contentColor = Green1
                )
            ) {
                Text(
                    text = "Make Substitution",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = selectedPlayer?.let {
                    "Selected: #${it.number} ${it.name}"
                } ?: "Select a player before registering an action",
                color = Color(0xFFB6BCC6),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(14.dp))

            ActionButtons(
                enabled = selectedPlayer != null,
                onActionClick = { action ->
                    processAction(action)
                }
            )

            Spacer(modifier = Modifier.height(26.dp))

            OutlinedButton(
                onClick = {
                    onEndMatchClick(
                        match.copy(status = "finished")
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFFFA3A3)
                )
            ) {
                Text(
                    text = "End Match",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun ScoreBoard(
    currentSet: Int,
    teamName: String,
    opponentName: String,
    teamScore: Int,
    opponentScore: Int,
    teamSetsWon: Int,
    opponentSetsWon: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF181D20), RoundedCornerShape(10.dp))
            .border(1.dp, Color(0xFF2A3036), RoundedCornerShape(10.dp))
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "SET $currentSet",
            color = Green1,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScoreTeamColumn(
                teamName = teamName,
                score = teamScore,
                setsWon = teamSetsWon,
                scoreColor = Blue1,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = ":",
                color = White,
                fontSize = 46.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(horizontal = 10.dp)
            )

            ScoreTeamColumn(
                teamName = opponentName,
                score = opponentScore,
                setsWon = opponentSetsWon,
                scoreColor = Color(0xFFFFA3A3),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ScoreTeamColumn(
    teamName: String,
    score: Int,
    setsWon: Int,
    scoreColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = teamName.uppercase(),
            color = Color(0xFFB6BCC6),
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = score.toString(),
            color = scoreColor,
            fontSize = 54.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "$setsWon SETS",
            color = Color(0xFF9CA3AF),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LiveCourtSection(
    courtPlayers: List<Player>,
    libero: Player?,
    selectedPlayer: Player?,
    onPlayerClick: (Player) -> Unit
) {
    val p1 = courtPlayers.getOrNull(0)
    val p2 = courtPlayers.getOrNull(1)
    val p3 = courtPlayers.getOrNull(2)
    val p4 = courtPlayers.getOrNull(3)
    val p5 = courtPlayers.getOrNull(4)
    val p6 = courtPlayers.getOrNull(5)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF181D20), RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFF2A3036), RoundedCornerShape(12.dp))
            .padding(18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            LivePlayerButton("P4", p4, selectedPlayer, Modifier.weight(1f), onPlayerClick)
            LivePlayerButton("P3", p3, selectedPlayer, Modifier.weight(1f), onPlayerClick)
            LivePlayerButton("P2", p2, selectedPlayer, Modifier.weight(1f), onPlayerClick)
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            LivePlayerButton("P5", p5, selectedPlayer, Modifier.weight(1f), onPlayerClick)
            LivePlayerButton("P6", p6, selectedPlayer, Modifier.weight(1f), onPlayerClick)
            LivePlayerButton("P1", p1, selectedPlayer, Modifier.weight(1f), onPlayerClick)
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            LivePlayerButton(
                label = "LIBERO",
                player = libero,
                selectedPlayer = selectedPlayer,
                modifier = Modifier.width(105.dp),
                onPlayerClick = onPlayerClick
            )
        }
    }
}

@Composable
fun LivePlayerButton(
    label: String,
    player: Player?,
    selectedPlayer: Player?,
    modifier: Modifier = Modifier,
    onPlayerClick: (Player) -> Unit
) {
    val isSelected = player != null && selectedPlayer?.id == player.id

    Column(
        modifier = modifier
            .height(100.dp)
            .background(
                if (isSelected) Blue1.copy(alpha = 0.25f) else Color(0xFF20252B),
                RoundedCornerShape(10.dp)
            )
            .border(
                1.dp,
                if (isSelected) Blue1 else Color(0xFF333A42),
                RoundedCornerShape(10.dp)
            )
            .clickable(enabled = player != null) {
                player?.let { onPlayerClick(it) }
            }
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = label,
            color = if (isSelected) Blue1 else Color(0xFF9CA3AF),
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (player == null) {
            Text(
                text = "--",
                color = Color(0xFF6B7280),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        } else {
            Text(
                text = "#${player.number}",
                color = White,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = getPlayerInitials(player.name),
                color = Blue1,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun ActionButtons(
    enabled: Boolean,
    onActionClick: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MatchActionButton("Point", enabled, Modifier.weight(1f)) {
                onActionClick("POINT")
            }

            MatchActionButton("Error", enabled, Modifier.weight(1f)) {
                onActionClick("ERROR")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MatchActionButton("Kill", enabled, Modifier.weight(1f)) {
                onActionClick("KILL")
            }

            MatchActionButton("Ace", enabled, Modifier.weight(1f)) {
                onActionClick("ACE")
            }

            MatchActionButton("Block", enabled, Modifier.weight(1f)) {
                onActionClick("BLOCK")
            }
        }
    }
}

@Composable
fun MatchActionButton(
    text: String,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue1,
            disabledContainerColor = Color(0xFF20252B),
            contentColor = Color.White,
            disabledContentColor = Color(0xFF6B7280)
        )
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}
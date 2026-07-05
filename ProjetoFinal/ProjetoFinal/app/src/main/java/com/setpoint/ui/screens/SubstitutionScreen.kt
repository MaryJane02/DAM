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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.setpoint.data.model.Match
import com.setpoint.data.model.MatchLineup
import com.setpoint.data.model.Player
import com.setpoint.ui.components.TopBar
import com.setpoint.ui.theme.Blue1
import com.setpoint.ui.theme.Green1
import com.setpoint.ui.theme.White
import com.setpoint.utils.getPlayerInitials

@Composable
fun SubstitutionScreen(
    match: Match?,
    players: List<Player> = emptyList(),
    onBackClick: () -> Unit = {},
    onConfirmSubstitution: (Match) -> Unit = {}
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

    var selectedCourtPosition by remember { mutableStateOf<String?>(null) }
    var selectedBenchPlayer by remember { mutableStateOf<Player?>(null) }

    val lineup = match.lineup

    val courtPlayerIds = listOf(
        lineup.position1PlayerId,
        lineup.position2PlayerId,
        lineup.position3PlayerId,
        lineup.position4PlayerId,
        lineup.position5PlayerId,
        lineup.position6PlayerId,
        lineup.liberoPlayerId
    ).filter { it.isNotBlank() }

    val benchPlayers = players.filter { player ->
        player.id !in courtPlayerIds
    }

    val canConfirm = selectedCourtPosition != null && selectedBenchPlayer != null

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
                text = "Make Substitution",
                color = White,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Choose a court player and a bench player.",
                color = Color(0xFFB6BCC6),
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Court Players",
                color = White,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(14.dp))

            SubstitutionCourtSection(
                match = match,
                players = players,
                selectedCourtPosition = selectedCourtPosition,
                onCourtPositionClick = { selectedCourtPosition = it }
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Bench Players",
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
                if (benchPlayers.isEmpty()) {
                    Text(
                        text = "No bench players available.",
                        color = Color(0xFF9CA3AF),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(18.dp)
                    )
                } else {
                    benchPlayers.forEachIndexed { index, player ->
                        BenchPlayerRow(
                            player = player,
                            selected = selectedBenchPlayer?.id == player.id,
                            onClick = { selectedBenchPlayer = player }
                        )

                        if (index < benchPlayers.lastIndex) {
                            HorizontalDivider(color = Color(0xFF2A3036))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    val position = selectedCourtPosition ?: return@Button
                    val benchPlayer = selectedBenchPlayer ?: return@Button

                    val updatedLineup = replacePlayerInLineup(
                        lineup = match.lineup,
                        position = position,
                        newPlayerId = benchPlayer.id
                    )

                    onConfirmSubstitution(
                        match.copy(lineup = updatedLineup)
                    )
                },
                enabled = canConfirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue1,
                    disabledContainerColor = Color(0xB4297DFF),
                    contentColor = Color.White,
                    disabledContentColor = Color.White.copy(alpha = 0.75f)
                )
            ) {
                Text(
                    text = "Confirm Substitution  →",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun SubstitutionCourtSection(
    match: Match,
    players: List<Player>,
    selectedCourtPosition: String?,
    onCourtPositionClick: (String) -> Unit
) {
    val lineup = match.lineup

    val p1 = players.find { it.id == lineup.position1PlayerId }
    val p2 = players.find { it.id == lineup.position2PlayerId }
    val p3 = players.find { it.id == lineup.position3PlayerId }
    val p4 = players.find { it.id == lineup.position4PlayerId }
    val p5 = players.find { it.id == lineup.position5PlayerId }
    val p6 = players.find { it.id == lineup.position6PlayerId }
    val libero = players.find { it.id == lineup.liberoPlayerId }

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
            SubstitutionPlayerButton("P4", p4, selectedCourtPosition == "P4", Modifier.weight(1f)) {
                onCourtPositionClick("P4")
            }

            SubstitutionPlayerButton("P3", p3, selectedCourtPosition == "P3", Modifier.weight(1f)) {
                onCourtPositionClick("P3")
            }

            SubstitutionPlayerButton("P2", p2, selectedCourtPosition == "P2", Modifier.weight(1f)) {
                onCourtPositionClick("P2")
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            SubstitutionPlayerButton("P5", p5, selectedCourtPosition == "P5", Modifier.weight(1f)) {
                onCourtPositionClick("P5")
            }

            SubstitutionPlayerButton("P6", p6, selectedCourtPosition == "P6", Modifier.weight(1f)) {
                onCourtPositionClick("P6")
            }

            SubstitutionPlayerButton("P1", p1, selectedCourtPosition == "P1", Modifier.weight(1f)) {
                onCourtPositionClick("P1")
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            SubstitutionPlayerButton(
                label = "LIBERO",
                player = libero,
                selected = selectedCourtPosition == "L",
                modifier = Modifier.width(120.dp)
            ) {
                onCourtPositionClick("L")
            }
        }
    }
}

@Composable
fun SubstitutionPlayerButton(
    label: String,
    player: Player?,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .height(92.dp)
            .background(
                if (selected) Blue1.copy(alpha = 0.25f) else Color(0xFF20252B),
                RoundedCornerShape(10.dp)
            )
            .border(
                1.dp,
                if (selected) Blue1 else Color(0xFF333A42),
                RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = label,
            color = if (selected) Blue1 else Color(0xFF9CA3AF),
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
fun BenchPlayerRow(
    player: Player,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(
                if (selected) Blue1.copy(alpha = 0.12f) else Color.Transparent
            )
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Blue1.copy(alpha = 0.15f), RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = getPlayerInitials(player.name),
                color = Blue1,
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = player.name.uppercase(),
                color = White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "#${player.number} • ${player.position.uppercase()}",
                color = Color(0xFFB6BCC6),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

fun replacePlayerInLineup(
    lineup: MatchLineup,
    position: String,
    newPlayerId: String
): MatchLineup {
    return when (position) {
        "P1" -> lineup.copy(position1PlayerId = newPlayerId)
        "P2" -> lineup.copy(position2PlayerId = newPlayerId)
        "P3" -> lineup.copy(position3PlayerId = newPlayerId)
        "P4" -> lineup.copy(position4PlayerId = newPlayerId)
        "P5" -> lineup.copy(position5PlayerId = newPlayerId)
        "P6" -> lineup.copy(position6PlayerId = newPlayerId)
        "L" -> lineup.copy(liberoPlayerId = newPlayerId)
        else -> lineup
    }
}
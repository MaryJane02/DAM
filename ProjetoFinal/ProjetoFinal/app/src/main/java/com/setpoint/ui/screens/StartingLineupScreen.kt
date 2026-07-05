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
import com.setpoint.data.model.MatchLineup
import com.setpoint.data.model.Player
import com.setpoint.ui.components.TopBar
import com.setpoint.ui.theme.Blue1
import com.setpoint.ui.theme.Green1
import com.setpoint.ui.theme.White
import com.setpoint.utils.getPlayerInitials

@Composable
fun StartingLineupScreen(
    players: List<Player> = emptyList(),
    isLoading: Boolean = false,
    onBackClick: () -> Unit = {},
    onContinueClick: (MatchLineup) -> Unit = {}
) {
    var selectedPosition by remember { mutableStateOf<String?>(null) }

    var position1 by remember { mutableStateOf<Player?>(null) }
    var position2 by remember { mutableStateOf<Player?>(null) }
    var position3 by remember { mutableStateOf<Player?>(null) }
    var position4 by remember { mutableStateOf<Player?>(null) }
    var position5 by remember { mutableStateOf<Player?>(null) }
    var position6 by remember { mutableStateOf<Player?>(null) }
    var libero by remember { mutableStateOf<Player?>(null) }

    val selectedPlayers = listOfNotNull(
        position1, position2, position3, position4, position5, position6, libero
    )

    val canContinue =
        position1 != null &&
                position2 != null &&
                position3 != null &&
                position4 != null &&
                position5 != null &&
                position6 != null &&
                libero != null

    if (selectedPosition != null) {
        PlayerSelectionDialog(
            title = "Select player",
            players = players.filter { player ->
                selectedPlayers.none { it.id == player.id } ||
                        getPlayerForPosition(
                            selectedPosition,
                            position1,
                            position2,
                            position3,
                            position4,
                            position5,
                            position6,
                            libero
                        )?.id == player.id
            },
            onDismiss = { selectedPosition = null },
            onPlayerSelected = { player ->
                when (selectedPosition) {
                    "P1" -> position1 = player
                    "P2" -> position2 = player
                    "P3" -> position3 = player
                    "P4" -> position4 = player
                    "P5" -> position5 = player
                    "P6" -> position6 = player
                    "L" -> libero = player
                }

                selectedPosition = null
            }
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
                text = "Starting Lineup",
                color = White,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Select 6 court players and 1 libero before starting the match.",
                color = Color(0xFFB6BCC6),
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 21.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

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
                VolleyballCourtLineup(
                    position1 = position1,
                    position2 = position2,
                    position3 = position3,
                    position4 = position4,
                    position5 = position5,
                    position6 = position6,
                    libero = libero,
                    onPositionClick = { selectedPosition = it }
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "${selectedPlayers.size}/7 PLAYERS SELECTED",
                color = Green1,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = {
                    onContinueClick(
                        MatchLineup(
                            position1PlayerId = position1?.id ?: "",
                            position2PlayerId = position2?.id ?: "",
                            position3PlayerId = position3?.id ?: "",
                            position4PlayerId = position4?.id ?: "",
                            position5PlayerId = position5?.id ?: "",
                            position6PlayerId = position6?.id ?: "",
                            liberoPlayerId = libero?.id ?: ""
                        )
                    )
                },
                enabled = canContinue,
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
                    text = "Continue  →",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun VolleyballCourtLineup(
    position1: Player?,
    position2: Player?,
    position3: Player?,
    position4: Player?,
    position5: Player?,
    position6: Player?,
    libero: Player?,
    onPositionClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF181D20), RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFF2A3036), RoundedCornerShape(12.dp))
            .padding(18.dp)
    ) {
        Text(
            text = "COURT FORMATION",
            color = Green1,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            CourtPlayerButton(
                label = "P4",
                player = position4,
                modifier = Modifier.weight(1f),
                onClick = { onPositionClick("P4") }
            )

            CourtPlayerButton(
                label = "P3",
                player = position3,
                modifier = Modifier.weight(1f),
                onClick = { onPositionClick("P3") }
            )

            CourtPlayerButton(
                label = "P2",
                player = position2,
                modifier = Modifier.weight(1f),
                onClick = { onPositionClick("P2") }
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            CourtPlayerButton(
                label = "P5",
                player = position5,
                modifier = Modifier.weight(1f),
                onClick = { onPositionClick("P5") }
            )

            CourtPlayerButton(
                label = "P6",
                player = position6,
                modifier = Modifier.weight(1f),
                onClick = { onPositionClick("P6") }
            )

            CourtPlayerButton(
                label = "P1",
                player = position1,
                modifier = Modifier.weight(1f),
                onClick = { onPositionClick("P1") }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            CourtPlayerButton(
                label = "LIBERO",
                player = libero,
                modifier = Modifier.width(105.dp),
                onClick = { onPositionClick("L") }
            )
        }
    }
}

@Composable
fun CourtPlayerButton(
    label: String,
    player: Player?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .height(100.dp)
            .background(
                if (player == null) Color(0xFF20252B) else Blue1.copy(alpha = 0.15f),
                RoundedCornerShape(10.dp)
            )
            .border(
                1.dp,
                if (player == null) Color(0xFF333A42) else Blue1,
                RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = label,
            color = if (player == null) Color(0xFF9CA3AF) else Blue1,
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (player == null) {
            Text(
                text = "Select",
                color = White,
                fontSize = 13.sp,
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
fun PlayerSelectionDialog(
    title: String,
    players: List<Player>,
    onDismiss: () -> Unit,
    onPlayerSelected: (Player) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                if (players.isEmpty()) {
                    Text("No players available.")
                } else {
                    players.forEach { player ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onPlayerSelected(player) }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "#${player.number}",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.width(54.dp)
                            )

                            Column {
                                Text(
                                    text = player.name,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = player.position,
                                    color = Color.Gray,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

fun getPlayerForPosition(
    selectedPosition: String?,
    position1: Player?,
    position2: Player?,
    position3: Player?,
    position4: Player?,
    position5: Player?,
    position6: Player?,
    libero: Player?
): Player? {
    return when (selectedPosition) {
        "P1" -> position1
        "P2" -> position2
        "P3" -> position3
        "P4" -> position4
        "P5" -> position5
        "P6" -> position6
        "L" -> libero
        else -> null
    }
}
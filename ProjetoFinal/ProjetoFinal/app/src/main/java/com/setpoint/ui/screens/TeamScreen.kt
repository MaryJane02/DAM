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
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.setpoint.data.model.Player
import com.setpoint.ui.components.BottomBar
import com.setpoint.ui.components.TopBar
import com.setpoint.ui.theme.Blue1
import com.setpoint.ui.theme.Green1
import com.setpoint.ui.theme.White
import com.setpoint.utils.getPlayerInitials

@Composable
fun TeamScreen(
    players: List<Player> = emptyList(),
    isLoading: Boolean = false,
    onPlayerClick: (Player) -> Unit = {},
    onAddPlayerClick: () -> Unit = {},
    onDashboardClick: () -> Unit = {},
    onStatsClick: () -> Unit = {},
    onMatchesClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onDeletePlayerClick: (Player) -> Unit = {},
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
                showProfileButton = true,
                onProfileClick = onProfileClick
            )


            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Team Roster",
                color = White,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "${players.size} ACTIVE PLAYERS",
                color = Green1,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = onAddPlayerClick,
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
                    text = "Add New Player",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
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

                    players.isEmpty() -> {
                        Text(
                            text = "No players added yet.",
                            color = Color(0xFF9CA3AF),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(18.dp)
                        )
                    }

                    else -> {
                        players.forEachIndexed { index, player ->
                            TeamPlayerRow(
                                player = player,
                                onClick = { onPlayerClick(player) },
                                onDeleteClick = { onDeletePlayerClick(player) }
                            )

                            if (index < players.lastIndex) {
                                HorizontalDivider(color = Color(0xFF2A3036))
                            }
                        }
                    }
                }
            }
        }

        BottomBar(
            selectedItem = "TEAM",
            onDashboardClick = onDashboardClick,
            onStatsClick = onStatsClick,
            onMatchesClick = onMatchesClick,
            onTeamClick = {},
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun TeamPlayerRow(
    player: Player,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
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

        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier
                .size(42.dp)
                .background(Color(0xFF3A1F1F), RoundedCornerShape(8.dp))
        ) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "Delete player",
                tint = Color(0xFFFFA3A3),
                modifier = Modifier.size(21.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = Color(0xFF6B7280)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TeamScreenPreview() {
    MaterialTheme {
        TeamScreen(
            players = listOf(
                Player(
                    id = "1",
                    name = "Mariana Almeida",
                    number = 14,
                    position = "Outside Hitter",
                    age = 21,
                    height = 178,
                    weight = 68.0
                )
            )
        )
    }
}
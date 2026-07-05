package com.setpoint.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.SportsVolleyball
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.setpoint.data.model.Team
import com.setpoint.ui.components.RegisterInputField
import com.setpoint.ui.components.TopBar
import com.setpoint.ui.theme.Blue1
import com.setpoint.ui.theme.Green1
import com.setpoint.ui.theme.White
import com.setpoint.utils.getCurrentSeasonText

@Composable
fun TeamProfileScreen(
    team: Team?,
    isLoading: Boolean = false,
    onSaveClick: (Team) -> Unit = {},
    onBackClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    if (isLoading || team == null) {
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

    var teamName by remember(team.id) { mutableStateOf(team.teamName) }
    var email by remember(team.id) { mutableStateOf(team.email) }
    var clubName by remember(team.id) { mutableStateOf(team.clubName) }
    var category by remember(team.id) { mutableStateOf(team.category) }
    val season = getCurrentSeasonText().removePrefix("SEASON ")
    var homeCourt by remember(team.id) { mutableStateOf(team.homeCourt) }
    var coachName by remember(team.id) { mutableStateOf(team.coachName) }

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
            TopBar(
                showProfileButton = false
            )

            Spacer(modifier = Modifier.height(20.dp))

            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            TeamProfileHeader(
                teamName = team.teamName,
                category = team.category,
                season = season
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Team Information",
                color = White,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(14.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF181D20), RoundedCornerShape(8.dp))
                    .border(1.dp, Color(0xFF2A3036), RoundedCornerShape(8.dp))
                    .padding(22.dp)
            ) {
                RegisterInputField(
                    label = "TEAM NAME",
                    value = teamName,
                    onValueChange = { teamName = it },
                    placeholder = "--",
                    icon = Icons.Outlined.Groups
                )

                Spacer(modifier = Modifier.height(18.dp))

                ReadOnlyTeamInfoField(
                    label = "EMAIL",
                    value = email,
                    icon = Icons.Outlined.Email
                )

                Spacer(modifier = Modifier.height(18.dp))

                RegisterInputField(
                    label = "CLUB NAME",
                    value = clubName,
                    onValueChange = { clubName = it },
                    placeholder = "Not defined",
                    icon = Icons.Outlined.Shield
                )

                Spacer(modifier = Modifier.height(18.dp))

                RegisterInputField(
                    label = "CATEGORY",
                    value = category,
                    onValueChange = { category = it },
                    placeholder = "Not defined",
                    icon = Icons.Outlined.SportsVolleyball
                )

                Spacer(modifier = Modifier.height(18.dp))

                RegisterInputField(
                    label = "HOME COURT",
                    value = homeCourt,
                    onValueChange = { homeCourt = it },
                    placeholder = "Not defined",
                    icon = Icons.Outlined.Home
                )

                Spacer(modifier = Modifier.height(18.dp))

                RegisterInputField(
                    label = "COACH NAME",
                    value = coachName,
                    onValueChange = { coachName = it },
                    placeholder = "Not defined",
                    icon = Icons.Outlined.Person
                )

                Spacer(modifier = Modifier.height(26.dp))

                Button(
                    onClick = {
                        onSaveClick(
                            team.copy(
                                teamName = teamName,
                                email = team.email,
                                clubName = clubName,
                                category = category,
                                season = season,
                                homeCourt = homeCourt,
                                coachName = coachName
                            )
                        )
                    },
                    enabled = teamName.isNotBlank() && email.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Blue1,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Save Team Profile  →",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onLogoutClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD64545),
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Logout,
                        contentDescription = "Logout",
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Logout",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun TeamProfileHeader(
    teamName: String,
    category: String,
    season: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF181D20), RoundedCornerShape(10.dp))
            .border(1.dp, Color(0xFF2A3036), RoundedCornerShape(10.dp))
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(92.dp)
                .background(Blue1.copy(alpha = 0.15f), RoundedCornerShape(26.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = getTeamInitials(teamName),
                color = Blue1,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = teamName.uppercase(),
            color = White,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = if (category.isBlank()) "NO CATEGORY DEFINED" else category.uppercase(),
            color = Green1,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "SEASON $season",
            color = Color(0xFF9CA3AF),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
    }
}

fun getTeamInitials(teamName: String): String {
    return teamName
        .trim()
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercase() }
}

@Composable
fun ReadOnlyTeamInfoField(
    label: String,
    value: String,
    icon: ImageVector
) {
    Column {
        Text(
            text = label,
            color = Color(0xFF9CA3AF),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xFF20252B), RoundedCornerShape(4.dp))
                .border(1.dp, Color(0xFF333A42), RoundedCornerShape(4.dp))
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = value,
                color = Color(0xFF9CA3AF),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TeamProfileScreenPreview() {
    MaterialTheme {
        TeamProfileScreen(
            team = Team(
                id = "1",
                teamName = "NewxGen Feminina",
                email = "mariana.amador.almeida@gmail.com",
                clubName = "CDR",
                category = "Seniores",
                season = "2025/2026",
                homeCourt = "Pavilhão Cacilhas Tejo",
                coachName = "Tiago Campos"
            )
        )
    }
}
package com.setpoint.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Height
import androidx.compose.material.icons.outlined.Numbers
import androidx.compose.material.icons.outlined.SportsVolleyball
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.setpoint.data.model.Player
import com.setpoint.ui.components.TopBar
import com.setpoint.ui.theme.Blue1
import com.setpoint.ui.theme.Green1
import com.setpoint.ui.theme.White
import com.setpoint.utils.getPlayerInitials
import com.setpoint.data.model.SeasonPlayerStats
import com.setpoint.utils.SeasonStatsCalculator

@Composable
fun PlayerProfileScreen(
    player: Player?,
    seasonStats: SeasonPlayerStats? = null,
    isLoading: Boolean = false,
    onBackClick: () -> Unit = {},
    onPhotoSelected: (Uri) -> Unit = {}
) {
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            onPhotoSelected(uri)
        }
    }

    if (isLoading || player == null) {
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

    val points = seasonStats?.points ?: 0
    val kills = seasonStats?.kills ?: 0
    val aces = seasonStats?.aces ?: 0
    val blocks = seasonStats?.blocks ?: 0
    val errors = seasonStats?.errors ?: 0
    val mvpScore = seasonStats?.let {
        SeasonStatsCalculator.calculateSeasonMvpScore(it)
    } ?: 0

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

            )

            Spacer(modifier = Modifier.height(24.dp))

            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            PlayerHeader(
                player = player,
                onPhotoClick = {
                    imagePicker.launch("image/*")
                }
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Player Information",
                color = White,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(14.dp))

            PlayerInfoCard(player)

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Season Statistics",
                color = White,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                PlayerStatCard("POINTS", points.toString(), Blue1, Modifier.weight(1f))
                PlayerStatCard("KILLS", kills.toString(), Green1, Modifier.weight(1f))
                PlayerStatCard("ERRORS", errors.toString(), Color(0xFFFFA3A3), Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                PlayerStatCard("ACES", aces.toString(), Blue1, Modifier.weight(1f))
                PlayerStatCard("BLOCKS", blocks.toString(), Green1, Modifier.weight(1f))
                PlayerStatCard("MVP SCORE", mvpScore.toString(), Color(0xFFFFD166), Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun PlayerHeader(
    player: Player,
    onPhotoClick: () -> Unit
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
                .size(96.dp)
                .clip(RoundedCornerShape(26.dp))
                .background(Blue1.copy(alpha = 0.15f))
                .clickable { onPhotoClick() },
            contentAlignment = Alignment.Center
        ) {
            if (player.photoUrl.isNotBlank()) {
                AsyncImage(
                    model = player.photoUrl,
                    contentDescription = "Player photo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    text = getPlayerInitials(player.name),
                    color = Blue1,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = player.name.uppercase(),
            color = White,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "#${player.number} • ${player.position.uppercase()}",
            color = Green1,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Tap photo to edit",
            color = Color(0xFF9CA3AF),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PlayerInfoCard(player: Player) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF181D20), RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFF2A3036), RoundedCornerShape(8.dp))
            .padding(18.dp)
    ) {
        PlayerInfoRow(Icons.Outlined.Numbers, "Number", "#${player.number}")
        HorizontalDivider(color = Color(0xFF2A3036))

        PlayerInfoRow(Icons.Outlined.SportsVolleyball, "Position", player.position)
        HorizontalDivider(color = Color(0xFF2A3036))

        PlayerInfoRow(Icons.Outlined.Height, "Height", "${player.height} cm")
        HorizontalDivider(color = Color(0xFF2A3036))

        PlayerInfoRow(Icons.Outlined.FitnessCenter, "Weight", "${player.weight} kg")
        HorizontalDivider(color = Color(0xFF2A3036))

        PlayerInfoRow(Icons.Outlined.BarChart, "Age", "${player.age} years")
    }
}

@Composable
fun PlayerInfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Blue1
        )

        Spacer(modifier = Modifier.width(14.dp))

        Text(
            text = label,
            color = Color(0xFF9CA3AF),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = value,
            color = White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PlayerStatCard(
    title: String,
    value: String,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(98.dp)
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

@Preview(showBackground = true)
@Composable
fun PlayerProfileScreenPreview() {
    MaterialTheme {
        PlayerProfileScreen(
            player = Player(
                id = "1",
                name = "Mariana Almeida",
                age = 21,
                height = 178,
                weight = 68.0,
                position = "Outside Hitter",
                number = 14,
                photoUrl = ""
            )
        )
    }
}
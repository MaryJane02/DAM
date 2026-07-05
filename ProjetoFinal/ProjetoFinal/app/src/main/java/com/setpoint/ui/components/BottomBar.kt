package com.setpoint.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PersonAddAlt1
import androidx.compose.material.icons.outlined.SportsVolleyball
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.setpoint.ui.theme.Blue1
@Composable
fun BottomBar(
    selectedItem: String,
    onDashboardClick: () -> Unit = {},
    onStatsClick: () -> Unit = {},
    onMatchesClick: () -> Unit = {},
    onTeamClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(74.dp)
            .background(Color(0xFF181D20))
            .border(1.dp, Color(0xFF2A3036))
            .padding(horizontal = 14.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(
            icon = Icons.Outlined.Dashboard,
            label = "DASHBOARD",
            selected = selectedItem == "DASHBOARD",
            onClick = onDashboardClick
        )

        BottomNavItem(
            icon = Icons.Outlined.SportsVolleyball,
            label = "STATS",
            selected = selectedItem == "STATS",
            onClick = onStatsClick
        )

        BottomNavItem(
            icon = Icons.Outlined.CalendarMonth,
            label = "MATCHES",
            selected = selectedItem == "MATCHES",
            onClick = onMatchesClick
        )

        BottomNavItem(
            icon = Icons.Outlined.PersonAddAlt1,
            label = "TEAM",
            selected = selectedItem == "TEAM",
            onClick = onTeamClick
        )
    }
}

@Composable
fun BottomNavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (selected) Blue1 else Color(0xFF6B7280)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            color = if (selected) Blue1 else Color(0xFF6B7280),
            fontSize = 10.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp
        )
    }
}
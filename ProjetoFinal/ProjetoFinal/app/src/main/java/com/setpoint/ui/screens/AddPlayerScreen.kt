package com.setpoint.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Height
import androidx.compose.material.icons.outlined.Numbers
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.SportsVolleyball
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.setpoint.ui.components.RegisterInputField
import com.setpoint.ui.components.TopBar
import com.setpoint.ui.theme.Blue1
import com.setpoint.ui.theme.Green1
import com.setpoint.ui.theme.White

data class NewPlayerForm(
    val name: String,
    val age: String,
    val height: String,
    val weight: String,
    val position: String,
    val number: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlayerScreen(
    onSavePlayerClick: (NewPlayerForm) -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf(18) }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }

    var positionExpanded by remember { mutableStateOf(false) }

    val positions = listOf(
        "Setter",
        "Outside Hitter",
        "Opposite",
        "Middle Blocker",
        "Libero"
    )

    val isFormValid =
        name.isNotBlank() &&
                height.isNotBlank() &&
                weight.isNotBlank() &&
                position.isNotBlank() &&
                number.isNotBlank()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101418))
            .padding(horizontal = 24.dp, vertical = 18.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(
                showProfileButton = false
            )


            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "TEAM MANAGEMENT",
                color = Green1,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                modifier = Modifier
                    .border(1.dp, Color(0xFF333A42), RoundedCornerShape(6.dp))
                    .background(Color(0xFF20252B), RoundedCornerShape(6.dp))
                    .padding(horizontal = 18.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Add Player",
                color = White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Register a new player and add them\nto your team roster.",
                color = White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF181D20), RoundedCornerShape(8.dp))
                    .border(1.dp, Color(0xFF2A3036), RoundedCornerShape(8.dp))
                    .padding(22.dp)
            ) {
                RegisterInputField(
                    label = "PLAYER NAME",
                    value = name,
                    onValueChange = { name = it },
                    placeholder = "",
                    icon = Icons.Outlined.Person
                )

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        NumberStepperField(
                            label = "AGE",
                            value = age,
                            onValueChange = { age = it },
                            minValue = 1,
                            maxValue = 100
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        RegisterInputField(
                            label = "NUMBER",
                            value = number,
                            onValueChange = { number = it },
                            placeholder = "",
                            icon = Icons.Outlined.Numbers
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        RegisterInputField(
                            label = "HEIGHT",
                            value = height,
                            onValueChange = { height = it },
                            placeholder = "--- cm",
                            icon = Icons.Outlined.Height
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        RegisterInputField(
                            label = "WEIGHT",
                            value = weight,
                            onValueChange = { weight = it },
                            placeholder = "-- kg",
                            icon = Icons.Outlined.FitnessCenter
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                ExposedDropdownMenuBox(
                    expanded = positionExpanded,
                    onExpandedChange = {
                        positionExpanded = !positionExpanded
                    }
                ) {
                    OutlinedTextField(
                        value = position,
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text(
                                text = "POSITION",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        },
                        placeholder = {
                            Text(
                                text = "Choose position",
                                color = Color(0xFF6B7280)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.SportsVolleyball,
                                contentDescription = null,
                                tint = Color(0xFF9CA3AF)
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = positionExpanded
                            )
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(4.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = White,
                            unfocusedTextColor = White,
                            focusedBorderColor = Blue1,
                            unfocusedBorderColor = Color(0xFF333A42),
                            focusedLabelColor = Blue1,
                            unfocusedLabelColor = Color(0xFF9CA3AF),
                            focusedContainerColor = Color(0xFF20252B),
                            unfocusedContainerColor = Color(0xFF20252B),
                            cursorColor = Blue1
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = positionExpanded,
                        onDismissRequest = {
                            positionExpanded = false
                        },
                        modifier = Modifier.background(Color(0xFF20252B))
                    ) {
                        positions.forEach { selectedPosition ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = selectedPosition,
                                        color = White,
                                        fontWeight = FontWeight.Bold
                                    )
                                },
                                onClick = {
                                    position = selectedPosition
                                    positionExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(26.dp))

                Button(
                    onClick = {
                        onSavePlayerClick(
                            NewPlayerForm(
                                name = name,
                                age = age.toString(),
                                height = height,
                                weight = weight,
                                position = position,
                                number = number
                            )
                        )
                    },
                    enabled = isFormValid,
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
                        text = "Save Player  →",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedButton(
                    onClick = onCancelClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(4.dp),
                    border = ButtonDefaults.outlinedButtonBorder,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Green1
                    )
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun NumberStepperField(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    minValue: Int = 1,
    maxValue: Int = 100
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
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    if (value > minValue) onValueChange(value - 1)
                }
            ) {
                Text("-", color = White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }

            Text(
                text = value.toString(),
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            IconButton(
                onClick = {
                    if (value < maxValue) onValueChange(value + 1)
                }
            ) {
                Text("+", color = White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddPlayerScreenPreview() {
    MaterialTheme {
        AddPlayerScreen()
    }
}
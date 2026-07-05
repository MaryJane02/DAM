package com.setpoint.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.setpoint.ui.components.RegisterInputField
import com.setpoint.ui.components.TopBar
import com.setpoint.ui.theme.Blue1
import com.setpoint.ui.theme.Green1
import com.setpoint.ui.theme.White
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

data class NewMatchForm(
    val opponent: String,
    val date: String,
    val time: String,
    val location: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMatchScreen(
    onSaveMatchClick: (NewMatchForm) -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
    var opponent by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()

    val timePickerState = rememberTimePickerState(
        initialHour = 20,
        initialMinute = 30,
        is24Hour = true
    )

    val isFormValid =
        opponent.isNotBlank() &&
                date.isNotBlank() &&
                time.isNotBlank() &&
                location.isNotBlank()

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedMillis = datePickerState.selectedDateMillis

                        if (selectedMillis != null) {
                            val selectedDate = Instant
                                .ofEpochMilli(selectedMillis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()

                            val formatter = DateTimeFormatter.ofPattern(
                                "dd/MM/yyyy",
                                Locale.getDefault()
                            )

                            date = selectedDate.format(formatter)
                        }

                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        time = String.format(
                            Locale.getDefault(),
                            "%02d:%02d",
                            timePickerState.hour,
                            timePickerState.minute
                        )

                        showTimePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showTimePicker = false }
                ) {
                    Text("Cancel")
                }
            },
            title = {
                Text("Select start time")
            },
            text = {
                TimePicker(
                    state = timePickerState
                )
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(
                showProfileButton = false
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "MATCH SCHEDULE",
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
                text = "New Match",
                color = White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Schedule a new volleyball match\nfor your team.",
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
                    label = "OPPONENT TEAM",
                    value = opponent,
                    onValueChange = { opponent = it },
                    placeholder = "Opponent Team's Name",
                    icon = Icons.Outlined.Shield
                )

                Spacer(modifier = Modifier.height(18.dp))

                SelectMatchField(
                    label = "DATE",
                    value = date,
                    placeholder = "Select date",
                    icon = Icons.Outlined.CalendarMonth,
                    onClick = { showDatePicker = true }
                )

                Spacer(modifier = Modifier.height(18.dp))

                SelectMatchField(
                    label = "START TIME",
                    value = time,
                    placeholder = "Select time",
                    icon = Icons.Outlined.Schedule,
                    onClick = { showTimePicker = true }
                )

                Spacer(modifier = Modifier.height(18.dp))

                RegisterInputField(
                    label = "LOCATION",
                    value = location,
                    onValueChange = { location = it },
                    placeholder = "Match location",
                    icon = Icons.Outlined.LocationOn
                )

                Spacer(modifier = Modifier.height(26.dp))

                Button(
                    onClick = {
                        onSaveMatchClick(
                            NewMatchForm(
                                opponent = opponent,
                                date = date,
                                time = time,
                                location = location
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
                        text = "Save Match  →",
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
fun SelectMatchField(
    label: String,
    value: String,
    placeholder: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Box {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    text = label,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            },
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color(0xFF6B7280)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF9CA3AF)
                )
            },
            modifier = Modifier.fillMaxWidth(),
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

        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { onClick() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddMatchScreenPreview() {
    MaterialTheme {
        AddMatchScreen()
    }
}
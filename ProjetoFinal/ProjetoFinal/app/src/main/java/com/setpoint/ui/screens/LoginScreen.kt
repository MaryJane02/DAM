package com.setpoint.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.setpoint.ui.components.RegisterInputField
import com.setpoint.ui.theme.Blue1
import com.setpoint.ui.theme.Green1
import com.setpoint.ui.theme.White
import com.setpoint.ui.components.SocialButton
import com.setpoint.ui.components.TopBar
import com.setpoint.R

@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit = { _, _ -> },
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: (String) -> Unit = {},
    onGoogleLoginClick: () -> Unit = {}

) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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

            Text(
                text = "TEAM PORTAL",
                color = Green1,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color(0xFF333A42),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .background(Color(0xFF20252B), RoundedCornerShape(6.dp))
                    .padding(horizontal = 18.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Login",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Access your team statistics\nand match performance data.",
                color = White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF181D20), RoundedCornerShape(8.dp))
                    .border(1.dp, Color(0xFF2A3036), RoundedCornerShape(8.dp))
                    .padding(22.dp)
            ) {
                RegisterInputField(
                    label = "EMAIL ADDRESS",
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "team@setpoint.com",
                    icon = Icons.Outlined.Email
                )

                Spacer(modifier = Modifier.height(18.dp))

                RegisterInputField(
                    label = "PASSWORD",
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "••••••••",
                    icon = Icons.Outlined.Lock,
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Forgot Your Password?",
                    color = Green1,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {onForgotPasswordClick(email)}
                )

                Spacer(modifier = Modifier.height(22.dp))

                Button(
                    onClick = {
                        onLoginClick(email, password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Blue1,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Login  →",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = Color(0xFF2A3036)
                    )

                    Text(
                        text = "OR LOGIN WITH",
                        color = Color(0xFF6B7280),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = Color(0xFF2A3036)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    SocialButton(
                        text = "Google",
                        painter = painterResource(R.drawable.ic_google_logo),
                        modifier = Modifier.weight(1f),
                        onGoogleLoginClick
                    )

                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            Row {
                Text(
                    text = "Don't have an account? ",
                    color = White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Sign Up",
                    color = Green1,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onRegisterClick() }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "© 2026 SET POINT TECHNOLOGIES. ALL RIGHTS RESERVED.",
                color = Color(0xFF6B7280),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen()
    }
}
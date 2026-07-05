package com.setpoint.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.setpoint.R
import androidx.compose.ui.graphics.graphicsLayer
import com.setpoint.ui.components.TopBar
import com.setpoint.ui.theme.Black
import com.setpoint.ui.theme.Blue1
import com.setpoint.ui.theme.Green1
import com.setpoint.ui.theme.White

@Composable
fun FirstScreen(
    onGetStartedClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.home_screen_background_image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = 1f,
                    scaleY = 1f,
                    translationX = 0f,
                    translationY = 0f
                ),
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter
        )

        // overlay base
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Black.copy(alpha = 0.40f))
        )

        // gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.0f to Black.copy(alpha = 0.75f),
                            0.3f to Color.Transparent,
                            0.6f to Color.Transparent,
                            0.85f to Black.copy(alpha = 0.9f),
                            1.0f to Black
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(
                showProfileButton = false
            )

            Spacer(modifier = Modifier.height(140.dp))

            Text(
                text = "SET\nPOINT",
                color = Color.White,
                fontSize = 64.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                lineHeight = 58.sp
            )


            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(6.dp)
                    .background(
                        color = Blue1,
                        shape = RoundedCornerShape(50)
                    )
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Welcome to smarter coaching and performance tracking.",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                lineHeight = 26.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onGetStartedClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(78.dp),
                shape = RoundedCornerShape(9.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue1
                )
            ) {
                Text(
                    text = "GET STARTED ↗",
                    color = White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedButton(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(78.dp),
                border = BorderStroke(2.dp, Green1),
                shape = RoundedCornerShape(9.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Black.copy(alpha = 0.35f)
                )
            ) {
                Text(
                    text = "LOGIN",
                    color = Green1,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 4.sp
                )
            }

            Spacer(modifier = Modifier.height(36.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewFirstScreen() {
    MaterialTheme {
        FirstScreen()
    }
}



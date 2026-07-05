package com.setpoint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.setpoint.ui.navigation.AppNavGraph
import com.setpoint.ui.theme.SetPointTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SetPointTheme {
                AppNavGraph()
            }
        }
    }
}
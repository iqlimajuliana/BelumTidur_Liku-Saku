package com.example.tamproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.tamproject.ui.*
import com.example.tamproject.ui.theme.TAMPROJECTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TAMPROJECTTheme {
                var currentScreen by remember { mutableStateOf("login") }
                
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (currentScreen) {
                        "login" -> LoginScreen(onLoginClick = { currentScreen = "dashboard" })
                        "dashboard" -> DashboardScreen(
                            onSortingWasteClick = { },
                            onSmartPantryClick = { },
                            onEcoChallengeClick = { },
                            onRewardClick = { },
                            onLogoutClick = { currentScreen = "login" }
                        )
                    }
                }
            }
        }
    }
}

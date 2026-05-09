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
                            onSortingWasteClick = { currentScreen = "waste_sorting" },
                            onSmartPantryClick = { currentScreen = "smart_pantry" },
                            onEcoChallengeClick = { currentScreen = "eco_challenge" }, // Pastikan ini diisi
                            onRewardClick = { currentScreen = "my_points" },
                            onLogoutClick = { currentScreen = "login" }
                        )
                        "waste_sorting" -> WasteSortingScreen(
                            onInorganicClick = { currentScreen = "inorganic" },
                            onHomeClick = { currentScreen = "dashboard" },
                            onEcoChallengeClick = { },
                            onWasteSortingClick = { currentScreen = "waste_sorting" },
                            onMyPointsClick = { currentScreen = "my_points" },
                            onSmartPantryClick = { currentScreen = "smart_pantry" }
                        )

                        "smart_pantry" -> SmartPantryScreen(
                            onCalendarClick = { currentScreen = "meal_plan" },
                            onHomeClick = { currentScreen = "dashboard" },
                            onEcoChallengeClick = { },
                            onWasteSortingClick = { currentScreen = "waste_sorting" },
                            onMyPointsClick = { currentScreen = "my_points" },
                            onSmartPantryClick = { currentScreen = "smart_pantry" }
                        )

                        "meal_plan" -> MealPlanScreen(
                            onBack = { currentScreen = "smart_pantry" },
                            onHomeClick = { currentScreen = "dashboard" },
                            onEcoChallengeClick = { },
                            onWasteSortingClick = { currentScreen = "waste_sorting" },
                            onMyPointsClick = { currentScreen = "my_points" },
                            onSmartPantryClick = { currentScreen = "smart_pantry" }
                        )

                        "inorganic" -> InorganicScreen(
                            onBack = { currentScreen = "waste_sorting" }
                        )

                        "my_points" -> MyPointsScreen(
                            onHomeClick = { currentScreen = "dashboard" },
                            onEcoChallengeClick = { },
                            onWasteSortingClick = { currentScreen = "waste_sorting" },
                            onMyPointsClick = { currentScreen = "my_points" },
                            onSmartPantryClick = { currentScreen = "smart_pantry" }
                        )

                        "eco_challenge" -> EcoChallengeScreen(
                            onHomeClick = { currentScreen = "dashboard" },
                            onEcoChallengeClick = { currentScreen = "eco_challenge" },
                            onWasteSortingClick = { currentScreen = "waste_sorting" },
                            onMyPointsClick = { currentScreen = "my_points" },
                            onSmartPantryClick = { currentScreen = "smart_pantry" }
                        )
                    }
                }
            }
        }
    }
}

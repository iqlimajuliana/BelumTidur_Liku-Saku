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
import com.example.tamproject.ui.screen.dashboard.DashboardScreen
import com.example.tamproject.ui.screen.login.LoginScreen
import com.example.tamproject.ui.screen.fitur.MarketScreen
import com.example.tamproject.ui.screen.fitur.EcoWasteDropScreen
import com.example.tamproject.ui.screen.fitur.EcoChallengeScreen
import com.example.tamproject.ui.screen.fitur.MyPointsScreen
import com.example.tamproject.ui.screen.fitur.SmartPantryScreen
import com.example.tamproject.ui.screen.fitur.WasteSortingScreen
import com.example.tamproject.ui.screen.fitur.NotificationScreen
import com.example.tamproject.ui.screen.fitur.ProfileScreen
import com.example.tamproject.ui.screen.fiturdetail.MealPlanScreen
import com.example.tamproject.ui.screen.fiturdetail.InorganicScreen

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
                            onEcoChallengeClick = { currentScreen = "eco_challenge" },
                            onRewardClick = { currentScreen = "my_points" },
                            onEcoWasteDropClick = { currentScreen = "eco_waste_drop" },
                            onMarketClick = { currentScreen = "market" },
                            onLogoutClick = { currentScreen = "login" },
                            onNotificationClick = { currentScreen = "notification" },
                            onProfileClick = { currentScreen = "profile" }
                        )

                        "notification" -> NotificationScreen(
                            onHomeClick = { currentScreen = "dashboard" },
                            onEcoChallengeClick = { currentScreen = "eco_challenge" },
                            onWasteSortingClick = { currentScreen = "waste_sorting" },
                            onMyPointsClick = { currentScreen = "my_points" },
                            onSmartPantryClick = { currentScreen = "smart_pantry" },
                            onNotificationClick = { },
                            onProfileClick = { currentScreen = "profile" }
                        )

                        "profile" -> ProfileScreen(
                            onHomeClick = { currentScreen = "dashboard" },
                            onEcoChallengeClick = { currentScreen = "eco_challenge" },
                            onWasteSortingClick = { currentScreen = "waste_sorting" },
                            onMyPointsClick = { currentScreen = "my_points" },
                            onSmartPantryClick = { currentScreen = "smart_pantry" },
                            onNotificationClick = { currentScreen = "notification" },
                            onProfileClick = { }
                        )

                        "eco_waste_drop" -> EcoWasteDropScreen(
                            onHomeClick = { currentScreen = "dashboard" },
                            onEcoChallengeClick = { currentScreen = "eco_challenge" },
                            onWasteSortingClick = { currentScreen = "waste_sorting" },
                            onMyPointsClick = { currentScreen = "my_points" },
                            onSmartPantryClick = { currentScreen = "smart_pantry" },
                            onNotificationClick = { currentScreen = "notification" },
                            onProfileClick = { currentScreen = "profile" }
                        )

                        "market" -> MarketScreen(
                            onHomeClick = { currentScreen = "dashboard" },
                            onEcoChallengeClick = { currentScreen = "eco_challenge" },
                            onWasteSortingClick = { currentScreen = "waste_sorting" },
                            onMyPointsClick = { currentScreen = "my_points" },
                            onSmartPantryClick = { currentScreen = "smart_pantry" },
                            onNotificationClick = { currentScreen = "notification" },
                            onProfileClick = { currentScreen = "profile" }
                        )

                        "waste_sorting" -> WasteSortingScreen(
                            onInorganicClick = { currentScreen = "inorganic" },
                            onHomeClick = { currentScreen = "dashboard" },
                            onEcoChallengeClick = { currentScreen = "eco_challenge" },
                            onWasteSortingClick = { },
                            onMyPointsClick = { currentScreen = "my_points" },
                            onSmartPantryClick = { currentScreen = "smart_pantry" },
                            onNotificationClick = { currentScreen = "notification" },
                            onProfileClick = { currentScreen = "profile" }
                        )

                        "smart_pantry" -> SmartPantryScreen(
                            onCalendarClick = { currentScreen = "meal_plan" },
                            onHomeClick = { currentScreen = "dashboard" },
                            onEcoChallengeClick = { currentScreen = "eco_challenge" },
                            onWasteSortingClick = { currentScreen = "waste_sorting" },
                            onMyPointsClick = { currentScreen = "my_points" },
                            onSmartPantryClick = { },
                            onNotificationClick = { currentScreen = "notification" },
                            onProfileClick = { currentScreen = "profile" }
                        )

                        "meal_plan" -> MealPlanScreen(
                            onBack = { currentScreen = "smart_pantry" },
                            onHomeClick = { currentScreen = "dashboard" },
                            onEcoChallengeClick = { currentScreen = "eco_challenge" },
                            onWasteSortingClick = { currentScreen = "waste_sorting" },
                            onMyPointsClick = { currentScreen = "my_points" },
                            onSmartPantryClick = { currentScreen = "smart_pantry" },
                            onNotificationClick = { currentScreen = "notification" },
                            onProfileClick = { currentScreen = "profile" }
                        )

                        "inorganic" -> InorganicScreen(
                            onBack = { currentScreen = "waste_sorting" }
                        )

                        "my_points" -> MyPointsScreen(
                            onHomeClick = { currentScreen = "dashboard" },
                            onEcoChallengeClick = { currentScreen = "eco_challenge" },
                            onWasteSortingClick = { currentScreen = "waste_sorting" },
                            onMyPointsClick = { },
                            onSmartPantryClick = { currentScreen = "smart_pantry" },
                            onNotificationClick = { currentScreen = "notification" },
                            onProfileClick = { currentScreen = "profile" }
                        )

                        "eco_challenge" -> EcoChallengeScreen(
                            onHomeClick = { currentScreen = "dashboard" },
                            onEcoChallengeClick = { },
                            onWasteSortingClick = { currentScreen = "waste_sorting" },
                            onMyPointsClick = { currentScreen = "my_points" },
                            onSmartPantryClick = { currentScreen = "smart_pantry" },
                            onNotificationClick = { currentScreen = "notification" },
                            onProfileClick = { currentScreen = "profile" }
                        )
                    }
                }
            }
        }
    }
}

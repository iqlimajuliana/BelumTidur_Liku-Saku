package com.example.tamproject.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Register : Screen("register")
    object Dashboard : Screen("dashboard")
    object Notification : Screen("notification")
    object Profile : Screen("profile")
    object EcoWasteDrop : Screen("eco_waste_drop")
    object Market : Screen("market")
    object WasteSorting : Screen("waste_sorting")
    object SmartPantry : Screen("smart_pantry")
    object MealPlan : Screen("meal_plan")
    object WasteDetail : Screen("waste_detail/{itemId}") {
        fun createRoute(itemId: String) = "waste_detail/$itemId"
    }
    object MyPoints : Screen("my_points")
    object EcoChallenge : Screen("eco_challenge")
    object Camera : Screen("camera")
    object InputSorting : Screen("input_sorting/{imageUri}") {
        fun createRoute(imageUri: String) = "input_sorting/$imageUri"
    }
    object WasteHistory : Screen("waste_history/{category}") {
        fun createRoute(category: String) = "waste_history/$category"
    }
}

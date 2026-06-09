package com.example.tamproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tamproject.data.repository.*
import com.example.tamproject.ui.screen.login.LoginScreen
import com.example.tamproject.ui.screen.login.RegisterScreen
import com.example.tamproject.ui.screen.login.SplashScreen
import com.example.tamproject.ui.screen.login.WelcomeScreen
import com.example.tamproject.ui.screen.dashboard.DashboardScreen
import com.example.tamproject.ui.screen.fitur.*
import com.example.tamproject.ui.screen.fiturdetail.*
import com.example.tamproject.ui.viewmodel.*

@Composable
fun AppNavGraph(
    authRepository: AuthRepository,
    pantryRepository: PantryRepository,
    challengeRepository: ChallengeRepository,
    wasteRepository: WasteRepository,
    marketRepository: MarketRepository,
    notificationRepository: NotificationRepository,
    pointsRepository: PointsRepository,
    profileRepository: ProfileRepository
) {
    val navController = rememberNavController()

    // AuthViewModel initialized at the top to check login state globally
    val authViewModel: AuthViewModel = viewModel(
        factory = BaseViewModelFactory { AuthViewModel(authRepository) }
    )
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState(initial = false)

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // --- AUTH ROUTES ---
        composable(Screen.Splash.route) {
            SplashScreen(
                onAnimationFinished = {
                    if (isLoggedIn) {
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.Welcome.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onGetStartedClick = { navController.navigate(Screen.Login.route) },
                onSignUpClick = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = authViewModel,
                onLoginClick = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onSignUpClick = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = authViewModel,
                onBackToLogin = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                        popUpTo(Screen.Login.route) { inclusive = true }
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        // --- DASHBOARD & MAIN NAVIGATION ---
        composable(Screen.Dashboard.route) {
            val dashboardViewModel: DashboardViewModel = viewModel(
                factory = BaseViewModelFactory { DashboardViewModel(authRepository) }
            )
            DashboardScreen(
                viewModel = dashboardViewModel,
                onHomeClick = { /* Already on Home */ },
                onEcoChallengeClick = { navController.navigate(Screen.EcoChallenge.route) },
                onScanClick = { navController.navigate(Screen.Camera.route) },
                onNotificationClick = { navController.navigate(Screen.Notification.route) },
                onProfileClick = { navController.navigate(Screen.Profile.route) },
                onWasteSortingClick = { navController.navigate(Screen.WasteSorting.route) },
                onMyPointsClick = { navController.navigate(Screen.MyPoints.route) },
                onSmartPantryClick = { navController.navigate(Screen.SmartPantry.route) },
                onEcoWasteDropClick = { navController.navigate(Screen.EcoWasteDrop.route) },
                onMarketClick = { navController.navigate(Screen.Market.route) },
                onLogoutClick = {
                    authViewModel.logout()
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // --- FEATURES ---
        composable(Screen.Camera.route) {
            CameraScreen(
                onBack = { navController.popBackStack() },
                onImageCaptured = { uri ->
                    navController.navigate(Screen.InputSorting.createRoute(uri))
                }
            )
        }

        composable(
            route = Screen.InputSorting.route,
            arguments = listOf(navArgument("imageUri") { type = NavType.StringType })
        ) { backStackEntry ->
            val imageUri = backStackEntry.arguments?.getString("imageUri") ?: ""
            val wasteViewModel: WasteViewModel = viewModel(
                factory = BaseViewModelFactory { WasteViewModel(wasteRepository, authRepository) }
            )
            InputSortingScreen(
                viewModel = wasteViewModel,
                imageUri = imageUri,
                onBack = { navController.popBackStack() },
                onSuccess = {
                    navController.navigate(Screen.WasteSorting.route) {
                        popUpTo(Screen.Camera.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Notification.route) {
            val notificationViewModel: NotificationViewModel = viewModel(
                factory = BaseViewModelFactory { NotificationViewModel(notificationRepository, authRepository) }
            )
            NotificationScreen(
                viewModel = notificationViewModel,
                onHomeClick = { navController.navigate(Screen.Dashboard.route) },
                onEcoChallengeClick = { navController.navigate(Screen.EcoChallenge.route) },
                onScanClick = { navController.navigate(Screen.Camera.route) },
                onNotificationClick = { /* Already here */ },
                onProfileClick = { navController.navigate(Screen.Profile.route) }
            )
        }

        composable(Screen.Profile.route) {
            val profileViewModel: ProfileViewModel = viewModel(
                factory = BaseViewModelFactory { ProfileViewModel(profileRepository, authRepository) }
            )
            ProfileScreen(
                viewModel = profileViewModel,
                onHomeClick = { navController.navigate(Screen.Dashboard.route) },
                onEcoChallengeClick = { navController.navigate(Screen.EcoChallenge.route) },
                onScanClick = { navController.navigate(Screen.Camera.route) },
                onNotificationClick = { navController.navigate(Screen.Notification.route) },
                onProfileClick = { /* Already here */ }
            )
        }

        composable(Screen.EcoWasteDrop.route) {
            val wasteViewModel: WasteViewModel = viewModel(
                factory = BaseViewModelFactory { WasteViewModel(wasteRepository, authRepository) }
            )
            EcoWasteDropScreen(
                viewModel = wasteViewModel,
                onHomeClick = { navController.navigate(Screen.Dashboard.route) },
                onEcoChallengeClick = { navController.navigate(Screen.EcoChallenge.route) },
                onScanClick = { navController.navigate(Screen.Camera.route) },
                onNotificationClick = { navController.navigate(Screen.Notification.route) },
                onProfileClick = { navController.navigate(Screen.Profile.route) }
            )
        }

        composable(Screen.Market.route) {
            val marketViewModel: MarketViewModel = viewModel(
                factory = BaseViewModelFactory { MarketViewModel(marketRepository, authRepository) }
            )
            MarketScreen(
                viewModel = marketViewModel,
                onHomeClick = { navController.navigate(Screen.Dashboard.route) },
                onEcoChallengeClick = { navController.navigate(Screen.EcoChallenge.route) },
                onScanClick = { navController.navigate(Screen.Camera.route) },
                onNotificationClick = { navController.navigate(Screen.Notification.route) },
                onProfileClick = { navController.navigate(Screen.Profile.route) }
            )
        }

        composable(Screen.WasteSorting.route) {
            val wasteViewModel: WasteViewModel = viewModel(
                factory = BaseViewModelFactory { WasteViewModel(wasteRepository, authRepository) }
            )
            WasteSortingScreen(
                viewModel = wasteViewModel,
                onCategoryClick = { category ->
                    navController.navigate(Screen.WasteHistory.createRoute(category))
                },
                onHomeClick = { navController.navigate(Screen.Dashboard.route) },
                onEcoChallengeClick = { navController.navigate(Screen.EcoChallenge.route) },
                onScanClick = { navController.navigate(Screen.Camera.route) },
                onNotificationClick = { navController.navigate(Screen.Notification.route) },
                onProfileClick = { navController.navigate(Screen.Profile.route) }
            )
        }

        composable(
            route = Screen.WasteHistory.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            val wasteViewModel: WasteViewModel = viewModel(
                factory = BaseViewModelFactory { WasteViewModel(wasteRepository, authRepository) }
            )
            WasteHistoryScreen(
                viewModel = wasteViewModel,
                category = category,
                onItemClick = { item ->
                    navController.navigate(Screen.WasteDetail.createRoute(item.id))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.SmartPantry.route) {
            val pantryViewModel: PantryViewModel = viewModel(
                factory = BaseViewModelFactory { PantryViewModel(pantryRepository, authRepository) }
            )
            SmartPantryScreen(
                viewModel = pantryViewModel,
                onCalendarClick = { navController.navigate(Screen.MealPlan.route) },
                onHomeClick = { navController.navigate(Screen.Dashboard.route) },
                onEcoChallengeClick = { navController.navigate(Screen.EcoChallenge.route) },
                onScanClick = { navController.navigate(Screen.Camera.route) },
                onNotificationClick = { navController.navigate(Screen.Notification.route) },
                onProfileClick = { navController.navigate(Screen.Profile.route) }
            )
        }

        composable(Screen.MealPlan.route) {
            MealPlanScreen(
                onBack = { navController.popBackStack() },
                onHomeClick = { navController.navigate(Screen.Dashboard.route) },
                onEcoChallengeClick = { navController.navigate(Screen.EcoChallenge.route) },
                onScanClick = { navController.navigate(Screen.Camera.route) },
                onNotificationClick = { navController.navigate(Screen.Notification.route) },
                onProfileClick = { navController.navigate(Screen.Profile.route) }
            )
        }

        composable(
            route = Screen.WasteDetail.route,
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
            val wasteViewModel: WasteViewModel = viewModel(
                factory = BaseViewModelFactory { WasteViewModel(wasteRepository, authRepository) }
            )
            
            val scanHistory by wasteViewModel.scanHistory.collectAsState()
            val item = scanHistory.find { it.id == itemId }
            
            val ecoDrops by wasteViewModel.ecoDrops.collectAsState()
            val nearestDrop = ecoDrops.firstOrNull()
            
            WasteDetailScreen(
                item = item,
                onBack = { navController.popBackStack() },
                onDropOffClick = { navController.navigate(Screen.EcoWasteDrop.route) },
                nearestDropName = nearestDrop?.name ?: "PT Angkut Aja",
                nearestDropDistance = nearestDrop?.distance ?: "2 Km"
            )
        }

        composable(Screen.MyPoints.route) {
            val pointsViewModel: PointsViewModel = viewModel(
                factory = BaseViewModelFactory { PointsViewModel(pointsRepository, authRepository) }
            )
            MyPointsScreen(
                viewModel = pointsViewModel,
                onHomeClick = { navController.navigate(Screen.Dashboard.route) },
                onEcoChallengeClick = { navController.navigate(Screen.EcoChallenge.route) },
                onScanClick = { navController.navigate(Screen.Camera.route) },
                onNotificationClick = { navController.navigate(Screen.Notification.route) },
                onProfileClick = { navController.navigate(Screen.Profile.route) },
                onEcoWasteDropClick = { navController.navigate(Screen.EcoWasteDrop.route) }
            )
        }

        composable(Screen.EcoChallenge.route) {
            val challengeViewModel: ChallengeViewModel = viewModel(
                factory = BaseViewModelFactory { ChallengeViewModel(challengeRepository, authRepository) }
            )
            EcoChallengeScreen(
                viewModel = challengeViewModel,
                onHomeClick = { navController.navigate(Screen.Dashboard.route) },
                onEcoChallengeClick = { /* Already here */ },
                onScanClick = { navController.navigate(Screen.Camera.route) },
                onNotificationClick = { navController.navigate(Screen.Notification.route) },
                onProfileClick = { navController.navigate(Screen.Profile.route) }
            )
        }
    }
}

class BaseViewModelFactory<T : ViewModel>(private val creator: () -> T) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return creator() as T
    }
}

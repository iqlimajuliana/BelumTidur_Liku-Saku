package com.example.tamproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.tamproject.data.api.RetrofitClient
import com.example.tamproject.data.repository.*
import com.example.tamproject.ui.navigation.AppNavGraph
import com.example.tamproject.ui.theme.TAMPROJECTTheme

class MainActivity : ComponentActivity() {
    private val authRepository = AuthRepository()
    private val pantryRepository = PantryRepository()
    private val challengeRepository = ChallengeRepository()
    private val wasteRepository by lazy { WasteRepository(applicationContext, RetrofitClient.imgBBApiService) }
    private val marketRepository by lazy { MarketRepository(applicationContext) }
    private val notificationRepository = NotificationRepository()
    private val pointsRepository = PointsRepository()
    private val profileRepository by lazy { ProfileRepository(applicationContext, RetrofitClient.imgBBApiService) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TAMPROJECTTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavGraph(
                        authRepository = authRepository,
                        pantryRepository = pantryRepository,
                        challengeRepository = challengeRepository,
                        wasteRepository = wasteRepository,
                        marketRepository = marketRepository,
                        notificationRepository = notificationRepository,
                        pointsRepository = pointsRepository,
                        profileRepository = profileRepository
                    )
                }
            }
        }
    }
}

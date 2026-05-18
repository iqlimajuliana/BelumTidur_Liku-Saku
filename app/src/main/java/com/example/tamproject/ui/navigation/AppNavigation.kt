package com.example.tamproject.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tamproject.ui.theme.MainGreen


@Composable
fun BottomNavigationBar(
    currentScreen: String,
    onHomeClick: () -> Unit,
    onEcoChallengeClick: () -> Unit,
    onWasteSortingClick: () -> Unit,
    onMyPointsClick: () -> Unit,
    onSmartPantryClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.height(80.dp)
    ) {
        val transparentColors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent,
            selectedIconColor = MainGreen,
            unselectedIconColor = Color.Gray,
            selectedTextColor = MainGreen,
            unselectedTextColor = Color.Gray
        )

        // 1. Home
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            selected = currentScreen == "dashboard",
            onClick = onHomeClick,
            colors = transparentColors
        )
        
        // 2. Eco Challenge (Icons.AutoMirrored.Filled.Assignment)
        NavigationBarItem(
            icon = { Icon(Icons.AutoMirrored.Filled.Assignment, contentDescription = "Eco Challenge") },
            selected = currentScreen == "eco_challenge",
            onClick = onEcoChallengeClick,
            colors = transparentColors
        )
        
        // 3. Scan (Tengah - Kosong/Tidak diklik)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(MainGreen),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.QrCodeScanner, contentDescription = "Scan", tint = Color.White)
            }
        }

        // 4. Notification (Bell Icon)
        NavigationBarItem(
            icon = { Icon(Icons.Default.Notifications, contentDescription = "Notification") },
            selected = currentScreen == "notification",
            onClick = onNotificationClick,
            colors = transparentColors
        )
        
        // 5. Profile (Person Icon)
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            selected = currentScreen == "profile",
            onClick = onProfileClick,
            colors = transparentColors
        )
    }
}

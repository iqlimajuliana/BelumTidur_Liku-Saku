package com.example.tamproject.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
    onSmartPantryClick: () -> Unit
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
        
        // 2. Eco Challenge
        NavigationBarItem(
            icon = { Icon(Icons.AutoMirrored.Filled.Assignment, contentDescription = "Eco Challenge") },
            selected = currentScreen == "eco_challenge",
            onClick = onEcoChallengeClick,
            colors = transparentColors
        )
        
        // 3. Waste Sorting
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable { onWasteSortingClick() },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(MainGreen),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.QrCodeScanner, contentDescription = "Waste Sorting", tint = Color.White)
            }
        }

        // 4. My Points
        NavigationBarItem(
            icon = { Icon(Icons.Default.Stars, contentDescription = "My Points") },
            selected = currentScreen == "my_points",
            onClick = onMyPointsClick,
            colors = transparentColors
        )
        
        // 5. Smart Pantry
        NavigationBarItem(
            icon = { Icon(Icons.Default.Kitchen, contentDescription = "Smart Pantry") },
            selected = currentScreen == "smart_pantry",
            onClick = onSmartPantryClick,
            colors = transparentColors
        )
    }
}

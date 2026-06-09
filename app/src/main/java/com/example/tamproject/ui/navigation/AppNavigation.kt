package com.example.tamproject.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.tamproject.ui.theme.Dimens
import com.example.tamproject.ui.theme.HijauEco

@Composable
fun BottomNavigationBar(
    currentScreen: String,
    onHomeClick: () -> Unit,
    onEcoChallengeClick: () -> Unit,
    onScanClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        color = Color.White,
        shadowElevation = 16.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val isHome = currentScreen == "dashboard" || currentScreen == "home"
            CustomNavItem(
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                isSelected = isHome,
                onClick = onHomeClick
            )

            CustomNavItem(
                selectedIcon = Icons.AutoMirrored.Filled.Assignment,
                unselectedIcon = Icons.Outlined.Assignment,
                isSelected = currentScreen == "eco_challenge",
                onClick = onEcoChallengeClick
            )

            // Central Scan Button
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(HijauEco)
                    .clickable { onScanClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.QrCodeScanner,
                    contentDescription = "Scan",
                    tint = Color.White,
                    modifier = Modifier.size(Dimens.IconMedium)
                )
            }

            CustomNavItem(
                selectedIcon = Icons.Filled.Notifications,
                unselectedIcon = Icons.Outlined.Notifications,
                isSelected = currentScreen == "notification",
                onClick = onNotificationClick
            )

            CustomNavItem(
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person,
                isSelected = currentScreen == "profile",
                onClick = onProfileClick
            )
        }
    }
}

@Composable
internal fun CustomNavItem(
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // Warna lebih gelap saat terpilih agar user tahu posisi menu mereka
    val iconColor = if (isSelected) Color(0xFF1B5E20) else Color(0xFF4CAF50).copy(alpha = 0.6f)
    val bgColor = if (isSelected) HijauEco.copy(alpha = 0.15f) else Color.Transparent

    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (isSelected) selectedIcon else unselectedIcon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(Dimens.IconMedium)
        )
    }
}

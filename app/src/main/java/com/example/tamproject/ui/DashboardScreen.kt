package com.example.tamproject.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tamproject.R
import com.example.tamproject.ui.theme.Black
import com.example.tamproject.ui.theme.MainGreen

@Composable
fun DashboardScreen(
    onSortingWasteClick: () -> Unit,
    onSmartPantryClick: () -> Unit,
    onEcoChallengeClick: () -> Unit,
    onRewardClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Scaffold(
        bottomBar = { 
            BottomNavigationBar(
                currentScreen = "dashboard",
                onHomeClick = { },
                onEcoChallengeClick = onEcoChallengeClick,
                onWasteSortingClick = onSortingWasteClick,
                onMyPointsClick = onRewardClick,
                onSmartPantryClick = onSmartPantryClick
            ) 
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Header: Profile & Greeting + Logout
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Good Morning, Dinda!", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Ready to manage waste better today?", fontSize = 12.sp, color = Color.Gray)
                }
                IconButton(onClick = onLogoutClick) {
                    Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout", tint = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Points Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Level 4 - Eco-Warriors! | 420 Points", fontSize = 12.sp, modifier = Modifier.weight(1f))
                        Image(
                            painter = painterResource(id = R.drawable.streak),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(" 25", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { 0.7f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = MainGreen,
                        trackColor = Color.LightGray,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Categories", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            
            Spacer(modifier = Modifier.height(16.dp))

            val categories = listOf(
                CategoryItem("Smart Pantry", Icons.Default.Kitchen),
                CategoryItem("Sorting Waste", Icons.Default.Recycling), 
                CategoryItem("Eco-Challenge", Icons.Default.Star),
                CategoryItem("Reward", Icons.Default.EmojiEvents),
                CategoryItem("Eco Waste Drop", Icons.Default.LocationOn),
                CategoryItem("Market", Icons.Default.ShoppingCart)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.height(240.dp)
            ) {
                items(categories) { item ->
                    CategoryCard(item) {
                        when (item.name) {
                            "Sorting Waste" -> onSortingWasteClick()
                            "Smart Pantry" -> onSmartPantryClick()
                            "Eco-Challenge" -> onEcoChallengeClick()
                            "Reward" -> onRewardClick()
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Eco-News", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.weight(1f))
                TextButton(onClick = {}) {
                    Text("Read More", color = MainGreen)
                }
            }

            // News Card Placeholder
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.dashboard),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.3f))
                    )
                    Text(
                        "KLH-BPLH Tegaskan Arah Baru Menuju Indonesia Bebas Sampah 2029",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(12.dp)
                    )
                }
            }
        }
    }
}

data class CategoryItem(val name: String, val icon: ImageVector)

@Composable
fun CategoryCard(item: CategoryItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(item.icon, contentDescription = null, tint = MainGreen, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(item.name, fontSize = 10.sp, fontWeight = FontWeight.Bold, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
    }
}

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
            unselectedIconColor = Color.Gray
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            selected = currentScreen == "dashboard",
            onClick = onHomeClick,
            colors = transparentColors
        )
        NavigationBarItem(
            icon = { Icon(Icons.AutoMirrored.Filled.Assignment, contentDescription = null) },
            selected = currentScreen == "eco_challenge",
            onClick = onEcoChallengeClick,
            colors = transparentColors
        )
        
        // Middle Button: Waste Sorting
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
                Icon(Icons.Default.QrCodeScanner, contentDescription = null, tint = Color.White)
            }
        }

        NavigationBarItem(
            icon = { Icon(Icons.Default.Stars, contentDescription = null) },
            selected = currentScreen == "my_points",
            onClick = onMyPointsClick,
            colors = transparentColors
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Kitchen, contentDescription = null) },
            selected = currentScreen == "smart_pantry",
            onClick = onSmartPantryClick,
            colors = transparentColors
        )
    }
}

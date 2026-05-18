package com.example.tamproject.ui.screen.fitur

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tamproject.R
import com.example.tamproject.ui.navigation.BottomNavigationBar
import com.example.tamproject.ui.theme.MainGreen

@Composable
fun ProfileScreen(
    onHomeClick: () -> Unit,
    onEcoChallengeClick: () -> Unit,
    onWasteSortingClick: () -> Unit,
    onMyPointsClick: () -> Unit,
    onSmartPantryClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentScreen = "profile",
                onHomeClick = onHomeClick,
                onEcoChallengeClick = onEcoChallengeClick,
                onWasteSortingClick = onWasteSortingClick,
                onMyPointsClick = onMyPointsClick,
                onSmartPantryClick = onSmartPantryClick,
                onNotificationClick = onNotificationClick,
                onProfileClick = onProfileClick
            )
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .padding(bottom = 60.dp, end = 10.dp)
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(1.dp, MainGreen, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Chat, contentDescription = null, tint = MainGreen)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Header Background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MainGreen),
                contentAlignment = Alignment.BottomCenter
            ) {
                // Circular Profile Image with + button
                Box(
                    modifier = Modifier
                        .offset(y = 50.dp)
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .border(4.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(48.dp), tint = Color.White)
                }
            }
            
            Spacer(modifier = Modifier.height(60.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Dinda Adria", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(20.dp))
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Public, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(color = Color(0xFFEFEBE9), shape = RoundedCornerShape(12.dp)) {
                        Text(text = "Level 4", modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp), fontSize = 12.sp)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(color = Color(0xFFE8F5E9), shape = RoundedCornerShape(12.dp)) {
                        Text(text = "12 badges", modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp), fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Stats Row
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatCard("2500", "Total XP", Color(0xFFFFF9C4), Modifier.weight(1f))
                    StatCard("25", "Day Streak", Color(0xFFFFCCBC), Modifier.weight(1f))
                    StatCard("5", "Badges", Color(0xFFC8E6C9), Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(32.dp))
                
                Text(text = "Recent Achievements", fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start))
                
                Spacer(modifier = Modifier.height(12.dp))
                
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                    items(achievements) { achievement ->
                        AchievementIcon(achievement)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
                
                Text(text = "Settings", fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start))
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Settings - Notifications toggle
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFFEFEBE9)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Notifications, contentDescription = null)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Notifications", fontWeight = FontWeight.Bold)
                            Text(text = "Push notifications and reminders", fontSize = 11.sp, color = Color.Gray)
                        }
                        Switch(checked = true, onCheckedChange = {})
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(value: String, label: String, color: Color, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = value, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = label, fontSize = 11.sp)
        }
    }
}

data class Achievement(val icon: androidx.compose.ui.graphics.vector.ImageVector, val label: String)

val achievements = listOf(
    Achievement(Icons.Default.Whatshot, "7-Day Streak"),
    Achievement(Icons.Default.Recycling, "Starter Recycler"),
    Achievement(Icons.Default.Eco, "Eco Enthusiast"),
    Achievement(Icons.Default.Gavel, "Glass Guardian"), // Placeholder
    Achievement(Icons.Default.EmojiEvents, "Zero Waste Champion")
)

@Composable
fun AchievementIcon(item: Achievement) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(70.dp)) {
        Box(
            modifier = Modifier.size(50.dp).border(1.dp, Color.LightGray, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(item.icon, contentDescription = null, tint = MainGreen, modifier = Modifier.size(30.dp))
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = item.label, fontSize = 9.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center, lineHeight = 10.sp)
    }
}

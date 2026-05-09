package com.example.tamproject.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tamproject.R
import com.example.tamproject.ui.theme.MainGreen
import com.example.tamproject.ui.components.BottomNavigationBar
@Composable
fun EcoChallengeScreen(
    onHomeClick: () -> Unit,
    onEcoChallengeClick: () -> Unit,
    onWasteSortingClick: () -> Unit,
    onMyPointsClick: () -> Unit,
    onSmartPantryClick: () -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentScreen = "eco_challenge",
                onHomeClick = onHomeClick,
                onEcoChallengeClick = onEcoChallengeClick,
                onWasteSortingClick = onWasteSortingClick,
                onMyPointsClick = onMyPointsClick,
                onSmartPantryClick = onSmartPantryClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Eco Challenges",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Image(
                    painter = painterResource(id = R.drawable.streak),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Text(" 25", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // User Progress Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MainGreen)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Dinda Adria", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow, modifier = Modifier.size(32.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("420", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    // Simple Progress Circle
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            progress = { 0.7f },
                            modifier = Modifier.size(80.dp),
                            color = Color.Yellow,
                            strokeWidth = 8.dp,
                            trackColor = Color.White.copy(alpha = 0.3f)
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Level", color = Color.White, fontSize = 10.sp)
                            Text("4", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Li-ku's Month", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                ChallengeCard("Zero-waste day", "24 hours zero waste", Modifier.weight(1f))
                ChallengeCard("Plant a tree", "From waste to soil", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Weekly Mission", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(missions) { mission ->
                    MissionCard(mission)
                }
            }
        }
    }
}

@Composable
fun ChallengeCard(title: String, desc: String, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(1f))
                Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = MainGreen, modifier = Modifier.size(20.dp))
            }
            Text(desc, fontSize = 10.sp, color = Color.Gray, modifier = Modifier.padding(vertical = 4.dp))
            Button(
                onClick = {},
                modifier = Modifier.height(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MainGreen),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
            ) {
                Text("Join challenge", fontSize = 9.sp)
            }
        }
    }
}

data class Mission(val title: String, val xp: String, val p: String, val progress: Float, val count: String)

val missions = listOf(
    Mission("Compost food scraps", "25 XP", "200 P", 0.4f, "1/3"),
    Mission("Go plastic-free for a week", "30 XP", "350 P", 0.8f, "6/7"),
    Mission("Try recipes made from food leftovers", "10 XP", "170 P", 0.3f, "1/3")
)

@Composable
fun MissionCard(mission: Mission) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Security, contentDescription = null, tint = Color.Yellow, modifier = Modifier.size(16.dp))
                Text(mission.xp, fontSize = 9.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow, modifier = Modifier.size(16.dp))
                Text(mission.p, fontSize = 9.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(mission.title, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { mission.progress },
                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
                    color = Color(0xFFFFA500),
                    trackColor = Color.LightGray
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier.width(40.dp).height(20.dp).clip(CircleShape).background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text(mission.count, fontSize = 9.sp)
            }
        }
    }
}
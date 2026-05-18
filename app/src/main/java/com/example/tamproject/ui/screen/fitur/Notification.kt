package com.example.tamproject.ui.screen.fitur

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Chat
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
fun NotificationScreen(
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
                currentScreen = "notification",
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
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Notification",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(notifications) { item ->
                    NotificationCard(item)
                }
            }
        }
    }
}

data class NotificationItem(val icon: Int, val message: String)

val notifications = listOf(
    NotificationItem(R.drawable.vector, "🍅 Your tomatoes are about to expire in 2 days. Time to cook something delicious with them!"),
    NotificationItem(R.drawable.vector, "👨‍🍳 You still have tofu and spinach. Why not try the Stir-fried Tofu and Spinach recipe today!"),
    NotificationItem(R.drawable.vector, "🌡 Your 'Backyard Compost' dropped to 18°C. Check the spot — it might need adjusting!"),
    NotificationItem(R.drawable.vector, "💪 Today's Challenge: Go single-use plastic free! Earn 30 EXP + 350 Points."),
    NotificationItem(R.drawable.vector, "📦 An e-waste event is happening this Saturday at UNILA Waste Bank. Be sure to check it out!"),
    NotificationItem(R.drawable.vector, "♻ Botol PET bisa kamu daur ulang di dropbox terdekat. Cek lokasi sekarang!")
)

@Composable
fun NotificationCard(item: NotificationItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MainGreen, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MainGreen),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = item.icon),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp),
                    contentScale = ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = item.message,
                fontSize = 12.sp,
                color = Color.Black,
                modifier = Modifier.weight(1f),
                lineHeight = 16.sp
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}

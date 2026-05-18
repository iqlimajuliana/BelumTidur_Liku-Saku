package com.example.tamproject.ui.screen.fitur

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun EcoWasteDropScreen(
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
                currentScreen = "",
                onHomeClick = onHomeClick,
                onEcoChallengeClick = onEcoChallengeClick,
                onWasteSortingClick = onWasteSortingClick,
                onMyPointsClick = onMyPointsClick,
                onSmartPantryClick = onSmartPantryClick,
                onNotificationClick = onNotificationClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Image(
                painter = painterResource(id = R.drawable.gmaps),
                contentDescription = "Map",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f),
                contentScale = ContentScale.Crop
            )

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f)
                    .offset(y = (-20).dp),
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "Eco waste drop terdekat",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(ecoDrops) { item ->
                            EcoDropCard(item)
                        }
                    }
                }
            }
        }
    }
}

data class EcoDrop(val name: String, val distance: String, val status: String, val imageRes: Int)

val ecoDrops = listOf(
    EcoDrop("PT Angkut Aja", "2 KM", "Open Monday - Saturday", R.drawable.inorganic),
    EcoDrop("PT Raja abas", "5,7 KM", "Closed", R.drawable.inorganic),
    EcoDrop("PT Raja abas", "5,7 KM", "Closed", R.drawable.inorganic)
)

@Composable
fun EcoDropCard(item: EcoDrop) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, MainGreen, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(60.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name, 
                    fontWeight = FontWeight.Bold, 
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = item.distance, 
                    fontSize = 12.sp,
                    color = Color.Black
                )
                Text(
                    text = item.status, 
                    fontSize = 12.sp, 
                    color = Color.Gray
                )
            }
            
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Detail",
                tint = Color.Black,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

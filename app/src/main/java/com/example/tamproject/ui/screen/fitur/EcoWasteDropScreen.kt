package com.example.tamproject.ui.screen.fitur

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tamproject.R
import com.example.tamproject.data.model.EcoDrop
import com.example.tamproject.ui.navigation.BottomNavigationBar
import com.example.tamproject.ui.theme.*
import com.example.tamproject.ui.viewmodel.WasteViewModel

@Composable
fun EcoWasteDropScreen(
    viewModel: WasteViewModel,
    onHomeClick: () -> Unit,
    onEcoChallengeClick: () -> Unit,
    onScanClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val ecoDrops by viewModel.ecoDrops.collectAsState(initial = null)

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            BottomNavigationBar(
                currentScreen = "home",
                onHomeClick = onHomeClick,
                onEcoChallengeClick = onEcoChallengeClick,
                onScanClick = onScanClick,
                onNotificationClick = onNotificationClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            // Background Map Section
            Image(
                painter = painterResource(id = R.drawable.gmaps),
                contentDescription = "Map Background",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                contentScale = ContentScale.Crop
            )

            // Content Card with Rounded Corners (Overlapping Map)
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 220.dp),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Eco waste drop terdekat",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        modifier = Modifier.padding(Dimens.PaddingLarge),
                        fontWeight = FontWeight.Bold
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        if (ecoDrops == null) {
                            item {
                                Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator(color = HijauEco)
                                }
                            }
                        } else {
                            items(ecoDrops!!) { item ->
                                EcoDropItemCard(item)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EcoDropItemCard(item: EcoDrop) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.PaddingLarge, vertical = Dimens.PaddingSmall)
            .shadow(
                elevation = 2.dp, 
                shape = RoundedCornerShape(Dimens.CornerRadiusLarge), 
                ambientColor = Color.LightGray
            ),
        shape = RoundedCornerShape(Dimens.CornerRadiusLarge),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, HijauEco)
    ) {
        Row(
            modifier = Modifier.padding(Dimens.PaddingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Location Icon (using gmaps drawable as requested)
            Image(
                painter = painterResource(id = R.drawable.gmaps),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(Dimens.CornerRadiusSmall)),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.width(Dimens.PaddingMedium))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name, 
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = item.distance, 
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = item.status, 
                    style = MaterialTheme.typography.bodySmall, 
                    color = Color.Black
                )
            }
            
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Info",
                    tint = Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

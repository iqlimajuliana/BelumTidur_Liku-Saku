package com.example.tamproject.ui.screen.fitur

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tamproject.R
import com.example.tamproject.data.model.Redemption
import com.example.tamproject.ui.navigation.BottomNavigationBar
import com.example.tamproject.ui.theme.*
import com.example.tamproject.ui.viewmodel.PointsViewModel

@Composable
fun MyPointsScreen(
    viewModel: PointsViewModel,
    onHomeClick: () -> Unit,
    onEcoChallengeClick: () -> Unit,
    onScanClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit,
    onEcoWasteDropClick: () -> Unit
) {
    val redemptions by viewModel.redemptions.collectAsState()
    val userData by viewModel.userData.collectAsState(initial = null)

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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = Dimens.PaddingLarge)
        ) {
            item {
                Spacer(modifier = Modifier.height(Dimens.PaddingLarge))
                Text(
                    text = "MyPoints",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(Dimens.PaddingMedium))
            }

            // Green QR & Points Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(Dimens.CornerRadiusLarge), ambientColor = Color.LightGray),
                    shape = RoundedCornerShape(Dimens.CornerRadiusLarge),
                    colors = CardDefaults.cardColors(containerColor = HijauEco)
                ) {
                    Column(modifier = Modifier.padding(Dimens.PaddingLarge)) {
                        Text(
                            text = "Visit the nearest Eco-waste Drop and redeem your points by showing the QR code!",
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().padding(bottom = Dimens.PaddingMedium)
                        )
                        Row(modifier = Modifier.fillMaxWidth()) {
                            // Column for Points and QR
                            Column(modifier = Modifier.weight(0.4f)) {
                                Card(
                                    shape = RoundedCornerShape(Dimens.CornerRadiusMedium),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier.padding(8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text("Points", fontSize = 10.sp, color = Color.Black)
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Image(painter = painterResource(id = R.drawable.streak), contentDescription = null, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = "${userData?.points ?: 420}",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp,
                                                color = Color.Black
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(Dimens.PaddingSmall))
                                Card(
                                    shape = RoundedCornerShape(Dimens.CornerRadiusMedium),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier.padding(8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text("scan", fontSize = 10.sp, color = Color.Black)
                                        Image(
                                            painter = painterResource(id = R.drawable.qr),
                                            contentDescription = "QR",
                                            modifier = Modifier.size(40.dp)
                                        )
                                    }
                                }
                            }
                            
                            Spacer(modifier = Modifier.width(Dimens.PaddingMedium))
                            
                            // Map Preview
                            Box(
                                modifier = Modifier
                                    .weight(0.6f)
                                    .height(115.dp)
                                    .clip(RoundedCornerShape(Dimens.CornerRadiusMedium))
                                    .clickable { onEcoWasteDropClick() }
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.gmaps),
                                    contentDescription = "Map",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(8.dp)
                                        .size(32.dp)
                                        .background(HijauEco, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Map, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(Dimens.PaddingLarge))
                Text(
                    text = "Redemption history",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(Dimens.PaddingMedium))
            }

            if (redemptions.isEmpty()) {
                item {
                    Text("No redemption history yet", color = AbuAbuText, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                }
            } else {
                items(redemptions) { item ->
                    RedemptionHistoryCard(item)
                }
            }
            
            item { Spacer(modifier = Modifier.height(Dimens.PaddingExtraLarge)) }
        }
    }
}

@Composable
fun RedemptionHistoryCard(item: Redemption) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.PaddingSmall)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(Dimens.CornerRadiusLarge), ambientColor = Color.LightGray),
        shape = RoundedCornerShape(Dimens.CornerRadiusLarge),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, HijauEco)
    ) {
        Row(
            modifier = Modifier.padding(Dimens.PaddingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.reward),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(Dimens.PaddingMedium))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.title, style = MaterialTheme.typography.bodyMedium, color = Color.Black, fontWeight = FontWeight.Medium)
                Text(item.date, style = MaterialTheme.typography.bodySmall, color = Color.Black)
            }
            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(item.location, fontSize = 8.sp, color = Color.Black)
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color(0xFF5C6BC0), modifier = Modifier.size(10.dp))
                }
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    color = HijauEco,
                    shape = RoundedCornerShape(Dimens.CornerRadiusExtraLarge)
                ) {
                    Text(
                        text = item.points,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

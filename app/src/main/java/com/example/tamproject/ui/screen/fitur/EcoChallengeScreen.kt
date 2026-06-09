package com.example.tamproject.ui.screen.fitur

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tamproject.R
import com.example.tamproject.data.model.Mission
import com.example.tamproject.ui.navigation.BottomNavigationBar
import com.example.tamproject.ui.theme.*
import com.example.tamproject.ui.viewmodel.ChallengeViewModel

@Composable
fun EcoChallengeScreen(
    viewModel: ChallengeViewModel,
    onHomeClick: () -> Unit,
    onEcoChallengeClick: () -> Unit,
    onScanClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val missions by viewModel.missions.collectAsState()
    val userData by viewModel.userData.collectAsState(initial = null)
    val claimState by viewModel.claimState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(claimState) {
        claimState?.let { result ->
            if (result.isSuccess) {
                Toast.makeText(context, "Points added to your profile!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to claim: ${result.exceptionOrNull()?.message}", Toast.LENGTH_SHORT).show()
            }
            viewModel.resetClaimState()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        bottomBar = {
            BottomNavigationBar(
                currentScreen = "eco_challenge",
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
        ) {
            // Header Section
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.PaddingLarge, vertical = Dimens.PaddingLarge),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Eco Challenges",
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.streak),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${userData?.streak ?: 0}",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.Black
                        )
                    }
                }
            }

            // Points & Level Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.PaddingLarge)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(Dimens.CornerRadiusLarge), ambientColor = Color.LightGray),
                    shape = RoundedCornerShape(Dimens.CornerRadiusLarge),
                    colors = CardDefaults.cardColors(containerColor = HijauEco)
                ) {
                    Row(
                        modifier = Modifier.padding(Dimens.PaddingLarge),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = userData?.fullName ?: "User",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(Dimens.PaddingSmall))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.MonetizationOn,
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp),
                                    tint = KuningPoint
                                )
                                Spacer(modifier = Modifier.width(Dimens.PaddingSmall))
                                Text(
                                    text = "${userData?.points ?: 0}",
                                    style = MaterialTheme.typography.displayLarge,
                                    color = Color.White,
                                    fontSize = 40.sp
                                )
                            }
                        }

                        Box(contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(
                                progress = { 0.75f },
                                modifier = Modifier.size(100.dp),
                                color = KuningPoint,
                                strokeWidth = 10.dp,
                                trackColor = HijauEco.copy(alpha = 0.3f),
                                strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Level", color = Color.White, fontSize = 12.sp)
                                Text(
                                    text = "${userData?.level ?: 1}",
                                    color = Color.White,
                                    style = MaterialTheme.typography.displayLarge,
                                    fontSize = 28.sp
                                )
                            }
                        }
                    }
                }
            }

            // Li-ku's Month
            item {
                Spacer(modifier = Modifier.height(Dimens.PaddingLarge))
                Text(
                    text = "Li-ku's Month",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = Dimens.PaddingLarge),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(Dimens.PaddingMedium))
                
                LazyRow(
                    contentPadding = PaddingValues(horizontal = Dimens.PaddingLarge),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium)
                ) {
                    item {
                        ChallengeItemCard(
                            title = "Zero-waste day",
                            desc = "24 hours zero waste,\na lifetime for Earth to taste!",
                            iconRes = R.drawable.vector
                        )
                    }
                    item {
                        ChallengeItemCard(
                            title = "Plant a tree",
                            desc = "From waste to fertile soil,\nLet's plant trees together!",
                            iconRes = R.drawable.vector
                        )
                    }
                }
            }

            // Weekly Mission
            item {
                Spacer(modifier = Modifier.height(Dimens.PaddingLarge))
                Text(
                    text = "Weekly Mission",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = Dimens.PaddingLarge),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(Dimens.PaddingSmall))
            }

            if (missions.isEmpty()) {
                item {
                    Text(
                        text = "No weekly missions available",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AbuAbuText,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimens.PaddingExtraLarge)
                    )
                }
            } else {
                items(missions) { mission ->
                    MissionItemCard(
                        mission = mission,
                        onClaimClick = { viewModel.claimMission(mission) }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(Dimens.PaddingExtraLarge)) }
        }
    }
}

@Composable
fun ChallengeItemCard(title: String, desc: String, iconRes: Int) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(160.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(Dimens.CornerRadiusLarge), ambientColor = Color.LightGray),
        shape = RoundedCornerShape(Dimens.CornerRadiusLarge),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFEEEEEE))
    ) {
        Column(modifier = Modifier.padding(Dimens.PaddingMedium)) {
            Row(verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(title, style = MaterialTheme.typography.titleLarge, color = Color.Black, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(desc, style = MaterialTheme.typography.bodySmall, color = Color.Black, lineHeight = 14.sp)
                }
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = { },
                    modifier = Modifier.height(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = HijauEco),
                    shape = RoundedCornerShape(Dimens.CornerRadiusSmall),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                ) {
                    Text("Join challenge", fontSize = 10.sp, color = Color.White)
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.Black)
            }
        }
    }
}

@Composable
fun MissionItemCard(mission: Mission, onClaimClick: () -> Unit) {
    val isCompleted = mission.progress >= 1f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.PaddingLarge, vertical = Dimens.PaddingSmall)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(Dimens.CornerRadiusLarge), ambientColor = Color.LightGray),
        shape = RoundedCornerShape(Dimens.CornerRadiusLarge),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFEEEEEE))
    ) {
        Row(
            modifier = Modifier.padding(Dimens.PaddingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = "XP Icon",
                    modifier = Modifier.size(18.dp),
                    tint = Color(0xFF4CAF50)
                )
                Text("25 XP", style = MaterialTheme.typography.bodySmall, fontSize = 8.sp, color = Color.Black)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.MonetizationOn,
                    contentDescription = "Point Icon",
                    modifier = Modifier.size(18.dp),
                    tint = KuningPoint
                )
                Text(mission.p, style = MaterialTheme.typography.bodySmall, fontSize = 8.sp, color = Color.Black)
            }
            
            Spacer(modifier = Modifier.width(Dimens.PaddingMedium))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(mission.title, style = MaterialTheme.typography.bodyMedium, color = Color.Black, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { mission.progress },
                    modifier = Modifier.fillMaxWidth().height(10.dp).clip(CircleShape),
                    color = Color(0xFFFFA000),
                    trackColor = Color(0xFFEEEEEE),
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
            }
            
            Spacer(modifier = Modifier.width(Dimens.PaddingMedium))

            if (isCompleted) {
                IconButton(onClick = onClaimClick) {
                    Image(painter = painterResource(id = R.drawable.reward), contentDescription = null, modifier = Modifier.size(32.dp))
                }
            } else {
                Text(mission.count, style = MaterialTheme.typography.bodySmall, color = AbuAbuText)
            }
        }
    }
}

package com.example.tamproject.ui.screen.fitur

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tamproject.R
import com.example.tamproject.data.model.Achievement
import com.example.tamproject.ui.navigation.BottomNavigationBar
import com.example.tamproject.ui.theme.*
import com.example.tamproject.ui.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onHomeClick: () -> Unit,
    onEcoChallengeClick: () -> Unit,
    onScanClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val userData by viewModel.userData.collectAsState(initial = null)
    val achievements by viewModel.achievements.collectAsState(initial = emptyList())
    
    var showEditNameDialog by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf("") }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            userData?.userId?.let { uid ->
                viewModel.updateProfile(uid, userData?.fullName ?: "", it)
            }
        }
    }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            BottomNavigationBar(
                currentScreen = "profile",
                onHomeClick = onHomeClick,
                onEcoChallengeClick = onEcoChallengeClick,
                onScanClick = onScanClick,
                onNotificationClick = onNotificationClick,
                onProfileClick = onProfileClick
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = Color.White,
                contentColor = HijauEco,
                shape = CircleShape,
                modifier = Modifier
                    .padding(bottom = 60.dp)
                    .size(56.dp)
                    .shadow(elevation = 6.dp, shape = CircleShape)
                    .border(1.dp, HijauEco, CircleShape)
            ) {
                Icon(Icons.Outlined.ChatBubbleOutline, contentDescription = "Chat")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Top Green Background & Profile Header
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(HijauEco),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    // White rounded overlay at bottom of green area
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .background(
                                Color.White,
                                RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                            )
                    )
                    
                    // Profile Picture with Plus Button
                    Box(
                        modifier = Modifier
                            .offset(y = (-10).dp)
                            .size(140.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE0E0E0))
                            .border(6.dp, Color.White, CircleShape)
                            .clickable { photoPickerLauncher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(userData?.profilePictureUrl.takeIf { !it.isNullOrBlank() } ?: R.drawable.profile)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Profile",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add Photo",
                            tint = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    // Shop Icon Top Right
                    Icon(
                        Icons.Outlined.Storefront,
                        contentDescription = "Store",
                        tint = HijauEco,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(bottom = 80.dp, end = 24.dp)
                            .size(32.dp)
                            .clickable { }
                    )
                }
            }

            // User Info Section
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { 
                            newName = userData?.fullName ?: ""
                            showEditNameDialog = true 
                        }
                    ) {
                        Text(
                            text = userData?.fullName ?: "User Name",
                            style = MaterialTheme.typography.displayLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp
                            ),
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Default.Edit, contentDescription = "Edit Name", tint = Color.Gray, modifier = Modifier.size(20.dp))
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Language, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Surface(
                            color = Color(0xFFEBD9B4),
                            shape = RoundedCornerShape(Dimens.CornerRadiusMedium)
                        ) {
                            Text(
                                "Level ${userData?.level ?: 1}",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                                fontSize = 12.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            color = Color(0xFFC1E1C1),
                            shape = RoundedCornerShape(Dimens.CornerRadiusMedium)
                        ) {
                            Text(
                                "12 badges",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                                fontSize = 12.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Stats Cards Row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.PaddingLarge),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium)
                ) {
                    ProfileStatCard(
                        value = "${userData?.points ?: 0}",
                        label = "Points",
                        gradient = Brush.verticalGradient(listOf(Color(0xFFFFF4D2), Color(0xFFF5E6BD))),
                        modifier = Modifier.weight(1f)
                    )
                    ProfileStatCard(
                        value = "${userData?.streak ?: 0}",
                        label = "Streak",
                        gradient = Brush.verticalGradient(listOf(Color(0xFFFFE4E1), Color(0xFFFDCFD8))),
                        modifier = Modifier.weight(1f)
                    )
                    ProfileStatCard(
                        value = "5",
                        label = "Badges",
                        gradient = Brush.verticalGradient(listOf(Color(0xFFE0F2F1), Color(0xFFB2DFDB))),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Recent Achievements Section
            item {
                Text(
                    text = "Recent Achievements",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = Dimens.PaddingLarge),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(Dimens.PaddingMedium))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = Dimens.PaddingLarge),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium)
                ) {
                    val sampleBadges = listOf(
                        "7 - Day\nStreak" to R.drawable.streak,
                        "Starter\nRecycler" to R.drawable.vector,
                        "Eco\nEnthusiast" to R.drawable.vector,
                        "Glass\nGuardian" to R.drawable.vector,
                        "Zero Waste\nChampion" to R.drawable.reward
                    )
                    items(sampleBadges) { badge ->
                        AchievementBadgeCard(badge.first, badge.second)
                    }
                }
            }

            // Settings Section
            item {
                Spacer(modifier = Modifier.height(Dimens.PaddingLarge))
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = Dimens.PaddingLarge),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(Dimens.PaddingMedium))
                
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.PaddingLarge)
                        .shadow(elevation = 2.dp, shape = RoundedCornerShape(Dimens.CornerRadiusLarge)),
                    shape = RoundedCornerShape(Dimens.CornerRadiusLarge),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFF5F5F5))
                ) {
                    Row(
                        modifier = Modifier.padding(Dimens.PaddingMedium),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = Color(0xFFF5E6BD),
                            shape = RoundedCornerShape(Dimens.CornerRadiusMedium),
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.Black, modifier = Modifier.padding(12.dp))
                        }
                        Spacer(modifier = Modifier.width(Dimens.PaddingMedium))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Notifications", style = MaterialTheme.typography.bodyLarge, color = Color.Black, fontWeight = FontWeight.Bold)
                            Text("Push notifications and reminders", fontSize = 10.sp, color = Color.Gray)
                        }
                        Switch(
                            checked = true,
                            onCheckedChange = { },
                            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = HijauEco)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(Dimens.PaddingExtraLarge))
            }
        }
    }

    if (showEditNameDialog) {
        AlertDialog(
            onDismissRequest = { showEditNameDialog = false },
            title = { Text("Edit Name") },
            text = {
                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text("Full Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        userData?.userId?.let { uid ->
                            viewModel.updateProfile(uid, newName, null)
                        }
                        showEditNameDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = HijauEco)
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditNameDialog = false }) {
                    Text("Cancel", color = Color.Gray)
                }
            }
        )
    }
}

@Composable
fun ProfileStatCard(value: String, label: String, gradient: Brush, modifier: Modifier) {
    Card(
        modifier = modifier.height(80.dp),
        shape = RoundedCornerShape(Dimens.CornerRadiusMedium)
    ) {
        Box(modifier = Modifier.fillMaxSize().background(gradient), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = value, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold), color = Color.Black, fontSize = 22.sp)
                Text(text = label, style = MaterialTheme.typography.bodySmall, color = Color.DarkGray, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun AchievementBadgeCard(title: String, iconRes: Int) {
    Card(
        modifier = Modifier
            .width(90.dp)
            .height(115.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(Dimens.CornerRadiusLarge)),
        shape = RoundedCornerShape(Dimens.CornerRadiusLarge),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = iconRes), 
                contentDescription = null, 
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title, 
                fontSize = 10.sp, 
                textAlign = TextAlign.Center, 
                color = Color.Black, 
                lineHeight = 12.sp, 
                fontWeight = FontWeight.Bold
            )
        }
    }
}

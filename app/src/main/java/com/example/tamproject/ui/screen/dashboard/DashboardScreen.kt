package com.example.tamproject.ui.screen.dashboard

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.tamproject.ui.navigation.BottomNavigationBar
import com.example.tamproject.ui.theme.Dimens
import com.example.tamproject.ui.theme.HijauEco
import com.example.tamproject.ui.theme.AbuAbuText
import com.example.tamproject.ui.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onHomeClick: () -> Unit,
    onEcoChallengeClick: () -> Unit,
    onWasteSortingClick: () -> Unit,
    onScanClick: () -> Unit,
    onMyPointsClick: () -> Unit,
    onSmartPantryClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit,
    onEcoWasteDropClick: () -> Unit,
    onMarketClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val userData by viewModel.userData.collectAsState(initial = null)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
        ) {
            // Header Section
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.PaddingLarge, vertical = Dimens.PaddingLarge),
                    verticalAlignment = Alignment.Top
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(userData?.profilePictureUrl.takeIf { !it.isNullOrBlank() } ?: R.drawable.profile)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .clickable { onProfileClick() },
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.profile),
                        placeholder = painterResource(R.drawable.profile)
                    )
                    Spacer(modifier = Modifier.width(Dimens.PaddingMedium))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Good Morning, ${userData?.fullName ?: "User"}!",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            ),
                            color = Color.Black
                        )
                        Text(
                            text = "Ready to manage waste better\ntoday?",
                            style = MaterialTheme.typography.bodyMedium,
                            color = AbuAbuText,
                            lineHeight = 20.sp
                        )
                    }
                    IconButton(onClick = onLogoutClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.Black
                        )
                    }
                }
            }

            // Level & Points Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.PaddingLarge)
                        .shadow(elevation = 10.dp, shape = RoundedCornerShape(Dimens.CornerRadiusLarge), ambientColor = Color.LightGray),
                    shape = RoundedCornerShape(Dimens.CornerRadiusLarge),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFEEEEEE))
                ) {
                    Column(modifier = Modifier.padding(Dimens.PaddingMedium)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Level ${userData?.level ?: 1} - Eco-Warriors! | ${userData?.points ?: 0} Points",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black,
                                modifier = Modifier.weight(1f)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.streak), // Fire icon
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${userData?.streak ?: 0}",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.height(Dimens.PaddingSmall))
                        
                        val progressValue = 0.65f // Placeholder logic
                        LinearProgressIndicator(
                            progress = { progressValue },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .clip(CircleShape),
                            color = HijauEco,
                            trackColor = Color(0xFFE0E0E0),
                        )
                    }
                }
            }

            // Categories Grid
            item {
                Column(modifier = Modifier.padding(top = Dimens.PaddingLarge)) {
                    Text(
                        text = "Categories",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = Dimens.PaddingLarge),
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

                    val categories = listOf(
                        CategoryData("Smart\nPantry", Icons.Default.Kitchen, "smart_pantry"),
                        CategoryData("Sorting\nWaste", Icons.Default.Recycling, "waste_sorting"),
                        CategoryData("Eco-\nChallenge", Icons.Default.Star, "eco_challenge"),
                        CategoryData("Reward", Icons.Default.EmojiEvents, "reward"),
                        CategoryData("Eco Waste\nDrop", Icons.Default.LocationOn, "eco_waste_drop"),
                        CategoryData("Market", Icons.Default.ShoppingCart, "market")
                    )

                    Column(
                        modifier = Modifier.padding(horizontal = Dimens.PaddingLarge),
                        verticalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium)
                    ) {
                        for (row in 0 until 2) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium)
                            ) {
                                for (col in 0 until 3) {
                                    val index = row * 3 + col
                                    if (index < categories.size) {
                                        val item = categories[index]
                                        CategoryCard(item, Modifier.weight(1f)) {
                                            when (item.id) {
                                                "smart_pantry" -> onSmartPantryClick()
                                                "waste_sorting" -> onWasteSortingClick()
                                                "eco_challenge" -> onEcoChallengeClick()
                                                "reward" -> onMyPointsClick()
                                                "eco_waste_drop" -> onEcoWasteDropClick()
                                                "market" -> onMarketClick()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Eco-News Section
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.PaddingLarge, vertical = Dimens.PaddingLarge),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Eco-News",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color.Black
                        )
                    }
                    Text(
                        text = "Read More",
                        color = HijauEco,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable { }
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = Dimens.PaddingLarge),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium),
                    modifier = Modifier.padding(bottom = Dimens.PaddingLarge)
                ) {
                    item {
                        NewsCard(
                            imageRes = R.drawable.dashboard,
                            title = "KLH-BPLH Tegaskan Arah Baru Menuju Indonesia Bebas Sampah 2029"
                        )
                    }
                    item {
                        NewsCard(
                            imageRes = R.drawable.dashboard,
                            title = "Penanganan Sampah Plastik di Laut Menjadi Prioritas Utama"
                        )
                    }
                }
            }
        }
    }
}

data class CategoryData(val name: String, val icon: ImageVector, val id: String)

@Composable
fun CategoryCard(item: CategoryData, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .defaultMinSize(minHeight = 100.dp)
            .clickable { onClick() }
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(Dimens.CornerRadiusLarge), ambientColor = Color.LightGray),
        shape = RoundedCornerShape(Dimens.CornerRadiusLarge),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFF0F0F0))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                modifier = Modifier.size(36.dp),
                tint = HijauEco
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.name,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 16.sp,
                    textAlign = TextAlign.Center
                ),
                color = Color.Black
            )
        }
    }
}

@Composable
fun NewsCard(imageRes: Int, title: String) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(180.dp),
        shape = RoundedCornerShape(Dimens.CornerRadiusLarge),
    ) {
        Box {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, HijauEco.copy(alpha = 0.9f)),
                            startY = 100f
                        )
                    )
            )
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(Dimens.PaddingMedium),
                fontSize = 14.sp,
                lineHeight = 18.sp
            )
        }
    }
}

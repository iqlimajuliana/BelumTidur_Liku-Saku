package com.example.tamproject.ui.screen.fitur

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import com.example.tamproject.data.model.SortingItem
import com.example.tamproject.ui.navigation.BottomNavigationBar
import com.example.tamproject.ui.theme.*
import com.example.tamproject.ui.utils.getResourceId
import com.example.tamproject.ui.viewmodel.WasteViewModel

@Composable
fun WasteSortingScreen(
    viewModel: WasteViewModel,
    onCategoryClick: (String) -> Unit,
    onHomeClick: () -> Unit,
    onEcoChallengeClick: () -> Unit,
    onScanClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val sortingItems by viewModel.sortingItems.collectAsState(initial = emptyList())
    var searchQuery by remember { mutableStateOf("") }

    val filteredItems = remember(sortingItems, searchQuery) {
        if (searchQuery.isBlank()) sortingItems
        else sortingItems.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

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
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Header Section with Logo
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(GradientGreenStart, GradientGreenEnd)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = HijauEco,
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Li-Ku Saku Center",
                            color = HijauEco,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Search Bar Section
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search . . .", color = AbuAbuText) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = AbuAbuText) },
                    trailingIcon = { Icon(Icons.Outlined.QrCodeScanner, contentDescription = null, tint = Color.Black) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.PaddingLarge)
                        .offset(y = (-25).dp)
                        .height(54.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(25.dp), ambientColor = Color.LightGray),
                    shape = RoundedCornerShape(25.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = HijauEco
                    ),
                    singleLine = true
                )
            }

            item {
                Text(
                    text = "Sorting",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = Dimens.PaddingLarge),
                    color = Color.Black
                )
            }

            // Sorting Categories Grid
            item {
                if (sortingItems.isEmpty()) {
                    Box(modifier = Modifier.fillMaxWidth().height(150.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = HijauEco)
                    }
                } else {
                    Column(
                        modifier = Modifier.padding(Dimens.PaddingLarge),
                        verticalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium)
                    ) {
                        filteredItems.chunked(3).forEach { rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium)
                            ) {
                                rowItems.forEach { item ->
                                    SortingCategoryCard(item, Modifier.weight(1f)) {
                                        onCategoryClick(item.name)
                                    }
                                }
                                repeat(3 - rowItems.size) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }

            // Compost Guard Monitor Section
            item {
                Text(
                    text = "Compost Guard Monitor",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = Dimens.PaddingLarge),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(Dimens.PaddingMedium))
                
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.PaddingLarge)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(Dimens.CornerRadiusLarge), ambientColor = Color.LightGray),
                    shape = RoundedCornerShape(Dimens.CornerRadiusLarge),
                    colors = CardDefaults.cardColors(containerColor = HijauEco)
                ) {
                    Row(
                        modifier = Modifier.padding(Dimens.PaddingMedium),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Kitchen Compost.", color = Color.White, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color.Green))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Cooked", color = Color.White, fontSize = 12.sp)
                            }
                            Spacer(modifier = Modifier.height(Dimens.PaddingSmall))
                            Text(
                                "Auto-Desc :\nDecomposition is\ncomplete...",
                                color = Color.White,
                                fontSize = 10.sp,
                                lineHeight = 12.sp,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            )
                        }

                        Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            // Temperature Card
                            Surface(
                                color = Color.White,
                                shape = RoundedCornerShape(Dimens.CornerRadiusMedium),
                                modifier = Modifier.width(130.dp)
                            ) {
                                Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Column {
                                        Text("temperature", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                                        Text("42°C", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                                    }
                                    Spacer(modifier = Modifier.weight(1f))
                                    Icon(Icons.Default.DeviceThermostat, contentDescription = null, tint = Color.Red.copy(alpha = 0.6f))
                                }
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                // Gas Card
                                MonitorSmallCard("Gas", "Low", Icons.Default.Cloud, Color.Blue.copy(alpha = 0.2f))
                                // pH Card
                                MonitorSmallCard("pH", "7.1", Icons.Default.WaterDrop, Color.Yellow.copy(alpha = 0.4f))
                            }
                        }
                    }
                }
                
                Box(modifier = Modifier.fillMaxWidth().padding(horizontal = Dimens.PaddingLarge, vertical = Dimens.PaddingMedium)) {
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = HijauEco),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.align(Alignment.CenterEnd).height(32.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
                    ) {
                        Text("+ Compost", fontSize = 12.sp, color = Color.White)
                    }
                }
                Spacer(modifier = Modifier.height(Dimens.PaddingLarge))
            }
        }
    }
}

@Composable
fun MonitorSmallCard(label: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector, iconTint: Color) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.width(62.dp)
    ) {
        Column(modifier = Modifier.padding(6.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(label, fontSize = 8.sp, color = Color.Black)
                Spacer(modifier = Modifier.width(2.dp))
                Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(10.dp))
            }
            Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}

@Composable
fun SortingCategoryCard(item: SortingItem, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val itemColor = try { Color(android.graphics.Color.parseColor(item.colorHex)) } catch (e: Exception) { HijauEco }
    Card(
        modifier = modifier
            .aspectRatio(0.85f)
            .clickable { onClick() }
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(Dimens.CornerRadiusLarge), ambientColor = Color.LightGray),
        shape = RoundedCornerShape(Dimens.CornerRadiusLarge),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(Dimens.PaddingSmall),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Recycling,
                    contentDescription = item.name,
                    modifier = Modifier.size(32.dp),
                    tint = itemColor
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.name,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                color = Color.Black,
                lineHeight = 13.sp,
                fontSize = 11.sp
            )
        }
    }
}

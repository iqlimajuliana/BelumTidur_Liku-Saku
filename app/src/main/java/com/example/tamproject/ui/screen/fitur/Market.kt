package com.example.tamproject.ui.screen.fitur

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tamproject.R
import com.example.tamproject.ui.navigation.BottomNavigationBar
import com.example.tamproject.ui.theme.HeaderGradientEnd
import com.example.tamproject.ui.theme.HeaderGradientStart
import com.example.tamproject.ui.theme.MainGreen

@Composable
fun MarketScreen(
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
                currentScreen = "market",
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
                Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = null, tint = MainGreen)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(HeaderGradientStart, HeaderGradientEnd)
                        )
                    )
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = "LI-MARKET",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainGreen
                )
            }

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Search . . .") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = MainGreen)
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = null, tint = MainGreen)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        MarketStatusItem(Icons.Default.AccountBalanceWallet, "Gopay", "RP 2.000")
                        VerticalDivider(modifier = Modifier.height(40.dp))
                        MarketStatusItem(Icons.Default.Stars, "Poins", "Check today's missions")
                        VerticalDivider(modifier = Modifier.height(40.dp))
                        MarketStatusItem(Icons.Default.LocalShipping, "Order", "View Details")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text("Categories", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CategoryChip("All", true)
                    CategoryChip("Handicraft", false)
                    CategoryChip("Compost", false)
                    CategoryChip("Seed", false)
                }

                Spacer(modifier = Modifier.height(20.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(productsList) { product ->
                        ProductCard(product)
                    }
                }
            }
        }
    }
}

@Composable
fun MarketStatusItem(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Color(0xFFFFD700), modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Text(subtitle, fontSize = 9.sp, color = Color.Gray)
        }
    }
}

@Composable
fun CategoryChip(label: String, isSelected: Boolean) {
    Surface(
        color = if (isSelected) MainGreen else Color.White,
        shape = RoundedCornerShape(16.dp),
        border = if (!isSelected) androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray) else null
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
            color = if (isSelected) Color.White else Color.Gray,
            fontSize = 12.sp
        )
    }
}

data class ProductItem(val name: String, val price: String, val location: String, val imageRes: Int)

val productsList = listOf(
    ProductItem("Organic fertilizer", "RP 14.000", "Jakarta", R.drawable.dashboard),
    ProductItem("Straw vase", "RP 20.000", "Bandung", R.drawable.dashboard),
    ProductItem("Chocolate seeds", "RP 15.000", "Malang", R.drawable.dashboard),
    ProductItem("Chili seeds", "RP 8.000", "Lampung", R.drawable.dashboard)
)

@Composable
fun ProductCard(product: ProductItem) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentScale = ContentScale.Crop
                )
                Icon(
                    Icons.Default.Percent,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(20.dp)
                        .background(Color(0xFFFF7043), CircleShape)
                        .padding(4.dp)
                )
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(product.name, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(product.price, fontSize = 12.sp, color = Color.Red, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocalShipping, contentDescription = null, tint = MainGreen, modifier = Modifier.size(14.dp))
                    Text(" 1 - 2 Days ", fontSize = 9.sp, color = Color.Gray)
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(10.dp))
                    Text(product.location, fontSize = 9.sp, color = Color.Gray)
                }
            }
        }
    }
}

package com.example.tamproject.ui.screen.fitur

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
import androidx.compose.material.icons.outlined.ShoppingCart
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
import com.example.tamproject.data.model.ProductItem
import com.example.tamproject.ui.navigation.BottomNavigationBar
import com.example.tamproject.ui.theme.*
import com.example.tamproject.ui.viewmodel.MarketViewModel
import kotlinx.coroutines.flow.collectLatest
import java.util.Locale

@Composable
fun MarketScreen(
    viewModel: MarketViewModel,
    onHomeClick: () -> Unit,
    onEcoChallengeClick: () -> Unit,
    onScanClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val products by viewModel.products.collectAsState(initial = emptyList())
    val searchQuery by viewModel.searchQuery.collectAsState()
    val userData by viewModel.userData.collectAsState(initial = null)
    val isLoading by viewModel.isLoading.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is MarketViewModel.UiEvent.Success -> snackbarHostState.showSnackbar(event.message)
                is MarketViewModel.UiEvent.Error -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    Scaffold(
        containerColor = Color.White,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            BottomNavigationBar(
                currentScreen = "home",
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
            // Header Section
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(GradientGreenStart, GradientGreenEnd)
                            )
                        )
                        .padding(horizontal = Dimens.PaddingLarge, vertical = Dimens.PaddingLarge),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        text = "LI-MARKET",
                        style = MaterialTheme.typography.displayLarge,
                        color = HijauEco,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            // Search Bar & Icons
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.PaddingLarge, vertical = Dimens.PaddingMedium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.onSearchChanged(it) },
                        placeholder = { Text("Search . . .", color = AbuAbuText) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = AbuAbuText) },
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = HijauEco,
                            unfocusedBorderColor = HijauEco,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(Dimens.PaddingSmall))
                    Icon(Icons.Outlined.ShoppingCart, contentDescription = "Cart", tint = HijauEco, modifier = Modifier.size(32.dp))
                    Spacer(modifier = Modifier.width(Dimens.PaddingSmall))
                    Icon(Icons.Outlined.ChatBubbleOutline, contentDescription = "Chat", tint = HijauEco, modifier = Modifier.size(32.dp))
                }
            }

            // Wallet & Orders Status Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.PaddingLarge)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(Dimens.CornerRadiusLarge), ambientColor = Color.LightGray),
                    shape = RoundedCornerShape(Dimens.CornerRadiusLarge),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFEEEEEE))
                ) {
                    Row(
                        modifier = Modifier.padding(Dimens.PaddingMedium),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MarketHeaderItem(Icons.Default.AccountBalanceWallet, "Gopay", "RP ${String.format(Locale.getDefault(), "%,.0f", userData?.balance ?: 0.0)}")
                        VerticalDivider(modifier = Modifier.height(40.dp), color = Color.LightGray)
                        MarketHeaderItem(Icons.Default.Stars, "Points", "${userData?.points ?: 0} Pts")
                        VerticalDivider(modifier = Modifier.height(40.dp), color = Color.LightGray)
                        MarketHeaderItem(Icons.Default.LocalShipping, "Order", "View Details")
                    }
                }
                Spacer(modifier = Modifier.height(Dimens.PaddingLarge))
            }

            // Categories
            item {
                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = Dimens.PaddingLarge),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(Dimens.PaddingMedium))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = Dimens.PaddingLarge),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.PaddingSmall)
                ) {
                    val categories = listOf("All", "Handicraft", "Compost", "Seed")
                    items(categories) { category ->
                        CategoryItemChip(category, category == "All")
                    }
                }
                Spacer(modifier = Modifier.height(Dimens.PaddingLarge))
            }

            if (isLoading) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = HijauEco)
                    }
                }
            } else {
                val rows = products.chunked(2)
                items(rows) { rowProducts ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimens.PaddingLarge, vertical = Dimens.PaddingSmall),
                        horizontalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium)
                    ) {
                        for (product in rowProducts) {
                            MarketProductCard(
                                modifier = Modifier.weight(1f),
                                product = product,
                                onBuyBalanceClick = { viewModel.buyWithBalance(product) },
                                onRedeemPointsClick = { viewModel.redeemWithPoints(product) }
                            )
                        }
                        if (rowProducts.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(Dimens.PaddingExtraLarge)) }
        }
    }
}

@Composable
fun MarketHeaderItem(icon: Any, title: String, subtitle: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (icon is Int) {
            Image(painter = painterResource(id = icon), contentDescription = null, modifier = Modifier.size(24.dp))
        } else if (icon is androidx.compose.ui.graphics.vector.ImageVector) {
            Icon(icon, contentDescription = null, tint = HijauEco, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(title, style = MaterialTheme.typography.labelLarge, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = Color.Black, fontSize = 10.sp, lineHeight = 12.sp)
        }
    }
}

@Composable
fun CategoryItemChip(label: String, isSelected: Boolean) {
    Surface(
        color = if (isSelected) HijauEco else Color.White,
        shape = RoundedCornerShape(Dimens.CornerRadiusMedium),
        border = BorderStroke(1.dp, if (isSelected) HijauEco else AbuAbuBorder),
        modifier = Modifier.clickable { }
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
            color = if (isSelected) Color.White else Color.Black,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun MarketProductCard(
    modifier: Modifier, 
    product: ProductItem, 
    onBuyBalanceClick: () -> Unit,
    onRedeemPointsClick: () -> Unit
) {
    Card(
        modifier = modifier.shadow(elevation = 4.dp, shape = RoundedCornerShape(Dimens.CornerRadiusLarge), ambientColor = Color.LightGray),
        shape = RoundedCornerShape(Dimens.CornerRadiusLarge),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFEEEEEE))
    ) {
        Column {
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.imageRes)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(110.dp),
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.dashboard)
                )
                // Discount Badge
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(Dimens.PaddingSmall)
                        .size(20.dp)
                        .background(DiscountBadge, CircleShape)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Percent, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                }
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(product.name, style = MaterialTheme.typography.bodyMedium, color = Color.Black, maxLines = 1, fontWeight = FontWeight.Bold)
                Text(product.price, style = MaterialTheme.typography.bodySmall, color = ErrorRed, fontWeight = FontWeight.Bold)
                
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)) {
                    Icon(Icons.Default.LocalShipping, contentDescription = null, tint = Color(0xFF5C6BC0), modifier = Modifier.size(10.dp))
                    Text(" 1-2 Days", fontSize = 8.sp, color = Color.Black)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = AbuAbuText, modifier = Modifier.size(8.dp))
                    Text(" Jakarta", fontSize = 8.sp, color = AbuAbuText)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = onBuyBalanceClick,
                    modifier = Modifier.fillMaxWidth().height(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = HijauEco),
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text("Buy with Balance", fontSize = 10.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedButton(
                    onClick = onRedeemPointsClick,
                    modifier = Modifier.fillMaxWidth().height(28.dp),
                    border = BorderStroke(1.dp, KuningPoint),
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = KuningPoint)
                ) {
                    Text("Redeem with Points", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

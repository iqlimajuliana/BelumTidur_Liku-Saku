package com.example.tamproject.ui.screen.fitur

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tamproject.R
import com.example.tamproject.data.model.PantryItemData
import com.example.tamproject.ui.navigation.BottomNavigationBar
import com.example.tamproject.ui.theme.*
import com.example.tamproject.ui.viewmodel.PantryViewModel

@Composable
fun SmartPantryScreen(
    viewModel: PantryViewModel,
    onCalendarClick: () -> Unit,
    onHomeClick: () -> Unit,
    onEcoChallengeClick: () -> Unit,
    onScanClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val pantryItems by viewModel.pantryItems.collectAsState(initial = null)
    val isLoading by viewModel.isLoading.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.PaddingLarge, vertical = Dimens.PaddingLarge),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your Pantry",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { showAddDialog = true }) {
                    Icon(Icons.Default.AddCircleOutline, contentDescription = "Add", tint = Color.Black, modifier = Modifier.size(Dimens.IconLarge))
                }
                IconButton(onClick = onCalendarClick) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = "Calendar", tint = Color.Black, modifier = Modifier.size(Dimens.IconLarge))
                }
            }

            if (isLoading || pantryItems == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = HijauEco)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(horizontal = Dimens.PaddingLarge),
                    verticalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium)
                ) {
                    items(pantryItems!!) { item ->
                        PantryItemCard(
                            item = item, 
                            onDelete = { viewModel.deleteItem(item.id) },
                            onUpdateQuantity = { newSum -> viewModel.updateQuantity(item.id, newSum) }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(Dimens.PaddingLarge))
                        Text(
                            text = "Recipe ideas",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(Dimens.PaddingMedium))
                        Row(horizontalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium)) {
                            RecipeCard(
                                title = "Banana pancakes",
                                imageRes = R.drawable.pancakes,
                                modifier = Modifier.weight(1f)
                            )
                            RecipeCard(
                                title = "Smoothies",
                                imageRes = R.drawable.smoothies,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Spacer(modifier = Modifier.height(Dimens.PaddingExtraLarge))
                    }
                }
            }
        }

        if (showAddDialog) {
            AddItemDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { name, sum, date ->
                    viewModel.addItem(name, sum.toIntOrNull() ?: 1, date, "")
                    showAddDialog = false
                }
            )
        }
    }
}

@Composable
fun PantryItemCard(
    item: PantryItemData, 
    onDelete: () -> Unit,
    onUpdateQuantity: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(Dimens.CornerRadiusLarge)),
        shape = RoundedCornerShape(Dimens.CornerRadiusLarge),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFF5F5F5))
    ) {
        Column(modifier = Modifier.padding(Dimens.PaddingMedium)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        ),
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Exp: ${item.date}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red.copy(alpha = 0.7f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Category: Food",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = { if (item.sum > 1) onUpdateQuantity(item.sum - 1) },
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color(0xFFF5F5F5), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Decrease",
                            tint = Color.Black,
                            modifier = Modifier.size(12.dp)
                        )
                    }

                    Text(
                        text = "${item.sum}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )

                    IconButton(
                        onClick = { onUpdateQuantity(item.sum + 1) },
                        modifier = Modifier
                            .size(24.dp)
                            .background(HijauEco, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increase",
                            tint = Color.White,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    
                    Text(
                        text = "Pcs",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun RecipeCard(title: String, imageRes: Int, modifier: Modifier) {
    Column(modifier = modifier) {
        Card(
            modifier = Modifier.fillMaxWidth().aspectRatio(1.5f),
            shape = RoundedCornerShape(Dimens.CornerRadiusLarge),
        ) {
            androidx.compose.foundation.Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(Dimens.PaddingSmall))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun AddItemDialog(onDismiss: () -> Unit, onConfirm: (String, String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var sum by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = { Text("Add New Item", color = Color.Black) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.PaddingSmall)) {
                OutlinedTextField(
                    value = name, 
                    onValueChange = { name = it }, 
                    placeholder = { Text("Item Name") },
                    shape = RoundedCornerShape(Dimens.CornerRadiusMedium)
                )
                OutlinedTextField(
                    value = sum, 
                    onValueChange = { sum = it }, 
                    placeholder = { Text("Quantity") },
                    shape = RoundedCornerShape(Dimens.CornerRadiusMedium)
                )
                OutlinedTextField(
                    value = date, 
                    onValueChange = { date = it }, 
                    placeholder = { Text("Expiry Date (e.g. 14 July 2025)") },
                    shape = RoundedCornerShape(Dimens.CornerRadiusMedium)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(name, sum, date) },
                colors = ButtonDefaults.buttonColors(containerColor = HijauEco)
            ) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel", color = Color.Gray) }
        }
    )
}

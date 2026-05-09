package com.example.tamproject.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.tamproject.ui.components.BottomNavigationBar
import com.example.tamproject.ui.theme.*

@Composable
fun SmartPantryScreen(
    onCalendarClick: () -> Unit,
    onHomeClick: () -> Unit,
    onEcoChallengeClick: () -> Unit,
    onWasteSortingClick: () -> Unit,
    onMyPointsClick: () -> Unit,
    onSmartPantryClick: () -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentScreen = "smart_pantry",
                onHomeClick = onHomeClick,
                onEcoChallengeClick = onEcoChallengeClick,
                onWasteSortingClick = onWasteSortingClick,
                onMyPointsClick = onMyPointsClick,
                onSmartPantryClick = onSmartPantryClick
            )
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
                    .height(140.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(HeaderGradientStart, HeaderGradientEnd)
                        )
                    )
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Your Pantry",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.Black)
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = "Meal Plan",
                        tint = Color.Black,
                        modifier = Modifier.clickable { onCalendarClick() }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(pantryItemsList) { item ->
                    PantryCard(item)
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Recipe ideas", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        RecipeCard("Banana pancakes", R.drawable.pancakes, Modifier.weight(1f))
                        RecipeCard("Smoothies", R.drawable.smoothies, Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

data class PantryItemData(val name: String, val sum: String, val date: String, val iconRes: Int)

val pantryItemsList = listOf(
    PantryItemData("Freshmilk", "1 Pcs", "14 July 2025", R.drawable.milk),
    PantryItemData("Eggs", "4 Pcs", "19 July 2025", R.drawable.egg),
    PantryItemData("Bananas", "4 Pcs", "15 July 2025", R.drawable.banana),
    PantryItemData("Dragon fruits", "1 Pcs", "15 July 2025", R.drawable.dragon),
    PantryItemData("Oat", "1 Pcs", "26 August 2026", R.drawable.oat)
)

@Composable
fun PantryCard(item: PantryItemData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.iconRes),
                contentDescription = null,
                modifier = Modifier.size(50.dp).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Sum: ${item.sum}", fontSize = 11.sp, color = Color.Gray)
                Text("Categories:", fontSize = 11.sp, color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.End) {
                Icon(Icons.Default.MoreHoriz, contentDescription = null, tint = Color.Gray)
                Spacer(modifier = Modifier.height(12.dp))
                Text("Expired: ${item.date}", fontSize = 9.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun RecipeCard(title: String, imageRes: Int, modifier: Modifier) {
    Column(modifier = modifier) {
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth().height(120.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(title, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}
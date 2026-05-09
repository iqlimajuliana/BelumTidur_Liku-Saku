package com.example.tamproject.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tamproject.R
import com.example.tamproject.ui.components.BottomNavigationBar
import com.example.tamproject.ui.theme.*

@Composable
fun WasteSortingScreen(
    onInorganicClick: () -> Unit,
    onHomeClick: () -> Unit,
    onEcoChallengeClick: () -> Unit,
    onWasteSortingClick: () -> Unit,
    onMyPointsClick: () -> Unit,
    onSmartPantryClick: () -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentScreen = "waste_sorting",
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
            // Header with Gradient
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(HeaderGradientStart, HeaderGradientEnd)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Logo",
                        tint = MainGreen,
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "Li-Ku\nSaku",
                        color = MainGreen,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 24.sp
                    )
                }
            }

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                // Search Bar
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Search...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = { Icon(Icons.Default.Info, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .clip(RoundedCornerShape(30.dp)),
                    shape = RoundedCornerShape(30.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                    )
                )

                Text(
                    text = "Sorting",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                val sortingItems = listOf(
                    SortingItem("Organic", R.drawable.vector, OrganicColor),
                    SortingItem("Inorganic", R.drawable.vector, InorganicColor),
                    SortingItem("Hazardous\nAnd Toxic", R.drawable.vector, HazardousColor),
                    SortingItem("Paper", R.drawable.vector, PaperColor),
                    SortingItem("Residue", R.drawable.vector, ResidueColor)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.height(240.dp)
                ) {
                    items(sortingItems) { item ->
                        SortingCard(item) {
                            if (item.name == "Inorganic") onInorganicClick()
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Compost Guard Monitor",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Compost Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MainGreen)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Kitchen Compost.", color = Color.White, fontWeight = FontWeight.Bold)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(Color.Green))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Cooked", color = Color.White, fontSize = 12.sp)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Auto-Desc :\nDecomposition is\ncomplete..",
                                color = Color.White,
                                fontSize = 10.sp,
                                lineHeight = 12.sp
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                modifier = Modifier.width(120.dp)
                            ) {
                                Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Column {
                                        Text("temperature", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                        Text("42°C", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Spacer(modifier = Modifier.weight(1f))
                                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color.Red)
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row {
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    modifier = Modifier.width(60.dp)
                                ) {
                                    Column(modifier = Modifier.padding(4.dp)) {
                                        Text("Gas", fontSize = 10.sp)
                                        Text("Low", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    modifier = Modifier.width(52.dp)
                                ) {
                                    Column(modifier = Modifier.padding(4.dp)) {
                                        Text("pH", fontSize = 10.sp)
                                        Text("7.1", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }

                Button(
                    onClick = { },
                    modifier = Modifier.align(Alignment.End).padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MainGreen),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("+ Compost", fontSize = 12.sp)
                }
            }
        }
    }
}

data class SortingItem(val name: String, val iconRes: Int, val color: Color)

@Composable
fun SortingCard(item: SortingItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = item.iconRes),
                contentDescription = null,
                tint = item.color,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

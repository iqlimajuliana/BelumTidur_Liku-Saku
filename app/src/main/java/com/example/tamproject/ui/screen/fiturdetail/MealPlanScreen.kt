package com.example.tamproject.ui.screen.fiturdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tamproject.ui.navigation.BottomNavigationBar
import com.example.tamproject.ui.theme.*

@Composable
fun MealPlanScreen(
    onBack: () -> Unit,
    onHomeClick: () -> Unit,
    onEcoChallengeClick: () -> Unit,
    onScanClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Action chat */ },
                containerColor = Color.White,
                contentColor = HijauEco,
                shape = CircleShape,
                modifier = Modifier
                    .padding(bottom = 60.dp)
                    .size(56.dp)
                    .shadow(elevation = 6.dp, shape = CircleShape)
                    .border(1.dp, HijauEco, CircleShape)
            ) {
                Icon(Icons.Default.ChatBubbleOutline, contentDescription = "Chat")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Header Section with Gradient (matches image)
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
                Column {
                    IconButton(onClick = onBack, modifier = Modifier.offset(x = (-12).dp)) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = HijauEco)
                    }
                    Text(
                        text = "Meal Plan",
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.Black
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.PaddingLarge),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "July 2025",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = Dimens.PaddingMedium)
                )

                CalendarGrid()
            }
        }
    }
}

@Composable
fun CalendarGrid() {
    val days = listOf("Sun", "Mon", "Tue", "Wed", "Thurs", "Fri", "Sat")
    val dates = (1..31).map { it.toString() }
    val emptyPreDays = 2

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, HijauEco)
    ) {
        // Day Headers
        Row(modifier = Modifier.background(HijauEco)) {
            days.forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
                )
            }
        }

        // Calendar Content using Vertical Grid for better alignment
        val totalCells = emptyPreDays + dates.size
        val rows = (totalCells + 6) / 7

        repeat(rows) { rowIndex ->
            Row(modifier = Modifier.height(70.dp).fillMaxWidth()) {
                repeat(7) { colIndex ->
                    val cellIndex = rowIndex * 7 + colIndex
                    val dateText = if (cellIndex in emptyPreDays until totalCells) {
                        dates[cellIndex - emptyPreDays]
                    } else {
                        ""
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .border(0.5.dp, HijauEco),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        if (dateText.isNotEmpty()) {
                            Text(
                                text = dateText,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 8.dp),
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

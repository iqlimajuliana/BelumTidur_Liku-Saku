package com.example.tamproject.ui.screen.fitur

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tamproject.R
import com.example.tamproject.data.model.NotificationItem
import com.example.tamproject.ui.navigation.BottomNavigationBar
import com.example.tamproject.ui.theme.*
import com.example.tamproject.ui.viewmodel.NotificationViewModel

@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel,
    onHomeClick: () -> Unit,
    onEcoChallengeClick: () -> Unit,
    onScanClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val notifications by viewModel.notifications.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            BottomNavigationBar(
                currentScreen = "notification",
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
                Icon(Icons.Default.ChatBubbleOutline, contentDescription = "Chat")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Text(
                    text = "Notification",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(Dimens.PaddingLarge)
                )
            }

            if (isLoading) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(400.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = HijauEco)
                    }
                }
            } else {
                items(notifications) { item ->
                    NotificationItemCard(item)
                }
            }
            
            item { Spacer(modifier = Modifier.height(Dimens.PaddingExtraLarge)) }
        }
    }
}

@Composable
fun NotificationItemCard(item: NotificationItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.PaddingLarge, vertical = Dimens.PaddingSmall)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(Dimens.CornerRadiusLarge)),
        shape = RoundedCornerShape(Dimens.CornerRadiusLarge),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, HijauEco)
    ) {
        Row(
            modifier = Modifier.padding(Dimens.PaddingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.vector),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .background(HijauEco, RoundedCornerShape(Dimens.CornerRadiusMedium))
                    .padding(8.dp)
            )
            
            Spacer(modifier = Modifier.width(Dimens.PaddingMedium))
            
            Text(
                text = item.message,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier.weight(1f),
                lineHeight = 18.sp,
                fontWeight = FontWeight.Medium
            )
            
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

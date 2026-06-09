package com.example.tamproject.ui.screen.fiturdetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.tamproject.data.model.ScanHistory
import com.example.tamproject.ui.theme.*

@Composable
fun WasteDetailScreen(
    item: ScanHistory?,
    onBack: () -> Unit,
    onDropOffClick: () -> Unit,
    nearestDropName: String = "PT Angkut Aja",
    nearestDropDistance: String = "2 Km"
) {
    Scaffold(
        containerColor = Color.White
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth().height(400.dp)) {
                    // Main Waste Image - Using user uploaded image
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item?.imageUrl.takeIf { !it.isNullOrBlank() } ?: R.drawable.inorganic)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.inorganic),
                        error = painterResource(id = R.drawable.inorganic)
                    )
                    
                    // Header Overlay
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color.Black.copy(alpha = 0.4f), Color.Transparent)
                                )
                            )
                            .padding(horizontal = Dimens.PaddingLarge),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = onBack) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = item?.category ?: "Waste Detail",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            item {
                // Info Section with large rounded top
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-40).dp),
                    shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFEEEEEE))
                ) {
                    Column(modifier = Modifier.padding(Dimens.PaddingLarge)) {
                        Text(
                            text = "Detail information",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(Dimens.PaddingLarge))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                DetailRowItem("Material = ${item?.material.takeIf { !it.isNullOrBlank() } ?: "Not provided"}")
                                DetailRowItem("Recyclable = ${item?.recyclable.takeIf { !it.isNullOrBlank() } ?: "Not provided"}")
                                DetailRowItem("Recycling Process = ${item?.recyclingProcess.takeIf { !it.isNullOrBlank() } ?: "Not provided"}")
                            }
                            Image(
                                painter = painterResource(id = R.drawable.reward), // Yellow recycle/reward icon
                                contentDescription = null,
                                modifier = Modifier.size(70.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(Dimens.PaddingLarge))

                        WasteDetailActionCard(
                            title = "Nearest Eco Waste Drop",
                            subtitle = "$nearestDropName ($nearestDropDistance)",
                            buttonText = "Drop it off",
                            onClick = onDropOffClick
                        )

                        Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

                        WasteDetailActionCard(
                            title = "Turn your waste into points!",
                            subtitle = "Collect points for every item",
                            buttonText = "Redeem now",
                            onClick = {}
                        )
                        
                        Spacer(modifier = Modifier.height(Dimens.PaddingExtraLarge))
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRowItem(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(vertical = 4.dp),
        color = Color.Black,
        lineHeight = 18.sp
    )
}

@Composable
fun WasteDetailActionCard(title: String, subtitle: String, buttonText: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.CornerRadiusLarge),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFEEEEEE))
    ) {
        Row(
            modifier = Modifier.padding(Dimens.PaddingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium, color = Color.Black)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = Color.Black)
            }
            OutlinedButton(
                onClick = onClick,
                border = BorderStroke(1.dp, HijauEco),
                shape = RoundedCornerShape(Dimens.CornerRadiusMedium),
                modifier = Modifier.height(36.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
            ) {
                Text(buttonText, style = MaterialTheme.typography.bodySmall, color = HijauEco, fontWeight = FontWeight.Medium)
            }
        }
    }
}

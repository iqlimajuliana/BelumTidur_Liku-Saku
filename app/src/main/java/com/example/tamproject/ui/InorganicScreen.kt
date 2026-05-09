package com.example.tamproject.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tamproject.R
import com.example.tamproject.ui.theme.*

@Composable
fun InorganicScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(HeaderGradientStart, HeaderGradientEnd)
                    )
                )
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MainGreen)
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Inorganic Waste",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1.2f))
            }
        }

        Image(
            painter = painterResource(id = R.drawable.inorganic),
            contentDescription = "Waste Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        Card(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-30).dp),
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Detail information",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        DetailRow("Material = Not PET (Glass)")
                        DetailRow("Recyclable = Yes")
                        DetailRow("Recycling Process = Washed -> Shredded -> Remade")
                    }
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = null,
                        tint = InorganicColor,
                        modifier = Modifier.size(60.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                ActionCard(
                    title = "Nearest Eco Waste Drop",
                    subtitle = "PT Angkut Aja (2 Km)",
                    buttonText = "Drop it off"
                )

                Spacer(modifier = Modifier.height(16.dp))

                ActionCard(
                    title = "Turn your glass waste into points!",
                    subtitle = "1 glass waste x 5 Points",
                    buttonText = "Redeem now"
                )
            }
        }
    }
}

@Composable
fun DetailRow(text: String) {
    Text(
        text = text,
        fontSize = 13.sp,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
fun ActionCard(title: String, subtitle: String, buttonText: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Text(subtitle, fontSize = 11.sp)
            }
            OutlinedButton(
                onClick = {},
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MainGreen),
                border = androidx.compose.foundation.BorderStroke(1.dp, MainGreen)
            ) {
                Text(buttonText, fontSize = 10.sp)
            }
        }
    }
}

package com.example.tamproject.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tamproject.R
import com.example.tamproject.ui.theme.Dimens
import com.example.tamproject.ui.theme.HijauEco

@Composable
fun WelcomeScreen(
    onGetStartedClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image sesuai gambar referensi (Truk/Eco-Hero)
        Image(
            painter = painterResource(id = R.drawable.login_4),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Gradient Overlay agar teks terbaca jelas
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                        startY = 500f
                    )
                )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.PaddingLarge),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Hey eco-hero!\nGlad you’re here.",
                    color = Color.White,
                    style = MaterialTheme.typography.displayLarge,
                    lineHeight = 40.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(Dimens.PaddingLarge))

                Button(
                    onClick = onGetStartedClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(Dimens.CornerRadiusMedium),
                    colors = ButtonDefaults.buttonColors(containerColor = HijauEco)
                ) {
                    Text(
                        text = "Get Started",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't have an account? ",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Sign up.",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable { onSignUpClick() }
                            .padding(4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(Dimens.PaddingExtraLarge))
            }
        }
    }
}

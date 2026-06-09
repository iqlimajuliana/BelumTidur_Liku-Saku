package com.example.tamproject.ui.screen.login

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.tamproject.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onAnimationFinished: () -> Unit) {
    var currentStep by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        delay(1500) // First image for 1.5s
        currentStep = 1
        delay(1000) // Transition/Second image duration
        onAnimationFinished()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Crossfade(targetState = currentStep, animationSpec = tween(1000)) { step ->
            when (step) {
                0 -> {
                    Image(
                        painter = painterResource(id = R.drawable.login_0),
                        contentDescription = "Splash Logo White",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }
                1 -> {
                    Image(
                        painter = painterResource(id = R.drawable.login_3),
                        contentDescription = "Splash Logo Green",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        }
    }
}

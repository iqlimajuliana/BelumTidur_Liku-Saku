package com.example.tamproject.ui.screen.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tamproject.R
import com.example.tamproject.ui.theme.AbuAbuText
import com.example.tamproject.ui.theme.Dimens
import com.example.tamproject.ui.theme.HijauEco
import com.example.tamproject.ui.viewmodel.AuthViewModel
import com.example.tamproject.ui.viewmodel.LoginState

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    
    val loginState by viewModel.loginState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            onLoginClick()
            viewModel.resetStates()
        } else if (loginState is LoginState.Error) {
            Toast.makeText(context, (loginState as LoginState.Error).message, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(containerColor = Color.White) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = Dimens.PaddingLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(Dimens.PaddingLarge))
                
                // Illustration
                Image(
                    painter = painterResource(id = R.drawable.login),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = "WELCOME",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

                Text(
                    text = "Your personalized waste assistant is\nready. Log in!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(Dimens.PaddingExtraLarge))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Username", color = AbuAbuText) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(Dimens.CornerRadiusExtraLarge),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Password", color = AbuAbuText) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(Dimens.CornerRadiusExtraLarge),
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = null, tint = Color.Black)
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Forget password",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(top = Dimens.PaddingSmall)
                            .clickable { },
                        style = MaterialTheme.typography.bodyMedium,
                        color = AbuAbuText
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.PaddingExtraLarge))

                if (loginState is LoginState.Loading) {
                    CircularProgressIndicator(color = HijauEco)
                } else {
                    Button(
                        onClick = {
                            if (email.isBlank() || password.isBlank()) {
                                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                            } else {
                                viewModel.login(email, password)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = HijauEco),
                        shape = RoundedCornerShape(Dimens.CornerRadiusExtraLarge)
                    ) {
                        Text(
                            "LOG IN",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimens.PaddingLarge))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
                    Text(
                        text = "  Or login with  ",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
                }

                Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Google",
                        modifier = Modifier.size(32.dp).clickable { }
                    )
                    Spacer(modifier = Modifier.width(Dimens.PaddingLarge))
                    Image(
                        painter = painterResource(id = R.drawable.fb),
                        contentDescription = "Facebook",
                        modifier = Modifier.size(32.dp).clickable { }
                    )
                    Spacer(modifier = Modifier.width(Dimens.PaddingLarge))
                    Image(
                        painter = painterResource(id = R.drawable.apple),
                        contentDescription = "Apple",
                        modifier = Modifier.size(32.dp).clickable { }
                    )
                }
                
                Spacer(modifier = Modifier.height(Dimens.PaddingLarge))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't have an account? ",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Sign up.",
                        color = HijauEco,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable { onSignUpClick() }
                            .padding(4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.PaddingLarge))
            }
        }
    }
}

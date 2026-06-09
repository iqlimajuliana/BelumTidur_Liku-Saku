package com.example.tamproject.ui.screen.login

import android.widget.Toast
import androidx.compose.foundation.Image
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
import com.example.tamproject.ui.viewmodel.RegisterState

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onBackToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val registerState by viewModel.registerState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(registerState) {
        if (registerState is RegisterState.Success) {
            Toast.makeText(context, "Registration Successful!", Toast.LENGTH_SHORT).show()
            onRegisterSuccess()
            viewModel.resetStates()
        } else if (registerState is RegisterState.Error) {
            Toast.makeText(context, (registerState as RegisterState.Error).message, Toast.LENGTH_LONG).show()
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
                
                Image(
                    painter = painterResource(id = R.drawable.login),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = "Create Your Account",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(Dimens.PaddingSmall))

                Text(
                    text = "Complete the information below to\ncreate your account.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(Dimens.PaddingExtraLarge))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Email", color = AbuAbuText) },
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
                    value = username,
                    onValueChange = { username = it },
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

                Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = { Text("Confirm Password", color = AbuAbuText) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(Dimens.CornerRadiusExtraLarge),
                    singleLine = true,
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
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

                Spacer(modifier = Modifier.height(Dimens.PaddingExtraLarge))

                if (registerState is RegisterState.Loading) {
                    CircularProgressIndicator(color = HijauEco)
                } else {
                    Button(
                        onClick = {
                            if (email.isBlank() || username.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                            } else if (password != confirmPassword) {
                                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                            } else {
                                viewModel.register(email, password, username)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = HijauEco),
                        shape = RoundedCornerShape(Dimens.CornerRadiusExtraLarge)
                    ) {
                        Text(
                            "Sign Up",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

                // Menambahkan navigasi balik ke Login agar parameter onBackToLogin digunakan
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Already have an account? ", color = AbuAbuText)
                    Text(
                        text = "Login",
                        color = HijauEco,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onBackToLogin() }
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.PaddingLarge))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
                    Text(
                        text = "  Sign up with  ",
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
            }
        }
    }
}

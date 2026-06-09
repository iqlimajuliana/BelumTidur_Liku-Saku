package com.example.tamproject.ui.screen.fitur

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tamproject.data.utils.Resource
import com.example.tamproject.ui.theme.HijauEco
import com.example.tamproject.ui.viewmodel.WasteViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputSortingScreen(
    viewModel: WasteViewModel,
    imageUri: String,
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    val context = LocalContext.current
    val userData by viewModel.userData.collectAsState(initial = null)
    
    val decodedUriString = remember(imageUri) {
        try {
            URLDecoder.decode(imageUri, StandardCharsets.UTF_8.toString())
        } catch (e: Exception) {
            imageUri
        }
    }

    var itemName by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Inorganic") }
    var material by remember { mutableStateOf("") }
    var recyclable by remember { mutableStateOf("Yes") }
    var recyclingProcess by remember { mutableStateOf("") }
    
    var expanded by remember { mutableStateOf(false) }
    val categories = listOf("Organic", "Inorganic", "Paper", "Hazardous", "Toxic")

    val uploadState by viewModel.uploadState.collectAsState()
    val isLoading = uploadState is Resource.Loading

    LaunchedEffect(uploadState) {
        when (uploadState) {
            is Resource.Success -> {
                Toast.makeText(context, (uploadState as Resource.Success<String>).data, Toast.LENGTH_SHORT).show()
                onSuccess()
                viewModel.resetUploadState()
            }
            is Resource.Error -> {
                Toast.makeText(context, (uploadState as Resource.Error).message, Toast.LENGTH_LONG).show()
                viewModel.resetUploadState()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Input Waste Details", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack, enabled = !isLoading) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(decodedUriString)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = itemName,
                onValueChange = { itemName = it },
                label = { Text("Item Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = category,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Category") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !isLoading,
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }, enabled = !isLoading) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                    }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    categories.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat) },
                            onClick = {
                                category = cat
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = material,
                onValueChange = { material = it },
                label = { Text("Material (e.g. Plastic, Glass)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = recyclable,
                onValueChange = { recyclable = it },
                label = { Text("Recyclable (Yes/No)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = recyclingProcess,
                onValueChange = { recyclingProcess = it },
                label = { Text("Recycling Process") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading,
                minLines = 3
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (itemName.isBlank()) {
                        Toast.makeText(context, "Item name cannot be empty", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    
                    val userId = userData?.userId
                    if (userId.isNullOrBlank()) {
                        Toast.makeText(context, "User ID not found. Please login again.", Toast.LENGTH_LONG).show()
                        return@Button
                    }
                    
                    val uri = try {
                        Uri.parse(decodedUriString)
                    } catch (e: Exception) {
                        null
                    }
                    
                    if (uri == null || uri == Uri.EMPTY) {
                        Toast.makeText(context, "Invalid image format", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    viewModel.saveManual(
                        userId = userId,
                        itemName = itemName,
                        category = category,
                        imageUri = uri,
                        material = material,
                        recyclable = recyclable,
                        recyclingProcess = recyclingProcess
                    )
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = HijauEco),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White, 
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Save Data", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

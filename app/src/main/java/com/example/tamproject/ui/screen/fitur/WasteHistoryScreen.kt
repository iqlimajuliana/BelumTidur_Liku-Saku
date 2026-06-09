package com.example.tamproject.ui.screen.fitur

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tamproject.R
import com.example.tamproject.data.model.ScanHistory
import com.example.tamproject.ui.theme.AbuAbuText
import com.example.tamproject.ui.theme.HijauEco
import com.example.tamproject.ui.viewmodel.WasteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WasteHistoryScreen(
    viewModel: WasteViewModel,
    category: String,
    onItemClick: (ScanHistory) -> Unit,
    onBack: () -> Unit
) {
    val scanHistory by viewModel.scanHistory.collectAsState()
    val userData by viewModel.userData.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(category, userData) {
        userData?.let {
            viewModel.loadScanHistoryByCategory(it.userId, category)
        }
    }

    val filteredHistory = remember(scanHistory, searchQuery) {
        scanHistory.filter { it.itemName.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text("$category History", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search items...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(25.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                singleLine = true
            )

            if (filteredHistory.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No items found in $category", color = AbuAbuText)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(filteredHistory) { item ->
                        WasteHistoryCard(item = item, onClick = { onItemClick(item) })
                    }
                }
            }
        }
    }
}

@Composable
fun WasteHistoryCard(item: ScanHistory, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.inorganic),
                error = painterResource(R.drawable.inorganic),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.itemName, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                Text(item.status, fontSize = 12.sp, color = HijauEco)
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier.size(20.dp).padding(start = 4.dp), // Using Back as placeholder or just rotation
                tint = AbuAbuText
            )
        }
    }
}

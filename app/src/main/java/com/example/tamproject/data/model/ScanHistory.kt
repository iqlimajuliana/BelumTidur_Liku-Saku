package com.example.tamproject.data.model

data class ScanHistory(
    val id: String = "",
    val userId: String = "",
    val itemName: String = "",
    val category: String = "",
    val imageUrl: String = "",
    val timestamp: Long = 0,
    val status: String = "",
    val material: String = "",
    val recyclable: String = "",
    val recyclingProcess: String = ""
)

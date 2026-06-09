package com.example.tamproject.data.model

data class User(
    val userId: String = "",
    val fullName: String = "",
    val email: String = "",
    val points: Int = 0,
    val balance: Double = 0.0,
    val level: Int = 1,
    val streak: Int = 0,
    val profilePictureUrl: String = ""
)

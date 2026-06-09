package com.example.tamproject.data.model

import com.google.gson.annotations.SerializedName

data class ImgBBResponse(
    @SerializedName("data")
    val data: ImgBBData,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("status")
    val status: Int
)

data class ImgBBData(
    @SerializedName("url")
    val url: String
)

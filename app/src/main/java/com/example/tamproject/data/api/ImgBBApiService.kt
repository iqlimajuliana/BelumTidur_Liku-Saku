package com.example.tamproject.data.api

import com.example.tamproject.data.model.ImgBBResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ImgBBApiService {
    @Multipart
    @POST("1/upload")
    suspend fun uploadImage(
        @Query("key") key: String,
        @Part image: MultipartBody.Part
    ): ImgBBResponse
}

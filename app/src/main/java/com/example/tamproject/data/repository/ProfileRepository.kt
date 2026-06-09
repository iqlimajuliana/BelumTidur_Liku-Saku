package com.example.tamproject.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import com.example.tamproject.data.api.EndPoint
import com.example.tamproject.data.api.ImgBBApiService
import com.example.tamproject.data.model.Achievement
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ProfileRepository(
    private val context: Context,
    private val imgBBApiService: ImgBBApiService
) {
    private val firestore = FirebaseFirestore.getInstance()

    private fun isOnline(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || 
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || 
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
    }

    fun getAchievements(userId: String): Flow<List<Achievement>> = callbackFlow {
        val listener = firestore.collection("achievements")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val items = snapshot?.toObjects(Achievement::class.java) ?: emptyList()
                trySend(items)
            }
        awaitClose { listener.remove() }
    }

    private suspend fun prepareMultipart(imageUri: Uri): MultipartBody.Part? = withContext(Dispatchers.IO) {
        try {
            val inputStream = context.contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            if (bitmap == null) return@withContext null

            val tempFile = File.createTempFile("profile_", ".jpg", context.cacheDir)
            FileOutputStream(tempFile).use { output ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, output)
            }

            val requestFile = tempFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("image", tempFile.name, requestFile)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun updateProfile(userId: String, newName: String, imageUri: Uri?): Result<Unit> {
        return try {
            val updates = mutableMapOf<String, Any>("fullName" to newName)
            
            if (imageUri != null) {
                if (!isOnline()) return Result.failure(Exception("Tidak ada koneksi internet untuk mengunggah foto."))
                
                val multipartBody = prepareMultipart(imageUri) ?: return Result.failure(Exception("Gagal menyiapkan data gambar."))
                val response = imgBBApiService.uploadImage(EndPoint.API_KEY, multipartBody)
                
                if (response.success) {
                    updates["profilePictureUrl"] = response.data.url
                } else {
                    return Result.failure(Exception("Gagal mengunggah foto ke server: ${response.status}"))
                }
            }
            
            firestore.collection("users").document(userId).update(updates).await()
            Result.success(Unit)
        } catch (e: IOException) {
            Result.failure(Exception("Kesalahan jaringan: Periksa koneksi internet Anda."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

package com.example.tamproject.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.tamproject.data.api.EndPoint
import com.example.tamproject.data.api.ImgBBApiService
import com.example.tamproject.data.model.EcoDrop
import com.example.tamproject.data.model.ScanHistory
import com.example.tamproject.data.model.SortingItem
import com.example.tamproject.data.utils.NetworkUtils
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

class WasteRepository(
    private val context: Context,
    private val imgBBApiService: ImgBBApiService
) {
    private val firestore = FirebaseFirestore.getInstance()

    fun getEcoDrops(): Flow<List<EcoDrop>> = callbackFlow {
        val listener = firestore.collection("eco_drops")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val items = snapshot?.toObjects(EcoDrop::class.java) ?: emptyList()
                trySend(items)
            }
        awaitClose { listener.remove() }
    }

    fun getSortingItems(): Flow<List<SortingItem>> = callbackFlow {
        val listener = firestore.collection("waste_categories")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val items = snapshot?.toObjects(SortingItem::class.java) ?: emptyList()
                trySend(items)
            }
        awaitClose { listener.remove() }
    }

    fun getScanHistory(userId: String): Flow<List<ScanHistory>> = callbackFlow {
        val listener = firestore.collection("scan_history")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val items = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(ScanHistory::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(items)
            }
        awaitClose { listener.remove() }
    }

    fun getScanHistoryByCategory(userId: String, category: String): Flow<List<ScanHistory>> = callbackFlow {
        val listener = firestore.collection("scan_history")
            .whereEqualTo("userId", userId)
            .whereEqualTo("category", category)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val items = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(ScanHistory::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(items)
            }
        awaitClose { listener.remove() }
    }

    private suspend fun prepareMultipart(imageUri: Uri): MultipartBody.Part = withContext(Dispatchers.IO) {
        val bitmap = context.contentResolver.openInputStream(imageUri)?.use { 
            BitmapFactory.decodeStream(it)
        } ?: throw Exception("Gagal memuat gambar")

        val tempFile = File.createTempFile("upload_", ".jpg", context.cacheDir)
        FileOutputStream(tempFile).use { output ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, output)
        }

        val requestFile = tempFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        MultipartBody.Part.createFormData("image", tempFile.name, requestFile)
    }

    suspend fun saveScanManual(
        userId: String, 
        itemName: String, 
        category: String, 
        imageUri: Uri,
        material: String,
        recyclable: String,
        recyclingProcess: String
    ): Result<Unit> {
        if (!NetworkUtils.isOnline(context)) return Result.failure(Exception("Tidak ada koneksi internet. Silakan periksa jaringan Anda."))
        
        return try {
            val multipartBody = prepareMultipart(imageUri)
            val response = imgBBApiService.uploadImage(EndPoint.API_KEY, multipartBody)
            
            if (response.success) {
                val imageUrl = response.data.url
                val scanData = ScanHistory(
                    userId = userId,
                    itemName = itemName,
                    category = category,
                    imageUrl = imageUrl,
                    timestamp = System.currentTimeMillis(),
                    status = "Pending",
                    material = material,
                    recyclable = recyclable,
                    recyclingProcess = recyclingProcess
                )
                
                firestore.collection("scan_history").add(scanData).await()
                Result.success(Unit)
            } else {
                Result.failure(Exception("Upload gagal: ${response.status}"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Kesalahan jaringan: Gagal terhubung ke server."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun uploadScanImage(userId: String, imageUri: Uri): Result<String> {
        if (!NetworkUtils.isOnline(context)) return Result.failure(Exception("Tidak ada koneksi internet."))

        return try {
            val multipartBody = prepareMultipart(imageUri)
            val response = imgBBApiService.uploadImage(EndPoint.API_KEY, multipartBody)
            
            if (response.success) {
                val imageUrl = response.data.url
                val scanData = hashMapOf(
                    "userId" to userId,
                    "imageUrl" to imageUrl,
                    "timestamp" to System.currentTimeMillis(),
                    "status" to "Completed"
                )
                firestore.collection("scan_history").add(scanData).await()
                Result.success(imageUrl)
            } else {
                Result.failure(Exception("Upload ImgBB gagal"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Koneksi internet terputus."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

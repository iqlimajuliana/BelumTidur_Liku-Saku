package com.example.tamproject.data.repository

import com.example.tamproject.data.model.PantryItemData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class PantryRepository {
    private val firestore = FirebaseFirestore.getInstance()

    fun getPantryItems(userId: String): Flow<List<PantryItemData>> = callbackFlow {
        val listener = firestore.collection("pantry")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val items = snapshot.documents.map { doc ->
                        doc.toObject(PantryItemData::class.java)?.copy(id = doc.id) ?: PantryItemData()
                    }
                    trySend(items)
                }
            }
        awaitClose { listener.remove() }
    }

    suspend fun addPantryItem(item: PantryItemData): Result<Unit> {
        return try {
            firestore.collection("pantry").add(item).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deletePantryItem(itemId: String): Result<Unit> {
        return try {
            firestore.collection("pantry").document(itemId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updatePantryItemQuantity(itemId: String, newQuantity: Int): Result<Unit> {
        return try {
            if (newQuantity <= 0) {
                deletePantryItem(itemId)
            } else {
                firestore.collection("pantry").document(itemId).update("sum", newQuantity).await()
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

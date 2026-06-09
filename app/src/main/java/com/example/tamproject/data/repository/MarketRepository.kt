package com.example.tamproject.data.repository

import android.content.Context
import com.example.tamproject.data.model.ProductItem
import com.example.tamproject.data.model.User
import com.example.tamproject.data.utils.NetworkUtils
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.io.IOException

class MarketRepository(private val context: Context) {
    private val firestore = FirebaseFirestore.getInstance()

    fun getProducts(): Flow<List<ProductItem>> = callbackFlow {
        val listener = firestore.collection("products")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val items = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(ProductItem::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(items)
            }
        awaitClose { listener.remove() }
    }

    suspend fun redeemProductWithPoints(userId: String, product: ProductItem): Result<Unit> {
        if (!NetworkUtils.isOnline(context)) return Result.failure(Exception("Tidak ada koneksi internet. Periksa jaringan Anda."))
        
        return try {
            firestore.runTransaction { transaction ->
                val userRef = firestore.collection("users").document(userId)
                val userSnapshot = transaction.get(userRef)
                val user = userSnapshot.toObject(User::class.java) ?: throw Exception("Pengguna tidak ditemukan")

                if (user.points < product.pricePoints) {
                    throw Exception("Poin tidak cukup")
                }

                transaction.update(userRef, "points", user.points - product.pricePoints)

                val transRef = firestore.collection("transactions").document()
                val transactionData = hashMapOf(
                    "userId" to userId,
                    "productId" to product.id,
                    "productName" to product.name,
                    "pointsSpent" to product.pricePoints,
                    "date" to System.currentTimeMillis(),
                    "paymentMethod" to "POINTS"
                )
                transaction.set(transRef, transactionData)
            }.await()
            Result.success(Unit)
        } catch (e: IOException) {
            Result.failure(Exception("Kesalahan jaringan: Gagal terhubung ke Firestore."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun purchaseProductWithBalance(userId: String, product: ProductItem): Result<Unit> {
        if (!NetworkUtils.isOnline(context)) return Result.failure(Exception("Tidak ada koneksi internet. Periksa jaringan Anda."))

        return try {
            firestore.runTransaction { transaction ->
                val userRef = firestore.collection("users").document(userId)
                val userSnapshot = transaction.get(userRef)
                val user = userSnapshot.toObject(User::class.java) ?: throw Exception("Pengguna tidak ditemukan")

                if (user.balance < product.priceIdr) {
                    throw Exception("Saldo tidak cukup")
                }

                transaction.update(userRef, "balance", user.balance - product.priceIdr)

                val transRef = firestore.collection("transactions").document()
                val transactionData = hashMapOf(
                    "userId" to userId,
                    "productId" to product.id,
                    "productName" to product.name,
                    "amountSpent" to product.priceIdr,
                    "date" to System.currentTimeMillis(),
                    "paymentMethod" to "BALANCE"
                )
                transaction.set(transRef, transactionData)
            }.await()
            Result.success(Unit)
        } catch (e: IOException) {
            Result.failure(Exception("Kesalahan jaringan: Gagal terhubung ke Firestore."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

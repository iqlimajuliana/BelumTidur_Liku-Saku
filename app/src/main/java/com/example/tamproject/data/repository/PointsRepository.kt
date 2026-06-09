package com.example.tamproject.data.repository

import com.example.tamproject.data.model.Redemption
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class PointsRepository {
    private val firestore = FirebaseFirestore.getInstance()

    fun getRedemptions(userId: String): Flow<List<Redemption>> = callbackFlow {
        val listener = firestore.collection("redemptions")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val items = snapshot.toObjects(Redemption::class.java)
                    trySend(items)
                }
            }
        awaitClose { listener.remove() }
    }
}

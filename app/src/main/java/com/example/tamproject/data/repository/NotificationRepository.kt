package com.example.tamproject.data.repository

import com.example.tamproject.data.model.NotificationItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class NotificationRepository {
    private val firestore = FirebaseFirestore.getInstance()

    fun getNotifications(userId: String): Flow<List<NotificationItem>> = callbackFlow {
        val listener = firestore.collection("notifications")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val items = snapshot.documents.map { doc ->
                        doc.toObject(NotificationItem::class.java)?.copy(id = doc.id) ?: NotificationItem()
                    }
                    trySend(items)
                }
            }
        awaitClose { listener.remove() }
    }
}

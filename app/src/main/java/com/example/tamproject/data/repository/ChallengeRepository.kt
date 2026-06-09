package com.example.tamproject.data.repository

import com.example.tamproject.data.model.Mission
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ChallengeRepository {
    private val firestore = FirebaseFirestore.getInstance()

    fun getMissions(userId: String): Flow<List<Mission>> = callbackFlow {
        val listener = firestore.collection("missions")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val items = snapshot.documents.map { doc ->
                        doc.toObject(Mission::class.java)?.copy(id = doc.id) ?: Mission()
                    }
                    trySend(items)
                }
            }
        awaitClose { listener.remove() }
    }

    suspend fun claimMission(userId: String, mission: Mission): Result<Unit> = try {
        firestore.runTransaction { transaction ->
            val userRef = firestore.collection("users").document(userId)
            val userSnapshot = transaction.get(userRef)
            
            // 1. Ambil poin saat ini dan tambah dengan poin misi
            val currentPoints = userSnapshot.getLong("points") ?: 0L
            val missionPoints = mission.p.filter { it.isDigit() }.toLongOrNull() ?: 0L
            transaction.update(userRef, "points", currentPoints + missionPoints)

            // 2. Tambah ke koleksi Achievements
            val achievementRef = firestore.collection("achievements").document()
            val achievementData = mapOf(
                "userId" to userId,
                "label" to mission.title,
                "iconRes" to "ic_achievement_default",
                "timestamp" to com.google.firebase.Timestamp.now()
            )
            transaction.set(achievementRef, achievementData)

            // 3. Hapus dari koleksi Missions
            val missionRef = firestore.collection("missions").document(mission.id)
            transaction.delete(missionRef)
        }.await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

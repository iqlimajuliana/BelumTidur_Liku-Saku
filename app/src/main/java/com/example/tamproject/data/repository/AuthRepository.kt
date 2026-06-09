package com.example.tamproject.data.repository

import com.example.tamproject.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _isLoggedIn = MutableStateFlow(auth.currentUser != null)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val userData: StateFlow<User?> = isLoggedIn
        .flatMapLatest { loggedIn ->
            if (loggedIn) {
                getUserDataFlow(auth.currentUser?.uid ?: "")
            } else {
                flowOf(null)
            }
        }
        .stateIn(repositoryScope, SharingStarted.WhileSubscribed(5000), null)

    private fun getUserDataFlow(uid: String): Flow<User?> = callbackFlow {
        if (uid.isEmpty()) {
            trySend(null)
            close()
            return@callbackFlow
        }
        val listener = firestore.collection("users").document(uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    trySend(snapshot.toObject(User::class.java))
                } else {
                    trySend(null)
                }
            }
        awaitClose { listener.remove() }
    }

    init {
        auth.addAuthStateListener { firebaseAuth ->
            _isLoggedIn.value = firebaseAuth.currentUser != null
        }
    }

    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(email: String, password: String, fullName: String): Result<Unit> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: throw Exception("User ID is null")

            val userProfile = User(
                userId = userId,
                fullName = fullName,
                email = email,
                points = 0,
                level = 1,
                streak = 0
            )

            firestore.collection("users").document(userId).set(userProfile).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun getCurrentUserId(): String? = auth.currentUser?.uid
}

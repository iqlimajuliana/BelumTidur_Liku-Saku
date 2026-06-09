package com.example.tamproject.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tamproject.data.model.Achievement
import com.example.tamproject.data.repository.AuthRepository
import com.example.tamproject.data.repository.ProfileRepository
import com.example.tamproject.data.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProfileRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    val userData = authRepository.userData

    private val _achievements = MutableStateFlow<List<Achievement>>(emptyList())
    val achievements: StateFlow<List<Achievement>> = _achievements.asStateFlow()

    private val _profileState = MutableStateFlow<Resource<Unit>>(Resource.Idle)
    val profileState: StateFlow<Resource<Unit>> = _profileState.asStateFlow()

    init {
        viewModelScope.launch {
            userData.collect { user ->
                if (user != null) {
                    loadAchievements(user.userId)
                }
            }
        }
    }

    private fun loadAchievements(userId: String) {
        viewModelScope.launch {
            repository.getAchievements(userId)
                .catch { e -> _profileState.value = Resource.Error("Gagal memuat pencapaian: ${e.localizedMessage}") }
                .collect {
                    _achievements.value = it
                }
        }
    }

    fun updateProfile(userId: String, newName: String, imageUri: Uri?) {
        val currentUser = userData.value
        if (currentUser == null || currentUser.userId != userId) {
            _profileState.value = Resource.Error("Sesi pengguna tidak valid.")
            return
        }

        viewModelScope.launch {
            _profileState.value = Resource.Loading
            val result = repository.updateProfile(userId, newName, imageUri)
            if (result.isSuccess) {
                _profileState.value = Resource.Success(Unit)
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Gagal memperbarui profil"
                _profileState.value = Resource.Error(errorMsg)
            }
        }
    }

    fun clearProfileState() {
        _profileState.value = Resource.Idle
    }
}

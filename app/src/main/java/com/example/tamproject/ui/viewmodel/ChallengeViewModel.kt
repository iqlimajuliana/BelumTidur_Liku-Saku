package com.example.tamproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tamproject.data.model.Mission
import com.example.tamproject.data.repository.AuthRepository
import com.example.tamproject.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChallengeViewModel(
    private val repository: ChallengeRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _missions = MutableStateFlow<List<Mission>>(emptyList())
    val missions: StateFlow<List<Mission>> = _missions.asStateFlow()

    val userData = authRepository.userData

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _claimState = MutableStateFlow<Result<Unit>?>(null)
    val claimState: StateFlow<Result<Unit>?> = _claimState.asStateFlow()

    init {
        viewModelScope.launch {
            userData.collect { user ->
                if (user != null) {
                    loadMissions(user.userId)
                }
            }
        }
    }

    private fun loadMissions(userId: String) {
        viewModelScope.launch {
            repository.getMissions(userId).collect {
                _missions.value = it
                _isLoading.value = false
            }
        }
    }

    fun claimMission(mission: Mission) {
        val userId = authRepository.getCurrentUserId() ?: return

        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.claimMission(userId, mission)
            _claimState.value = result
            _isLoading.value = false
        }
    }

    fun resetClaimState() {
        _claimState.value = null
    }
}

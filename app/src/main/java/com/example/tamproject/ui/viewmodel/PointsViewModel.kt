package com.example.tamproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tamproject.data.model.Redemption
import com.example.tamproject.data.repository.AuthRepository
import com.example.tamproject.data.repository.PointsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PointsViewModel(
    private val repository: PointsRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    val userData = authRepository.userData

    private val _redemptions = MutableStateFlow<List<Redemption>>(emptyList())
    val redemptions: StateFlow<List<Redemption>> = _redemptions.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            userData.collect { user ->
                if (user != null) {
                    loadRedemptions(user.userId)
                }
            }
        }
    }

    private fun loadRedemptions(userId: String) {
        viewModelScope.launch {
            repository.getRedemptions(userId).collect {
                _redemptions.value = it
                _isLoading.value = false
            }
        }
    }
}

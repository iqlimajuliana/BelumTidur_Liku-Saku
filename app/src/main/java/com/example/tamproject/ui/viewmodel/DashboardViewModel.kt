package com.example.tamproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tamproject.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel(private val authRepository: AuthRepository) : ViewModel() {
    val userData = authRepository.userData

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun logout() {
        authRepository.logout()
    }
}

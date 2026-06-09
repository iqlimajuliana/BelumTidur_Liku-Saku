package com.example.tamproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tamproject.data.model.NotificationItem
import com.example.tamproject.data.repository.AuthRepository
import com.example.tamproject.data.repository.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val repository: NotificationRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val userData = authRepository.userData

    init {
        viewModelScope.launch {
            userData.collect { user ->
                if (user != null) {
                    loadNotifications(user.userId)
                }
            }
        }
    }

    private fun loadNotifications(userId: String) {
        viewModelScope.launch {
            repository.getNotifications(userId).collect {
                _notifications.value = it
                _isLoading.value = false
            }
        }
    }
}

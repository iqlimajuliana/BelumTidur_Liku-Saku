package com.example.tamproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tamproject.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    val isLoggedIn = repository.isLoggedIn

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState.asStateFlow()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val result = repository.login(email, password)
            result.fold(
                onSuccess = {
                    _loginState.value = LoginState.Success
                },
                onFailure = {
                    _loginState.value = LoginState.Error(it.message ?: "Login failed")
                }
            )
        }
    }

    fun register(email: String, password: String, fullName: String) {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            val result = repository.register(email, password, fullName)
            result.fold(
                onSuccess = {
                    _registerState.value = RegisterState.Success
                },
                onFailure = {
                    _registerState.value = RegisterState.Error(it.message ?: "Registration failed")
                }
            )
        }
    }

    fun logout() {
        repository.logout()
    }

    fun resetStates() {
        _registerState.value = RegisterState.Idle
        _loginState.value = LoginState.Idle
    }
}

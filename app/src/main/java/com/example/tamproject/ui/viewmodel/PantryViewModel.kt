package com.example.tamproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tamproject.data.model.PantryItemData
import com.example.tamproject.data.repository.AuthRepository
import com.example.tamproject.data.repository.PantryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PantryViewModel(
    private val repository: PantryRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _pantryItems = MutableStateFlow<List<PantryItemData>>(emptyList())
    val pantryItems: StateFlow<List<PantryItemData>> = _pantryItems.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val userData = authRepository.userData

    init {
        viewModelScope.launch {
            userData.collect { user ->
                if (user != null) {
                    loadPantryItems(user.userId)
                }
            }
        }
    }

    private fun loadPantryItems(userId: String) {
        viewModelScope.launch {
            repository.getPantryItems(userId).collect {
                _pantryItems.value = it
                _isLoading.value = false
            }
        }
    }

    fun addItem(name: String, sum: Int, date: String, iconRes: String) {
        val userId = userData.value?.userId ?: return
        viewModelScope.launch {
            val newItem = PantryItemData(userId = userId, name = name, sum = sum, date = date, iconRes = iconRes)
            repository.addPantryItem(newItem)
        }
    }

    fun deleteItem(itemId: String) {
        viewModelScope.launch {
            repository.deletePantryItem(itemId)
        }
    }

    fun updateQuantity(itemId: String, newQuantity: Int) {
        viewModelScope.launch {
            repository.updatePantryItemQuantity(itemId, newQuantity)
        }
    }
}

package com.example.tamproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tamproject.data.model.ProductItem
import com.example.tamproject.data.repository.AuthRepository
import com.example.tamproject.data.repository.MarketRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MarketViewModel(
    private val repository: MarketRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _allProducts = MutableStateFlow<List<ProductItem>>(emptyList())
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val products: StateFlow<List<ProductItem>> = combine(_allProducts, _searchQuery) { products, query ->
        if (query.isBlank()) {
            products
        } else {
            products.filter { it.name.contains(query, ignoreCase = true) }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    val userData = authRepository.userData

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            repository.getProducts().collect {
                _allProducts.value = it
                _isLoading.value = false
            }
        }
    }

    fun onSearchChanged(query: String) {
        _searchQuery.value = query
    }

    fun redeemWithPoints(product: ProductItem) {
        val user = userData.value ?: return
        val userId = user.userId
        
        if (user.points < product.pricePoints) {
            viewModelScope.launch {
                _uiEvent.emit(UiEvent.Error("Insufficient points! You need ${product.pricePoints} points."))
            }
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.redeemProductWithPoints(userId, product)
            _isLoading.value = false
            
            if (result.isSuccess) {
                _uiEvent.emit(UiEvent.Success("Successfully redeemed ${product.name}!"))
            } else {
                _uiEvent.emit(UiEvent.Error(result.exceptionOrNull()?.message ?: "Redemption failed"))
            }
        }
    }

    fun buyWithBalance(product: ProductItem) {
        val user = userData.value ?: return
        val userId = user.userId
        
        if (user.balance < product.priceIdr) {
            viewModelScope.launch {
                _uiEvent.emit(UiEvent.Error("Insufficient balance! You need Rp ${product.priceIdr}."))
            }
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.purchaseProductWithBalance(userId, product)
            _isLoading.value = false
            
            if (result.isSuccess) {
                _uiEvent.emit(UiEvent.Success("Successfully purchased ${product.name}!"))
            } else {
                _uiEvent.emit(UiEvent.Error(result.exceptionOrNull()?.message ?: "Purchase failed"))
            }
        }
    }

    sealed class UiEvent {
        data class Success(val message: String) : UiEvent()
        data class Error(val message: String) : UiEvent()
    }
}

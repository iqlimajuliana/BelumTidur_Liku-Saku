package com.example.tamproject.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tamproject.data.model.EcoDrop
import com.example.tamproject.data.model.ScanHistory
import com.example.tamproject.data.model.SortingItem
import com.example.tamproject.data.repository.AuthRepository
import com.example.tamproject.data.repository.WasteRepository
import com.example.tamproject.data.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class WasteViewModel(
    private val repository: WasteRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _ecoDrops = MutableStateFlow<List<EcoDrop>>(emptyList())
    val ecoDrops: StateFlow<List<EcoDrop>> = _ecoDrops.asStateFlow()

    private val _sortingItems = MutableStateFlow<List<SortingItem>>(emptyList())
    val sortingItems: StateFlow<List<SortingItem>> = _sortingItems.asStateFlow()

    private val _scanHistory = MutableStateFlow<List<ScanHistory>>(emptyList())
    val scanHistory: StateFlow<List<ScanHistory>> = _scanHistory.asStateFlow()

    private val _uploadState = MutableStateFlow<Resource<String>>(Resource.Idle)
    val uploadState: StateFlow<Resource<String>> = _uploadState.asStateFlow()

    val userData = authRepository.userData

    init {
        loadEcoDrops()
        loadSortingItems()
        viewModelScope.launch {
            userData.collect { user ->
                if (user != null) {
                    loadScanHistory(user.userId)
                }
            }
        }
    }

    private fun loadEcoDrops() {
        viewModelScope.launch {
            repository.getEcoDrops()
                .catch { e -> _uploadState.value = Resource.Error("Gagal memuat data Eco Drop: ${e.localizedMessage}") }
                .collect { list -> _ecoDrops.value = list }
        }
    }

    private fun loadSortingItems() {
        viewModelScope.launch {
            repository.getSortingItems()
                .catch { e -> _uploadState.value = Resource.Error("Gagal memuat kategori: ${e.localizedMessage}") }
                .collect { _sortingItems.value = it }
        }
    }

    private fun loadScanHistory(userId: String) {
        viewModelScope.launch {
            repository.getScanHistory(userId)
                .catch { e -> _uploadState.value = Resource.Error("Gagal memuat riwayat: ${e.localizedMessage}") }
                .collect { _scanHistory.value = it }
        }
    }

    fun loadScanHistoryByCategory(userId: String, category: String) {
        viewModelScope.launch {
            repository.getScanHistoryByCategory(userId, category)
                .catch { e -> _uploadState.value = Resource.Error("Gagal memuat riwayat kategori $category: ${e.localizedMessage}") }
                .collect { _scanHistory.value = it }
        }
    }

    fun saveManual(
        userId: String, 
        itemName: String, 
        category: String, 
        imageUri: Uri,
        material: String,
        recyclable: String,
        recyclingProcess: String
    ) {
        viewModelScope.launch {
            _uploadState.value = Resource.Loading
            val result = repository.saveScanManual(
                userId, 
                itemName, 
                category, 
                imageUri,
                material,
                recyclable,
                recyclingProcess
            )
            if (result.isSuccess) {
                _uploadState.value = Resource.Success("Data berhasil disimpan!")
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Gagal menyimpan data"
                _uploadState.value = Resource.Error(errorMsg)
            }
        }
    }

    fun captureAndUpload(uri: Uri, userId: String) {
        viewModelScope.launch {
            _uploadState.value = Resource.Loading
            val result = repository.uploadScanImage(userId, uri)
            if (result.isSuccess) {
                _uploadState.value = Resource.Success(result.getOrDefault(""))
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Gagal mengunggah gambar"
                _uploadState.value = Resource.Error(errorMsg)
            }
        }
    }

    fun resetUploadState() {
        _uploadState.value = Resource.Idle
    }
}

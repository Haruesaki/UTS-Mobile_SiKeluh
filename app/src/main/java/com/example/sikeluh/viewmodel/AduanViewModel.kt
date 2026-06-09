package com.example.sikeluh.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sikeluh.data.repository.AduanRepository
import com.example.sikeluh.model.Aduan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AduanViewModel : ViewModel() {
    private val repository = AduanRepository()
    
    private val _aduans = MutableStateFlow<List<Aduan>>(emptyList())
    val aduans: StateFlow<List<Aduan>> = _aduans

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchAduans() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _aduans.value = repository.getAllAduan()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchUserAduans(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _aduans.value = repository.getAduanByUserId(userId)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun submitAduan(aduan: Aduan, imageBytes: ByteArray? = null, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                var finalAduan = aduan
                if (imageBytes != null) {
                    val fileName = "aduan_${System.currentTimeMillis()}.jpg"
                    try {
                        val imageUrl = repository.uploadImage(fileName, imageBytes)
                        finalAduan = aduan.copy(lampiranFoto = imageUrl)
                    } catch (e: Exception) {
                        onResult(false, "Gagal mengunggah gambar: ${e.message}")
                        return@launch
                    }
                }
                repository.createAduan(finalAduan)
                fetchAduans()
                onResult(true, null)
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(false, "Gagal menyimpan aduan: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}

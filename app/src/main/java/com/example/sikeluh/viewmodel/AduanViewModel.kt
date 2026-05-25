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

    fun submitAduan(aduan: Aduan) {
        viewModelScope.launch {
            try {
                repository.createAduan(aduan)
                fetchAduans() // Refresh list
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

package com.example.sikeluh.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sikeluh.data.repository.AuthRepository
import com.example.sikeluh.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()
    
    private val _currentUser = MutableStateFlow<UserProfile?>(null)
    val currentUser: StateFlow<UserProfile?> = _currentUser

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun login(nik: String, pass: String, onSuccess: () -> Unit) {
        if (nik.isBlank() || pass.isBlank()) {
            _error.value = "NIK dan Password tidak boleh kosong"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val profile = repository.signIn(nik, pass)
                _currentUser.value = profile
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message ?: "Login Gagal"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun register(nik: String, pass: String, nama: String, agree: Boolean, onSuccess: () -> Unit) {
        if (nama.isBlank() || nik.isBlank() || pass.isBlank()) {
            _error.value = "Semua field harus diisi"
            return
        }
        if (nik.length < 16) {
            _error.value = "NIK harus 16 digit"
            return
        }
        if (pass.length < 6) {
            _error.value = "Password minimal 6 karakter"
            return
        }
        if (!agree) {
            _error.value = "Anda harus menyetujui syarat & ketentuan"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val profile = repository.signUp(nik, pass, nama)
                _currentUser.value = profile
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message ?: "Pendaftaran Gagal"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.signOut()
                _currentUser.value = null
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

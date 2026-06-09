package com.example.sikeluh.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sikeluh.data.local.SessionManager
import com.example.sikeluh.data.repository.AuthRepository
import com.example.sikeluh.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AuthRepository()
    private val sessionManager = SessionManager(application)
    
    private val _currentUser = MutableStateFlow<UserProfile?>(null)
    val currentUser: StateFlow<UserProfile?> = _currentUser

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoggedIn = MutableStateFlow<Boolean?>(null) // null berarti masih memeriksa
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {
            val nik = sessionManager.getNik().first()
            if (nik != null) {
                try {
                    // Ambil profil lengkap dari basis data menggunakan NIK yang disimpan di sesi
                    val profile = repository.getProfileByNik(nik)
                    if (profile != null) {
                        _currentUser.value = profile
                        _isLoggedIn.value = true
                    } else {
                        _isLoggedIn.value = false
                    }
                } catch (e: Exception) {
                    _isLoggedIn.value = false
                }
            } else {
                _isLoggedIn.value = false
            }
        }
    }

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
                sessionManager.saveSession(nik, profile.id) // Simpan NIK dan ID ke DataStore
                _currentUser.value = profile
                _isLoggedIn.value = true
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
                sessionManager.saveSession(nik, profile.id) // Simpan NIK dan ID ke DataStore
                _currentUser.value = profile
                _isLoggedIn.value = true
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
                sessionManager.clearSession() // Hapus DataStore
                repository.signOut()
                _currentUser.value = null
                _isLoggedIn.value = false
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateProfile(nama: String, nik: String, email: String, telepon: String, alamat: String, fotoProfil: String?, onSuccess: () -> Unit) {
        val user = _currentUser.value ?: return
        
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val updatedProfile = user.copy(
                    nama = nama,
                    nik = nik,
                    email = email,
                    nomorTelepon = telepon,
                    alamat = alamat,
                    fotoProfil = fotoProfil
                )
                repository.updateProfile(updatedProfile)
                _currentUser.value = updatedProfile
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun changePassword(currentPass: String, newPass: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val user = _currentUser.value ?: return
        
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Verifikasi kata sandi saat ini
                if (user.password != currentPass) {
                    onError("Kata sandi saat ini salah")
                    return@launch
                }
                
                // Perbarui kata sandi di basis data
                repository.updatePassword(user.id, newPass)
                
                // Perbarui sesi lokal
                _currentUser.value = user.copy(password = newPass)
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}

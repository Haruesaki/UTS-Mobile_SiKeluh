package com.example.sikeluh.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sikeluh.model.NotificationItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotifikasiViewModel : ViewModel() {
    
    private val _notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notifications: StateFlow<List<NotificationItem>> = _notifications

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchNotifications(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Menampilkan data dummy sesuai permintaan gambar
                _notifications.value = listOf(
                    NotificationItem(
                        id = "1",
                        title = "Status Aduan Diperbarui",
                        description = "Aduan Anda Mengenai \"Jalan Berlubang di Jl. Merdeka\" telah diubah statusnya menjadi Dalam Pengerjaan",
                        createdAt = "2 jam yang lalu"
                    ),
                    NotificationItem(
                        id = "2",
                        title = "Status Aduan Diperbarui",
                        description = "Aduan Anda Mengenai \"Jalan Berlubang di Jl. Merdeka\" telah diubah statusnya menjadi Dalam Pengerjaan",
                        createdAt = "2 jam yang lalu"
                    ),
                    NotificationItem(
                        id = "3",
                        title = "Status Aduan Diperbarui",
                        description = "Aduan Anda Mengenai \"Jalan Berlubang di Jl. Merdeka\" telah diubah statusnya menjadi Dalam Pengerjaan",
                        createdAt = "2 jam yang lalu"
                    ),
                    NotificationItem(
                        id = "4",
                        title = "Status Aduan Diperbarui",
                        description = "Aduan Anda Mengenai \"Jalan Berlubang di Jl. Merdeka\" telah diubah statusnya menjadi Dalam Pengerjaan",
                        createdAt = "2 jam yang lalu"
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

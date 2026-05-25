package com.example.sikeluh.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sikeluh.data.repository.NotifikasiRepository
import com.example.sikeluh.model.NotificationItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotifikasiViewModel : ViewModel() {
    private val repository = NotifikasiRepository()
    
    private val _notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notifications: StateFlow<List<NotificationItem>> = _notifications

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchNotifications(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _notifications.value = repository.getNotificationsByUserId(userId)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

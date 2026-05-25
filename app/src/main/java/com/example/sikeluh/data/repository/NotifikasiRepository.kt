package com.example.sikeluh.data.repository

import com.example.sikeluh.data.remote.SupabaseClient
import com.example.sikeluh.model.NotificationItem
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotifikasiRepository {
    suspend fun getNotificationsByUserId(userId: String): List<NotificationItem> = withContext(Dispatchers.IO) {
        SupabaseClient.client.postgrest["notifications"].select {
            filter {
                eq("user_id", userId)
            }
        }.decodeList<NotificationItem>()
    }
}

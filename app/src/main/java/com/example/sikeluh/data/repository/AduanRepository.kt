package com.example.sikeluh.data.repository

import com.example.sikeluh.data.remote.SupabaseClient
import com.example.sikeluh.model.Aduan
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AduanRepository {
    suspend fun getAllAduan(): List<Aduan> = withContext(Dispatchers.IO) {
        SupabaseClient.client.postgrest["aduan"].select().decodeList<Aduan>()
    }

    suspend fun getAduanByUserId(userId: String): List<Aduan> = withContext(Dispatchers.IO) {
        SupabaseClient.client.postgrest["aduan"].select {
            filter {
                eq("user_id", userId)
            }
        }.decodeList<Aduan>()
    }

    suspend fun createAduan(aduan: Aduan) = withContext(Dispatchers.IO) {
        SupabaseClient.client.postgrest["aduan"].insert(aduan)
    }
}

package com.example.sikeluh.data.repository

import com.example.sikeluh.data.remote.SupabaseClient
import com.example.sikeluh.model.Aduan
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AduanRepository {
    suspend fun getAllAduan(): List<Aduan> = withContext(Dispatchers.IO) {
        SupabaseClient.client.from("aduan").select().decodeList<Aduan>()
    }

    suspend fun getAduanByUserId(userId: String): List<Aduan> = withContext(Dispatchers.IO) {
        SupabaseClient.client.from("aduan").select {
            filter {
                eq("id_pengguna", userId)
            }
        }.decodeList<Aduan>()
    }

    suspend fun createAduan(aduan: Aduan) = withContext(Dispatchers.IO) {
        SupabaseClient.client.from("aduan").insert(aduan)
    }

    suspend fun uploadImage(fileName: String, bytes: ByteArray): String = withContext(Dispatchers.IO) {
        val bucket = SupabaseClient.client.storage.from("foto_aduan")
        bucket.upload(fileName, bytes)
        bucket.publicUrl(fileName)
    }
}

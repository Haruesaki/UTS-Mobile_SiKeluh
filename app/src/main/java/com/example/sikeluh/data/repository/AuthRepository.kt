package com.example.sikeluh.data.repository

import com.example.sikeluh.data.remote.SupabaseClient
import com.example.sikeluh.model.UserProfile
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class AuthRepository {
    // Shared state to simulate current user session since we're not using Supabase Auth
    private var currentUserProfile: UserProfile? = null

    suspend fun signUp(nik: String, password: String, namaLengkap: String): UserProfile = withContext(Dispatchers.IO) {
        val profile = UserProfile(
            id = UUID.randomUUID().toString(),
            nik = nik,
            nama = namaLengkap,
            password = password
        )
        
        try {
            // Direct Insert into 'pengguna' table
            SupabaseClient.client.postgrest["pengguna"].insert(profile)
            currentUserProfile = profile
            profile
        } catch (e: Exception) {
            throw Exception("Gagal mendaftar: NIK mungkin sudah terdaftar")
        }
    }

    suspend fun signIn(nik: String, password: String): UserProfile = withContext(Dispatchers.IO) {
        try {
            // Direct Query to match nik and password
            val profile = SupabaseClient.client.postgrest["pengguna"]
                .select {
                    filter {
                        eq("nik", nik)
                        eq("password", password)
                    }
                }
                .decodeSingleOrNull<UserProfile>()
            
            profile?.let {
                currentUserProfile = it
                it
            } ?: throw Exception("NIK atau Password salah")
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getCurrentProfile(): UserProfile? = withContext(Dispatchers.IO) {
        // Return the cached profile from the current app session
        currentUserProfile
    }

    suspend fun signOut() = withContext(Dispatchers.IO) {
        currentUserProfile = null
    }
}

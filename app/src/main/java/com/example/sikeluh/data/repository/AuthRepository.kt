package com.example.sikeluh.data.repository

import com.example.sikeluh.data.remote.SupabaseClient
import com.example.sikeluh.model.UserProfile
import io.github.jan.supabase.postgrest.from
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
            SupabaseClient.client.from("pengguna").insert(profile)
            currentUserProfile = profile
            profile
        } catch (e: Exception) {
            throw Exception("Gagal mendaftar: NIK mungkin sudah terdaftar")
        }
    }

    suspend fun signIn(nik: String, password: String): UserProfile = withContext(Dispatchers.IO) {
        try {
            // Direct Query to match nik and password
            val profile = SupabaseClient.client.from("pengguna")
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

    suspend fun getProfileByNik(nik: String): UserProfile? = withContext(Dispatchers.IO) {
        try {
            val profile = SupabaseClient.client.from("pengguna")
                .select {
                    filter {
                        eq("nik", nik)
                    }
                }
                .decodeSingleOrNull<UserProfile>()
            
            profile?.let {
                currentUserProfile = it
            }
            profile
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateProfile(profile: UserProfile) = withContext(Dispatchers.IO) {
        try {
            SupabaseClient.client.from("pengguna").update(profile) {
                filter {
                    eq("id", profile.id)
                }
            }
            currentUserProfile = profile
        } catch (e: Exception) {
            throw Exception("Gagal memperbarui profil: ${e.message}")
        }
    }

    suspend fun updatePassword(userId: String, newPassword: String) = withContext(Dispatchers.IO) {
        try {
            SupabaseClient.client.from("pengguna").update(
                mapOf("password" to newPassword)
            ) {
                filter {
                    eq("id", userId)
                }
            }
        } catch (e: Exception) {
            throw Exception("Gagal memperbarui kata sandi: ${e.message}")
        }
    }

    suspend fun signOut() = withContext(Dispatchers.IO) {
        currentUserProfile = null
    }
}

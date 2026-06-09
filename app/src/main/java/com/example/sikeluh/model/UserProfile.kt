package com.example.sikeluh.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    @SerialName("id")
    val id: String,
    @SerialName("nama")
    val nama: String? = null,
    @SerialName("nik")
    val nik: String? = null,
    @SerialName("password")
    val password: String? = null,
    @SerialName("nomor_telepon")
    val nomorTelepon: String? = null,
    @SerialName("alamat")
    val alamat: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("foto_profil")
    val fotoProfil: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null
)

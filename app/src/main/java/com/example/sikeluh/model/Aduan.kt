package com.example.sikeluh.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Aduan(
    @SerialName("id")
    val id: String? = null,
    @SerialName("kategori_keluhan")
    val kategoriKeluhan: String,
    @SerialName("deskripsi_keluhan")
    val deskripsiKeluhan: String,
    @SerialName("lampiran_foto")
    val lampiranFoto: String? = null,
    @SerialName("lokasi_aduan")
    val lokasiAduan: String? = null,
    @SerialName("alamat_provinsi")
    val alamatProvinsi: String? = null,
    @SerialName("alamat_kota")
    val alamatKota: String? = null,
    @SerialName("alamat_kecamatan")
    val alamatKecamatan: String? = null,
    @SerialName("status")
    val status: String? = "Menunggu Verifikasi",
    @SerialName("created_at")
    val createdAt: String? = null
)

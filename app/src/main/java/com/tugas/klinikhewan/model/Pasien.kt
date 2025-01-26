package com.tugas.klinikhewan.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pasien(
    @SerialName("id_hewan")
    val idhewan: String = "",

    @SerialName("nama_hewan")
    val namahewan: String = "",

    @SerialName("jenis_hewan_id")
    val jenishewanid: String = "",

    val pemilik : String = "",

    @SerialName("kontak_pemilik")
    val kontakpemilik: String = "",

    @SerialName("tanggal_lahir")
    val tanggallahir: String = "",

    @SerialName("catatan_kesehatan")
    val catatankesehatan: String = "",
)

@Serializable
data class AllPasienResponse(
    val status: Boolean,
    val message: String,
    val data: List<Pasien>
)

@Serializable
data class PasienDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Pasien
)
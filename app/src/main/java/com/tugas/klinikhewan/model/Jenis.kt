package com.tugas.klinikhewan.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Jenis(
    @SerialName("id_jenis_hewan")
    val idjenishewan: String = "",

    @SerialName("nama_jenis_hewan")
    val namajenishewan: String = "",

    val deskripsi: String = ""
)

@Serializable
data class AllJenisResponse(
    val status: Boolean,
    val message: String,
    val data: List<Jenis>
)

@Serializable
data class JenisDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Jenis
)
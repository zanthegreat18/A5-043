package com.tugas.klinikhewan.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Perawatan(
    @SerialName("id_perawatan")
    val idperawatan: String,

    @SerialName("id_hewan")
    val idhewan: String,

    @SerialName("id_dokter")
    val iddokter: String,

    @SerialName("tanggal_perawatan")
    val tanggalperawatan: String,

    @SerialName("detail_perawatan")
    val detailperawatan: String
)

@Serializable
data class AllPerawatanResponse(
    val status: Boolean,
    val message: String,
    val data: List<Perawatan>
)

@Serializable
data class PerawatanDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Perawatan
)
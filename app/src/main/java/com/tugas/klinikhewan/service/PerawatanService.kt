package com.tugas.klinikhewan.service

import com.tugas.klinikhewan.model.AllPerawatanResponse
import com.tugas.klinikhewan.model.Perawatan
import com.tugas.klinikhewan.model.PerawatanDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PerawatanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("perawatan/store")
    suspend fun insertPerawatan(@Body perawatan: Perawatan)

    @GET("perawatan/.")
    suspend fun getAllPerawatan(): AllPerawatanResponse

    @GET("perawatan/{id_perawatan}")
    suspend fun getPerawatanById(@Path("id_perawatan") idperawatan: String): PerawatanDetailResponse

    @PUT("perawatan/{id_perawatan}")
    suspend fun updatePerawatan(@Path("id_perawatan") idperawatan: String, @Body perawatan: Perawatan)

    @DELETE("perawatan/{id_perawatan}")
    suspend fun deletePerawatan(@Path("id_perawatan") idperawatan: String): Response<Void>

}
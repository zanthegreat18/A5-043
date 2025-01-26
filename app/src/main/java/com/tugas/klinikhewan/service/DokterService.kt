package com.tugas.klinikhewan.service

import com.tugas.klinikhewan.model.AllDokterResponse
import com.tugas.klinikhewan.model.Dokter
import com.tugas.klinikhewan.model.DokterDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DokterService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("dokter/store")
    suspend fun insertDokter(@Body dokter: Dokter)

    @GET("dokter/.")
    suspend fun getAllDokter(): AllDokterResponse

    @GET("dokter/{id_dokter}")
    suspend fun getDokterById(@Path("id_dokter") iddokter: String): DokterDetailResponse

    @PUT("dokter/{id_dokter}")
    suspend fun updateDokter(@Path("id_dokter") iddokter: String, @Body dokter: Dokter)

    @DELETE("dokter/{id_dokter}")
    suspend fun deleteDokter(@Path("id_dokter") iddokter: String): Response<Void>

}